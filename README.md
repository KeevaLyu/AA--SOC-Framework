# `AA*-SOC Framework` Adaptive A* Graph Routing Approach with Smoothed Obstacle Clearance Strategy

Instructions to download, build and run the AA*-SOC Framework using the RoboCupRescue Simulation (RCRS) system

## 1. Software Pre-Requisites

- Git
- Gradle
- OpenJDK Java 8+

## 2. Download the project from GitHub

```bash

$ git@github.com:KeevaLyu/AA--SOC-Framework.git
```

## 3. Compile the project

Open a terminal window, navigate to the ```rcrs-server-1.5``` root directory
```bash
$ ./gradlew clean completeBuild
```

Open a new terminal window, navigate to the ```AA*-SOC``` root directory
```bash
$ ./gradlew clean build
```

## 4. Execute the project

Open the terminal window of the ```rcrs-server``` root directory and execute

```bash
$ cd boot
$ ./start.sh -m ../maps/gml/test/map -c ../maps/gml/test/config
```

Open the terminal window of the ```AA*-SOC``` root directory and execute

```bash
$ ./launch.sh -all
```

## 6. Support

To report a bug, suggest improvements or request support, please open an issue at GitHub <https://github.com/KeevaLyu/AA--SOC-Framework/issues>.
