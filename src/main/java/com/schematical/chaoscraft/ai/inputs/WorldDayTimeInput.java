package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class WorldDayTimeInput extends InputNeuron {

    @Override
    public float evaluate(){
        long worldTime = nNet.entity.world.getDayTime();
        float remainder = (worldTime % (24000l));
       setCurrentValue(remainder/24000l);
        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

    }

}
