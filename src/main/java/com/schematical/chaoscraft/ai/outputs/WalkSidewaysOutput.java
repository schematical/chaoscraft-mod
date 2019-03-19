package com.schematical.chaoscraft.ai.outputs;
import com.schematical.chaoscraft.Enum;
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
        float reversedValue = Math.abs((this._lastValue * 2)-1);
        if(reversedValue < ChaosCraft.activationThreshold){
            return;
        }

        //Pulled from net.minecraft.pathfinding.PathNavigate.onUpdateNavigation#263
        //this.entity.getMoveHelper().setMoveTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);


        nNet.entity.moveStrafing = reversedValue * Enum.SPEED;
        //ChaosCraft.logger.info(nNet.entity.getName() + " Walking Sideways: " + this._lastValue + " - " +  nNet.entity.moveStrafing);
        nNet.entity.getMoveHelper().strafe(nNet.entity.moveForward, nNet.entity.moveStrafing);

    }
}
