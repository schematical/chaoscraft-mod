package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class PitchInput extends InputNeuron {
    private static final int  YAW_DEGREES = 365;
    public Float pitchOffset;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range
        float yawAdjusted = pitchOffset + this.nNet.entity.rotationPitch;
        float yawDiff = Math.abs(yawAdjusted - pitchOffset);
        _lastValue = yawDiff /YAW_DEGREES;
        return _lastValue;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        pitchOffset = 0f;//Float.parseFloat(jsonObject.get("pitchOffset").toString());
    }

}
