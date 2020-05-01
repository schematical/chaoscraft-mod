package com.schematical.chaoscraft.ai.outputs.rawnav;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.Vec3d;

/**
 * Created by user1a on 12/10/18.
 */
public class WalkSidewaysOutput extends RawOutputNeuron  {
    @Override
    public void execute() {
        float reversedValue = this.reverseSigmoid(this.getCurrentValue());

        if(Math.abs(reversedValue) < ChaosCraft.activationThreshold){
           getEntity().moveStrafing = 0;
            return;
        }



        getEntity().moveStrafing = reversedValue * 2;
        getEntity().getMoveHelper().strafe(getEntity().moveForward, getEntity().moveStrafing);
        //nNet.entity.move(MoverType.SELF, new Vec3d(nNet.entity.moveForward, 0,  nNet.entity.moveStrafing));
        //nNet.entity.setMotion(new Vec3d(nNet.entity.moveForward, 0, nNet.entity.moveStrafing));
    }
}
