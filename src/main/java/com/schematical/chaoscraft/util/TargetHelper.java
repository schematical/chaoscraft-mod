package com.schematical.chaoscraft.util;

import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class TargetHelper {
    public int maxDistance = 20;
    public TargetHelper(){

    }
    public Entity getTarget(iHasAttributeIdValue x){
        switch(x.getAttributeId()){

            // break;
            case(CCAttributeId.ENTITY_ID):

                AxisAlignedBB grownBox = x.getEntity().getBoundingBox().grow(maxDistance, maxDistance, maxDistance);
                List<Entity> entities =  x.getEntity().world.getEntitiesWithinAABB(LivingEntity.class,  grownBox);
                entities.addAll(x.getEntity().world.getEntitiesWithinAABB(ItemEntity.class,  grownBox));
                Entity closestEntity = null;
                double closestEntityDist = 10000;
                int entityCount = 0;
                for (Entity entity : entities) {
                    String entityId =  entity.getType().getRegistryName().getNamespace() + ":" + entity.getType().getRegistryName().getPath();
                    if(entityId.equals(x.getAttributeValue())){
                        entityCount += 1;
                        double dist = x.getEntity().getPositionVec().distanceTo(entity.getPositionVec());
                        if(
                                closestEntity == null ||
                                closestEntityDist > dist
                        ){
                            closestEntity = entity;
                            closestEntityDist = dist;
                        }
                    }
                }
                if(closestEntity != null){
                    return closestEntity;
                }
                break;
            default:
                throw new ChaosNetException("Invalid `attributeId`: " + x.getAttributeId());
        }

        return null;
    }
    public Double getYawDelta(iHasAttributeIdValue x){
        Entity targetEntity =  getTarget(x);
        if(targetEntity == null){
            return null;
        }
        Vec3d lookVec = x.getEntity().getLookVec();
        Vec3d vecToTarget = targetEntity.getPositionVec().subtract(x.getEntity().getEyePosition(1));
        double yaw = - Math.atan2(vecToTarget.x, vecToTarget.z);
        double degrees = Math.toDegrees(yaw);

        double lookYaw = - Math.atan2(lookVec.x, lookVec.z);
        double lookDeg = Math.toDegrees(lookYaw);
        degrees -= lookDeg;
        return degrees;
    }
    public Double getPitchDelta(iHasAttributeIdValue x){
        Entity targetEntity =  getTarget(x);
        if(targetEntity == null){
            return null;
        }
        Vec3d lookVec = x.getEntity().getLookVec();
        Vec3d vecToTarget = targetEntity.getPositionVec().subtract(x.getEntity().getEyePosition(1));
        double pitch = -Math.atan2((vecToTarget.y + .5), Math.sqrt(Math.pow(vecToTarget.x, 2) + Math.pow(vecToTarget.z, 2)));
        double degrees = Math.toDegrees(pitch);

        double lookPitch = -Math.atan2(lookVec.y, Math.sqrt(Math.pow(lookVec.x, 2) + Math.pow(lookVec.z, 2)));
        double lookDeg = Math.toDegrees(lookPitch);
        degrees -= lookDeg;
        return degrees;
    }
    public Double getDist(iHasAttributeIdValue x){
      Entity targetEntity =  getTarget(x);
      if(targetEntity == null){
          return null;
      }
      return x.getEntity().getPositionVector().distanceTo(targetEntity.getPositionVec());
    }
}
