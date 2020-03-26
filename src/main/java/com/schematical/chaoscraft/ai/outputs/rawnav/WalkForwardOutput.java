package com.schematical.chaoscraft.ai.outputs.rawnav;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.Vec3d;

/**
 * Created by user1a on 12/10/18.
 */
public class WalkForwardOutput extends OutputNeuron {
    @Override
    public void execute() {
        float reversedValue = this.reverseSigmoid(this.getCurrentValue());


        if(Math.abs(reversedValue) < ChaosCraft.activationThreshold){
            nNet.entity.moveForward = 0;
            return;
        }

        //Pulled from net.minecraft.pathfinding.PathNavigate.onUpdateNavigation#263
        //this.entity.getMoveHelper().setMoveTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);

        nNet.entity.moveForward = reversedValue * 2;
        //ChaosCraft.logger.info(nNet.entity.getName() + " Walking Forward: " + this._lastValue + " - " + nNet.entity.moveForward);
        nNet.entity.getMoveHelper().strafe(nNet.entity.moveForward, nNet.entity.moveStrafing);
        //nNet.entity.move(MoverType.SELF, new Vec3d(nNet.entity.moveForward, 0, nNet.entity.moveStrafing));
        //nNet.entity.setMotion(new Vec3d(nNet.entity.moveForward, 0, nNet.entity.moveStrafing));
    }
}
