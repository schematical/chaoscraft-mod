package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.util.math.Vec3d;
import software.amazon.ion.Decimal;

/**
 * Created by user1a on 12/10/18.
 */
public class WalkForwardOutput extends OutputNeuron {
    @Override
    public void execute() {
        if(this._lastValue == 0){
            return;
        }
        ChaosCraft.logger.info(nNet.entity.getName() + " Walking Forward: " + this._lastValue);
        //Pulled from net.minecraft.pathfinding.PathNavigate.onUpdateNavigation#263
        //this.entity.getMoveHelper().setMoveTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);

        nNet.entity.moveForward = this._lastValue * 0.8f;
        /*
        Vec3d vec3d = nNet.entity.getPositionVector();
        double distance = (this._lastValue > 0)? 1: -1;

        vec3d.add(new Vec3d(distance, 0D, 0D));

        vec3d.rotateYaw(nNet.entity.rotationYaw);
        nNet.entity.getMoveHelper().setMoveTo(vec3d.x, vec3d.y, vec3d.z, Math.abs(this._lastValue) * 0.8D);
        */
    }
}
