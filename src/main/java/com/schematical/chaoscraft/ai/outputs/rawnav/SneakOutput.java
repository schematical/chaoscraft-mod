package com.schematical.chaoscraft.ai.outputs.rawnav;

import com.schematical.chaoscraft.ai.OutputNeuron;

public class SneakOutput extends OutputNeuron {
    public void execute() {
        if(this.getCurrentValue() <= .5){
            nNet.entity.setSneaking(false);
            return;
        }
        nNet.entity.setSneaking(true);
    }
}
