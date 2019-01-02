package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;

/**
 * Created by user1a on 12/10/18.
 */
public class ChangePitchOutput extends OutputNeuron {
    @Override
    public void execute() {
        if(this._lastValue == 0){
            return;
        }
        ChaosCraft.logger.info(nNet.entity.getName() + " ChangePitchOutput: " + this._lastValue);
        this.nNet.entity.rotationPitch += this._lastValue * 90;
    }
}
