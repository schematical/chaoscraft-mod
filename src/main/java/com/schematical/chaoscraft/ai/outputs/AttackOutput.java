package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RayTraceResult;

import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public class AttackOutput extends OutputNeuron {
    @Override
    public void execute() {
        if(this._lastValue <= .5){
            return;
        }
        OrgEntity entity = this.nNet.entity;
        List<LivingEntity> entities = entity.world.getEntitiesWithinAABB(LivingEntity.class, entity.getCollisionBoundingBox().grow(2.0D, 1.0D, 2.0D));
        //



        for (LivingEntity target : entities) {
            if(target != nNet.entity) {
               /* if (nNet.entity.getDebug()) {
                    ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Attempting to Attack: " + target.getName());
                }*/

                RayTraceResult rayTraceResult = entity.isEntityInLineOfSight(target, 3d);
                if (rayTraceResult != null) {
                    entity.attackEntityAsMob(target);
                    /*if (nNet.entity.getDebug()) {
                        ChaosCraft.logger.info(nNet.entity.getCCNamespace() + "Attacked: " + target.getName());
                    }*/
                }
            }

        }

    }
}
