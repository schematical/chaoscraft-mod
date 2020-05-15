package com.schematical.chaoscraft.ai.inputs.actioncandidate;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.action.ActionBuffer;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;

/**
 * Created by user1a on 12/8/18.
 */
public class ActionCandidateExecutedCountInput extends InputNeuron {



    @Override
    public float evaluate(){
        ClientOrgManager clientOrgManager = ((OrgEntity)this.getEntity()).getClientOrgManager();
        ScanManager scanManager = clientOrgManager.getScanManager();
        ActionTargetSlot actionTargetSlot = scanManager.getFocusedActionTargetSlot();
        ActionBuffer actionBuffer = clientOrgManager.getActionBuffer();
        String key = actionTargetSlot.getSimpleActionStatsKey();
        ActionBuffer.SimpleActionStats simpleActionStats = actionBuffer.getSimpleActionStats(key);
        setCurrentValue(simpleActionStats.numTimesExecuted);

        return getCurrentValue();
    }


}
