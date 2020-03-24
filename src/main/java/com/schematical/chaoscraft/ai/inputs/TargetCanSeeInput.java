package com.schematical.chaoscraft.ai.inputs;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockRayTraceResult;
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
        if(vec3d1 == null) {
            return getCurrentValue();
        }
        Entity entity = this.getEntity();
        BlockRayTraceResult blockRayTraceResult = entity.world.rayTraceBlocks(
                new RayTraceContext(
                        vec3d,
                        vec3d1,
                        RayTraceContext.BlockMode.COLLIDER,
                        RayTraceContext.FluidMode.NONE,
                        this.getEntity()
                )
        );
        boolean canBeSeen = blockRayTraceResult.getType() == RayTraceResult.Type.MISS;

        if(canBeSeen) {
            setCurrentValue(1);
        }



        return getCurrentValue();
    }



}
