package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.Eye;
import com.schematical.chaoscraft.tickables.OrgPositionManager;
import org.json.simple.JSONObject;


import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public class IsLookingAtInput extends InputNeuron {

    public String attributeId;
    public String attributeValue;
    private boolean useDistanceAsValue = true;
    public Eye eye;
    private String eyeId;

    //public PositionRange positionRange;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range
        /*if(this.nNet.entity.getDebug()){
            //ChaosCraft.logger.info("Debugging...");
        }*/
        if(eye == null){
            ChaosCraft.LOGGER.error("Missing: " + eyeId);
            return getCurrentValue();
        }
        List<CCObserviableAttributeCollection> attributeCollections = null;
        float newVal = getCurrentValue();
        switch(attributeId){
            case(CCAttributeId.BLOCK_ID):
                attributeCollections = eye.canSeenBlocks();
                for(CCObserviableAttributeCollection attributeCollection: attributeCollections) {
                    if (attributeValue.equals(attributeCollection.resourceId)) {
                        if(useDistanceAsValue) {
                            double dist = attributeCollection.getDist(nNet.entity);
                            float distVal = 1 - (float)dist / eye.maxDistance;
                            if(distVal > newVal){
                                newVal = distVal;
                            }
                        }else{
                            setCurrentValue(1);
                        }
                    }
                }

            break;
            case(CCAttributeId.ENTITY_ID):
                attributeCollections = eye.canSeenEntities();
                for(CCObserviableAttributeCollection attributeCollection: attributeCollections) {
                    if (attributeValue.equals(attributeCollection.resourceId)) {
                        if(useDistanceAsValue) {
                            double dist = attributeCollection.getDist(nNet.entity);
                            float distVal = 1 - (float)dist / eye.maxDistance;
                            if(distVal > newVal){
                                newVal = distVal;
                            }
                        }else{
                            setCurrentValue(1);
                        }
                    }
                }

            break;
            case(CCAttributeId.BLOCK_TOUCH_STATE):
                attributeCollections = eye.canSeenBlocks();
                for(CCObserviableAttributeCollection attributeCollection: attributeCollections) {
                    //This should be client side

                    OrgPositionManager orgPositionManager = (OrgPositionManager)nNet.entity.getClientOrgManager().getTickable(OrgPositionManager.class);
                    if(orgPositionManager.hasTouchedBlock(attributeCollection.position)) {
                        if (useDistanceAsValue) {
                            double dist = attributeCollection.getDist(nNet.entity);
                            float distVal = 1 - (float) dist / eye.maxDistance;
                            if (distVal > newVal) {
                                newVal = distVal;
                            }
                        } else {
                            setCurrentValue(1);
                        }
                    }
                }


                break;
            default:
                ChaosCraft.LOGGER.error("Invalid `attributeId`: " + attributeId);

        }
        setCurrentValue(newVal);
        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();
        eyeId = jsonObject.get("eye").toString();

        if(!nNet.biology.containsKey(eyeId)){
            ChaosCraft.LOGGER.error("Invalid Eye Id: " +eyeId);
        }
        eye = (Eye)nNet.biology.get(eyeId);

    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.attributeId + " " + this.attributeValue + " E" + this.eyeId + "=> " + this.getCurrentValue();
        return response;

    }

}
