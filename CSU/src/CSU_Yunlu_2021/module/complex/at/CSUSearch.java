package CSU_Yunlu_2021.module.complex.at;

import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.component.module.algorithm.Clustering;
import adf.component.module.algorithm.PathPlanning;
import adf.component.module.complex.Search;
import rescuecore2.worldmodel.EntityID;

import java.util.Collection;

public class CSUSearch extends Search {
    private boolean debug = false;
    private PathPlanning pathPlanning;
    private Clustering clustering;
    private EntityID result;
    private Collection<EntityID> unvisitedBuildingIDs;

    public CSUSearch(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    @Override
    public EntityID getTarget() {
        return null;
    }

    @Override
    public Search calc() {
        return null;
    }

}
