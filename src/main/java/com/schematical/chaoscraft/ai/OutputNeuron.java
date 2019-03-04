package com.schematical.chaoscraft.ai;

import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public abstract class OutputNeuron extends NeuronBase {
    public static final String OUPUT_GROUP_NONE = "OUPUT_GROUP_NONE";
    public String _outputGroup = "OUPUT_GROUP_NONE";
    public String _base_type(){
        return com.schematical.chaoscraft.Enum.OUTPUT;
    }
    public abstract void execute();
     @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        if(jsonObject.containsKey("$OUTPUT_GROUP")){
            _outputGroup = jsonObject.get("$OUTPUT_GROUP").toString();
        }

    }
}
