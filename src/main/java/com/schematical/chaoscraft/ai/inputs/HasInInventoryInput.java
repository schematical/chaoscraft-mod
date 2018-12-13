package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.util.PositionRange;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class HasInInventoryInput extends InputNeuron {
    public String attributeId;
    public String attributeValue;
    public PositionRange positionRange;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range

        return -1;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();
        positionRange = new PositionRange();
        positionRange.parseData((JSONObject) jsonObject.get("positionRange"));
    }

}
