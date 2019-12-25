package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;

/**
 * Created by user1a on 12/10/18.
 */
public class WalkSidewaysOutput extends OutputNeuron {
    @Override
    public void execute() {
        float reversedValue = this.reverseSigmoid(this._lastValue);
        if(Math.abs(reversedValue) < ChaosCraft.activationThreshold){
            nNet.entity.moveStrafing = 0;
            return;
        }



        nNet.entity.moveStrafing = reversedValue * Enum.SPEED;
        //ChaosCraft.logger.info(nNet.entity.getName() + " Walking Sideways: " + this._lastValue + " - " +  nNet.entity.moveStrafing);
        nNet.entity.getMoveHelper().strafe(nNet.entity.moveForward, nNet.entity.moveStrafing);

    }
}
