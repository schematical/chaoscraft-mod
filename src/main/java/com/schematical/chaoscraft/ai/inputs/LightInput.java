package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class LightInput extends InputNeuron {

    @Override
    public float evaluate(){
        int light = nNet.entity.world.getLight(nNet.entity.getPosition());
        setCurrentValue(((float) light)/ 15f);
        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

    }

}
