package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;

/**
 * Created by user1a on 12/10/18.
 */
public class ChangeYawOutput extends OutputNeuron {
    @Override
    public void execute() {
        if(this._lastValue == 0){
            return;
        }
        this.nNet.entity.rotationYaw += this._lastValue;

    }
}
