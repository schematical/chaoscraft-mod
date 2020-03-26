package com.schematical.chaoscraft.ai.outputs.rawnav;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;

/**
 * Created by user1a on 12/10/18.
 */
public class ChangeHeadYawOutput extends OutputNeuron {
    @Override
    public void execute() {

        float delta = reverseSigmoid(getCurrentValue());

        if(Math.abs(delta) < ChaosCraft.activationThreshold){
            return;
        }


        this.nNet.entity.setDesiredHeadYaw(delta  * 90);

    }
}
