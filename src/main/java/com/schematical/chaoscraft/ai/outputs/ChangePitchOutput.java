package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.util.math.MathHelper;

/**
 * Created by user1a on 12/10/18.
 */
public class ChangePitchOutput extends OutputNeuron {
    @Override
    public void execute() {
        if(this._lastValue == 0){
            return;
        }

        float delta = ((this._lastValue * 2) -1) * 90;
        //ChaosCraft.logger.info(nNet.entity.getName() + " ChangePitchOutput: " + this._lastValue + " - " + delta);

        this.nNet.entity.setDesiredPitch(delta);
        //this.nNet.entity.rotationPitch += delta;
    }
}
