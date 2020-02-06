package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class PitchInput extends InputNeuron {
    private static final int  YAW_DEGREES = 180;


    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range

        setCurrentValue(this.nNet.entity.rotationPitch /YAW_DEGREES);
        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

    }

}
