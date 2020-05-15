package com.schematical.chaoscraft.ai.inputs.targetcandidate;

import com.schematical.chaoscraft.ai.action.ActionBuffer;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.ai.inputs.BaseTargetInputNeuron;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.TargetHelper;
import com.schematical.chaosnet.model.ChaosNetException;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetCandidateHasAlreadyTriedActionTargetSlotInput extends BaseTargetInputNeuron {
    protected ActionTargetSlot actionTargetSlot = null;
    @Override
    public float evaluate(){
        ClientOrgManager clientOrgManager = ((OrgEntity)this.getEntity()).getClientOrgManager();

        ScanManager scanManager =  clientOrgManager.getScanManager();
        ScanEntry scanEntry = scanManager.getFocusedScanEntry();
       
        if(actionTargetSlot == null) {
            if(!(targetSlot instanceof  ActionTargetSlot)) {
                throw new ChaosNetException("Invalid `targetSlot` !instnaceof ActionTargetSlot");
            }
            actionTargetSlot = (ActionTargetSlot) targetSlot;
        }
        ActionBuffer actionBuffer = clientOrgManager.getActionBuffer();
        boolean hasExecutedRecently = actionBuffer.hasExecutedRecently(
                actionTargetSlot.getActionBaseClass(),
                scanEntry.getChaosTarget(),
                null,
                10
        );
        if(hasExecutedRecently){
            setCurrentValue(1f);
        }
        return getCurrentValue();
    }


}
