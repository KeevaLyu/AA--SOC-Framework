package CSU_Yunlu_2021.centralized.ambulance;


import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.component.centralized.CommandPicker;
import adf.component.communication.CommunicationMessage;
import rescuecore2.worldmodel.EntityID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 未作修改
 */
public class CommandPickerAmbulance extends CommandPicker{


	    private int scoutDistance;

	    private Collection<CommunicationMessage> messages;
	    private Map<EntityID, EntityID> allocationData;

	    public CommandPickerAmbulance(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
	        super(ai, wi, si, moduleManager, developData);
	        this.messages = new ArrayList<>();
	        this.allocationData = null;
	        this.scoutDistance = developData.getInteger("CommandPickerAmbulance.scoutDistance", 40000);
	    }

	    @Override
	    public CommandPicker setAllocatorResult(Map<EntityID, EntityID> allocationData) {
	        this.allocationData = allocationData;
	        return this;
	    }

	    @Override
	    public CommandPicker calc() {
	        return this;
	    }

	    @Override
	    public Collection<CommunicationMessage> getResult() {
	        return this.messages;
	    }


	

}
