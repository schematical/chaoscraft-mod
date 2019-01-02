package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Created by user1a on 12/10/18.
 */
public class WalkSidewaysOutput extends OutputNeuron {
    @Override
    public void execute() {
        if(this._lastValue == 0){
            return;
        }
        ChaosCraft.logger.info(nNet.entity.getName() + " Walking Sideways: " + this._lastValue);
        //Pulled from net.minecraft.pathfinding.PathNavigate.onUpdateNavigation#263
        //this.entity.getMoveHelper().setMoveTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);


        nNet.entity.moveStrafing =  MathHelper.clamp(this._lastValue * 0.8f, -1, 1);
        nNet.entity.getMoveHelper().strafe(nNet.entity.moveForward, nNet.entity.moveStrafing);
        /*
        Vec3d vec3d = nNet.entity.getPositionVector();

        double distance = (this._lastValue > 0)? 1: -1;
        vec3d.add(new Vec3d(0D, 0D, distance));
        vec3d.rotateYaw(nNet.entity.rotationYaw);
        nNet.entity.getMoveHelper().setMoveTo(vec3d.x, vec3d.y, vec3d.z, Math.abs(this._lastValue) * 0.8D);
        */
    }
}
