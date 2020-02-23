package com.schematical.chaoscraft.ai.outputs.targetnet;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.Eye;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/10/18.
 *
 */

public class ScoreTargetSlotOutput extends OutputNeuron {
    private String targetSlotId;

    @Override
    public void execute() {

        //TODO: Decide how to get the scan manager

        ScanManager scanManager = new ScanManager(((OrgEntity)this.getEntity()).getClientOrgManager());  //TODO: REMOVE SUPER HACKY FAKE ... mat must sleep now
        ScanManager.ScanEntry scanEntry = scanManager.getFocusedScanEntry();
        scanEntry.setScore(targetSlotId, this.getCurrentValue());
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        targetSlotId = jsonObject.get("targetSlotId").toString();
    }
}
