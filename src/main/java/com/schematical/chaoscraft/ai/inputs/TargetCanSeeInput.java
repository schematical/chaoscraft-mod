package com.schematical.chaoscraft.ai.inputs;

import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetCanSeeInput extends BaseTargetInputNeuron {

    @Override
    public float evaluate(){


        if(getTarget() == null) {
            return getCurrentValue();
        }

        Vec3d vec3d = this.getEntity().getEyePosition(1);
        Vec3d vec3d1 = this.getTarget().getTargetPosition();
        boolean canBeSeen = this.getEntity().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this.getEntity())).getType() == RayTraceResult.Type.MISS;

        if(canBeSeen) {
            setCurrentValue(1);
        }



        return getCurrentValue();
    }



}
