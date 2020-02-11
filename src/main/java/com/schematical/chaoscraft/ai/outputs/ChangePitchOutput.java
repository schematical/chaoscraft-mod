package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;

/**
 * Created by user1a on 12/10/18.
 */
public class ChangePitchOutput extends OutputNeuron {
    @Override
    public void execute() {
        float delta = reverseSigmoid(this.getCurrentValue());

        this.nNet.entity.setDesiredPitch(delta * 90 );

        //this.nNet.entity.rotationPitch += delta;
    }
}
