package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.EntityOrganism;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.RayTraceResult;

/**
 * Created by user1a on 12/8/18.
 */
public class AttackOutput extends OutputNeuron {

  @Override
  public void execute() {
    if (this._lastValue <= .5) {
      return;
    }
    EntityOrganism entity = this.nNet.entity;
    List<EntityLiving> entities = entity.world.getEntitiesWithinAABB(EntityLiving.class,
        entity.getEntityBoundingBox().grow(2.0D, 1.0D, 2.0D));
    //

    for (EntityLiving target : entities) {
      if (target != nNet.entity) {
        if (nNet.entity.getDebug()) {
          ChaosCraft.logger
              .info(nNet.entity.getCCNamespace() + " Attempting to Attack: " + target.getName());
        }

        RayTraceResult rayTraceResult = entity.isEntityInLineOfSight(target, 3d);
        if (rayTraceResult != null) {
          entity.attackEntityAsMob(target);
          if (nNet.entity.getDebug()) {
            ChaosCraft.logger.info(nNet.entity.getCCNamespace() + "Attacked: " + target.getName());
          }
        }
      }

    }

  }
}
