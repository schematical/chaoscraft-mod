package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class HealthInput extends InputNeuron {

    @Override
    public float evaluate(){

        _lastValue = (nNet.entity.getHealth() / nNet.entity.getMaxHealth()) * 2 - 1;
        return _lastValue;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

    }

}
