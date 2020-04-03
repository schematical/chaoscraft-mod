package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/10/18.
 *
 */

public class TargetCandidateSetScoreOutput extends OutputNeuron {
    private String targetSlotId;
    private ActionTargetSlot actionTargetSlot;
    private boolean loaded = false;
    @Override
    public void execute() {
        if(!loaded) {
            BiologyBase biologyBase = this.nNet.getBiology(targetSlotId);
            if (biologyBase instanceof ActionTargetSlot) {
                actionTargetSlot = (ActionTargetSlot) biologyBase;
                if (actionTargetSlot == null) {
                    throw new ChaosNetException("Cannot find `actionTargetSlot`: " + targetSlotId);
                }
            }
            loaded = true;
        }


        OrgEntity orgEntity =  ((OrgEntity)this.getEntity());
        ClientOrgManager clientOrgManager = orgEntity.getClientOrgManager();
        ScanManager scanManager = clientOrgManager.getScanManager();
        ScanEntry scanEntry = scanManager.getFocusedScanEntry();
        if(scanEntry == null) {
            throw new ChaosNetException("ScanEntity should not be null if this is firing");
        }
        if(actionTargetSlot != null){
            boolean isValid = actionTargetSlot.validatePotentialTarget(orgEntity, scanEntry.getChaosTarget());
            if(!isValid){
                scanEntry.setScore(targetSlotId, -1);
                return;
            }

        }
        scanEntry.setScore(targetSlotId, this.getCurrentValue());

    }
    @Override
    public void parseData(JSONObject jsonObject){
        executeSide = ExecuteSide.Client;
        super.parseData(jsonObject);
        targetSlotId = jsonObject.get("targetSlotId").toString();
    }
}
