package com.schematical.chaoscraft.ai.outputs.rawnav;

import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import net.minecraft.entity.LivingEntity;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public class UseRawOutputNeuron extends OutputNeuron {

    @Override
    public void execute() {
        boolean sendRawOutput = false;
        if(this.getCurrentValue() > .5f){
            sendRawOutput = true;
        }
        this.nNet.entity.setSendRawOutput(sendRawOutput);

    }
    @Override
    public void parseData(JSONObject jsonObject){
        executeSide = ExecuteSide.Client;
        super.parseData(jsonObject);

    }

}
