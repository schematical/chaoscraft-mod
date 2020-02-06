package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class YawHeadInput extends InputNeuron {
    private static final int  YAW_DEGREES = 360;


    @Override
    public float evaluate(){


       setCurrentValue( this.nNet.entity.rotationYawHead /YAW_DEGREES);
        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
       }

}
