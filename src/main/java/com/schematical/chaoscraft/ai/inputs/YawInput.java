package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class YawInput extends InputNeuron {
    private static final int  YAW_DEGREES = 365;
    public Float yawOffset;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range
        float yawAdjusted = yawOffset + this.nNet.entity.rotationYaw;
        float yawDiff = Math.abs(yawAdjusted - yawOffset);
        setCurrentValue(yawDiff /YAW_DEGREES);
        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        yawOffset = 0f;//Float.parseFloat(jsonObject.get("yawOffset").toString());
    }

}
