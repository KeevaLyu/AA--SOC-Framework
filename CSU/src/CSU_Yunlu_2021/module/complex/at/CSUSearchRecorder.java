package CSU_Yunlu_2021.module.complex.at;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.component.module.algorithm.Clustering;
import adf.component.module.algorithm.PathPlanning;
import rescuecore2.worldmodel.EntityID;

import java.util.Set;

public class CSUSearchRecorder {
    private Set<EntityID> allBuildings;
    private Set<EntityID> allCivilians;
    private MessageManager messageManager;

    private EntityID lastPosition;
    private EntityID nowPosition;
    private EntityID target;
    private int nowPriority;
    private int strategyType;
    private WorldInfo worldInfo;
    public AgentInfo agentInfo;
    private ScenarioInfo scenarioInfo;
    private int voiceRange;
    private int lastClusterIndex = -1;

    private Clustering clustering;
    private PathPlanning pathPlanning;
}
