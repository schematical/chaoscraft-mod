package com.schematical.chaoscraft.ai.outputs.rawnav;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;

/**
 * Created by user1a on 12/10/18.
 */
public class JumpOutput extends RawOutputNeuron {
    @Override
    public void execute() {
        if(this.getCurrentValue() <= .5f){
            return;
        }
        //ChaosCraft.logger.info(nNet.entity.getName() + " Jumping: " + this._lastValue);
        this.getEntity()._jump();
    }
}
