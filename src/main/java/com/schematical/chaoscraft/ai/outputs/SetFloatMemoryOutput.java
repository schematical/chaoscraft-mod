package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.FloatMemory;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class SetFloatMemoryOutput extends OutputNeuron {

    private String memoryId;



    @Override
    public void execute() {
        float reversedValue = Math.abs((this._lastValue * 2)-1);
        if(reversedValue < ChaosCraft.activationThreshold){
            return;
        }
        FloatMemory floatMemory = (FloatMemory) nNet.getBiology(memoryId);
        floatMemory.setValue(reversedValue);

    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        memoryId = jsonObject.get("memoryId").toString();
    }


}
