#!/bin/bash
trap "echo 'killing...'; ./kill.sh; exit" INT
. functions.sh

processArgs $*

# Delete old logs
rm -f $LOGDIR/*.log
sh kill.sh

# startGIS
startKernel --nomenu
startSims
startViewer
startViewerEventLogger

echo "Start your agents"
waitFor $LOGDIR/kernel.log "Kernel has shut down" 30

./logextract.sh ./logs/rescue.log ./scores

kill $PIDS
#./kill.sh

