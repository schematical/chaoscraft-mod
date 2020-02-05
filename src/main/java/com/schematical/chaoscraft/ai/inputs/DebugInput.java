package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class DebugInput extends InputNeuron {


    private float value = -1;

    @Override
    public float evaluate(){
        setCurrentValue(value);
        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        value = Float.parseFloat(jsonObject.get("value").toString());
       

    }

}
