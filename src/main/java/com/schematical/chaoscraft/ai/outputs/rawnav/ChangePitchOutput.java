package com.schematical.chaoscraft.ai.outputs.rawnav;

import com.schematical.chaoscraft.ai.OutputNeuron;

/**
 * Created by user1a on 12/10/18.
 */
public class ChangePitchOutput extends RawOutputNeuron {
    @Override
    public void execute() {
        float delta = reverseSigmoid(this.getCurrentValue());

        this.getEntity().setDesiredPitch(delta * 45 );


    }
}
