package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class BiasInput extends InputNeuron {
    //TODO: Add a specific slot
    public float weight;


    @Override
    public float evaluate(){
        this.setCurrentValue(weight);
        return this.getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject) {
        super.parseData(jsonObject);
        weight =  Float.parseFloat(jsonObject.get("weight").toString());

    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.weight;
        return response;

    }

}
