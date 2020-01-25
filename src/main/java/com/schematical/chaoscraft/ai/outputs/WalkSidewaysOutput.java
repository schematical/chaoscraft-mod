package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.Vec3d;

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



        nNet.entity.moveStrafing = reversedValue * 0.25f;
        //nNet.entity.getMoveHelper().strafe(nNet.entity.moveForward, nNet.entity.moveStrafing);
        nNet.entity.move(MoverType.SELF, new Vec3d(nNet.entity.moveForward, 0,  nNet.entity.moveStrafing));
    }
}
