package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/10/18.
 *
 */

public class TargetCandidateSetScoreOutput extends OutputNeuron {
    private String targetSlotId;

    @Override
    public void execute() {


        OrgEntity orgEntity =  ((OrgEntity)this.getEntity());
        ClientOrgManager clientOrgManager = orgEntity.getClientOrgManager();
        ScanManager scanManager = clientOrgManager.getScanManager();
        ScanManager.ScanEntry scanEntry = scanManager.getFocusedScanEntry();
        if(scanEntry != null) {
            scanEntry.setScore(targetSlotId, this.getCurrentValue());
        }
    }
    @Override
    public void parseData(JSONObject jsonObject){
        executeSide = ExecuteSide.Client;
        super.parseData(jsonObject);
        targetSlotId = jsonObject.get("targetSlotId").toString();
    }
}
