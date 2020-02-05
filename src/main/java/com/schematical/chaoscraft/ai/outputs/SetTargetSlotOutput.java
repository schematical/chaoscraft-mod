package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.Eye;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/10/18.
 */
public class SetTargetSlotOutput extends OutputNeuron {
    private String targetSlotId;

    @Override
    public void execute() {
        if(this.getCurrentValue() <= .5){
            return;
        }
        Eye eye = (Eye)nNet.getBiology("Eye_0");
        TargetSlot targetSlot = (TargetSlot) nNet.getBiology(targetSlotId);
        CCObserviableAttributeCollection atts = eye.getClosestSeenObject();
        if(atts == null){
            return;
        }
        switch(atts.resourceType){
            case("BLOCK"):
                //Just use the position
                if(atts.position == null){
                    throw new ChaosNetException("Invalid `CCObserviableAttributeCollection` passed in. Missing `position`");
                }
                targetSlot.setTarget(atts.position);
            break;
            case("ENTITY"):
                targetSlot.setTarget(atts._entity);
            break;
            default:
                throw new ChaosNetException("Invalid `CCObserviableAttributeCollection.resourceType`: " + atts.resourceType);
        }
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        targetSlotId = jsonObject.get("targetSlotId").toString();
    }
}
