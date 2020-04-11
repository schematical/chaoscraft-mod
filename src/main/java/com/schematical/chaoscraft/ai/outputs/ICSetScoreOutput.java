package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/10/18.
 *
 */

public class ICSetScoreOutput extends OutputNeuron {

    private String targetSlotId;
    private ActionTargetSlot actionTargetSlot;
    private boolean loaded = false;
    @Override
    public void execute() {


        OrgEntity orgEntity =  ((OrgEntity)this.getEntity());
        ClientOrgManager clientOrgManager = orgEntity.getClientOrgManager();
        ScanManager scanManager = clientOrgManager.getScanManager();

        ScanEntry scanEntry = scanManager.getScanItemInstance().getFocusedScanEntry();

        if(scanEntry == null) {
            throw new ChaosNetException("ScanEntity should not be null if this is firing");
        }

        scanEntry.setScore(targetSlotId, this.getCurrentValue());

    }
    @Override
    public float evaluate(){
        if(!loaded) {
            BiologyBase biologyBase = this.nNet.getBiology(targetSlotId);
            if(biologyBase == null){
               throw new ChaosNetException("No `actionTargetSlot` with id " + targetSlotId );
            }
            if (biologyBase instanceof ActionTargetSlot) {
                actionTargetSlot = (ActionTargetSlot) biologyBase;
                if (actionTargetSlot == null) {
                    throw new ChaosNetException("Cannot find `actionTargetSlot`: " + targetSlotId);
                }
            }else{
                throw new ChaosNetException("Invalid `actionTargetSlot`: " + targetSlotId + " - " + biologyBase.getClass().getSimpleName());
            }
            loaded = true;
        }
        if(getHasBeenEvaluated()){
            return getCurrentValue();
        }

        OrgEntity orgEntity =  ((OrgEntity)this.getEntity());
        ClientOrgManager clientOrgManager = orgEntity.getClientOrgManager();
        ScanManager scanManager = clientOrgManager.getScanManager();

        ScanEntry scanEntry = scanManager.getScanItemInstance().getFocusedScanEntry();
        if(scanEntry == null) {
            throw new ChaosNetException("ScanEntity should not be null if this is firing");
        }
        ChaosTargetItem chaosTargetItem = null;
        if(scanEntry.recipe != null){
            chaosTargetItem = new ChaosTargetItem(scanEntry.recipe);
        }else if(scanEntry.targetSlot != null){
            chaosTargetItem = new ChaosTargetItem(scanEntry.targetSlot);
        }else{
            throw new ChaosNetException("Invalid `scanEntry` for item scan");
        }
        if (actionTargetSlot == null) {
            throw new ChaosNetException("Null `actionTargetSlot`: " + targetSlotId);
        }
        boolean isValid = actionTargetSlot.validateTargetItem((OrgEntity)getEntity(),chaosTargetItem);
        if(!isValid){
            scanEntry.setScore(targetSlotId, this.getCurrentValue());
            return getCurrentValue();
        }


        return super.evaluate();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        executeSide = ExecuteSide.Client;
        super.parseData(jsonObject);
        targetSlotId = jsonObject.get("targetSlotId").toString();
    }
}
