package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public class AttackOutput extends OutputNeuron {
    @Override
    public void execute() {
        if(this._lastValue <= 0){
            return;
        }
        EntityOrganism entity = this.nNet.entity;
        List<EntityLiving> entities = entity.world.getEntitiesWithinAABB(EntityLiving.class, entity.getEntityBoundingBox().grow(2.0D, 1.0D, 2.0D));
        //
        RayTraceResult rayTraceResult = nNet.entity.rayTraceBlocks(nNet.entity.REACH_DISTANCE);
        for (EntityLiving target : entities) {
           BlockPos targetBlockPos = target.getPosition();
           if(rayTraceResult.getBlockPos().equals(targetBlockPos)){
               entity.attackEntityAsMob(target);
           }

        }

    }
}
