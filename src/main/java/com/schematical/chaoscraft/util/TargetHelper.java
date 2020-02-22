package com.schematical.chaoscraft.util;

import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.*;

import java.util.List;

public class TargetHelper {
    public int maxDistance = 40;
    public TargetHelper(){

    }
    public Entity getTarget(iHasAttributeIdValue iX){
        switch(iX.getAttributeId()){
           /* case(CCAttributeId.BLOCK_ID):


                Vec3d vec3d = iX.getEntity().getEyePosition(1);
                Vec3i vec3i = new Vec3i(
                        (int)vec3d.getX(),
                        (int)vec3d.getY(),
                        (int)vec3d.getZ()
                );



               for(int x = vec3i.getX() - maxDistance; x < vec3i.getX() + maxDistance; x++){
                   for(int y = vec3i.getY() - maxDistance; y < vec3i.getY() + maxDistance; y++) {
                       for (int z = vec3i.getZ() - maxDistance; z < vec3i.getZ() + maxDistance; z++) {
                            BlockPos blockPos = new BlockPos(x,y,z);
                           BlockState blockState = iX.getEntity().world.getBlockState(
                                   blockPos
                           );
                           Block block = blockState.getBlock();

                           CCObserviableAttributeCollection attributeCollection = ((OrgEntity)iX.getEntity()).observableAttributeManager.Observe(block);
                           attributeCollection.position = new Vec3d(
                                   blockPos.getX(),
                                   blockPos.getY(),
                                   blockPos.getZ()
                           );



                       }
                   }
                }
            // break;*/
            case(CCAttributeId.ENTITY_ID):

                AxisAlignedBB grownBox = iX.getEntity().getBoundingBox().grow(maxDistance, maxDistance, maxDistance);
                List<Entity> entities =  iX.getEntity().world.getEntitiesWithinAABB(LivingEntity.class,  grownBox);
                entities.addAll(iX.getEntity().world.getEntitiesWithinAABB(ItemEntity.class,  grownBox));
                Entity closestEntity = null;
                double closestEntityDist = 10000;
                int entityCount = 0;
                for (Entity entity : entities) {
                    String entityId =  entity.getType().getRegistryName().getNamespace() + ":" + entity.getType().getRegistryName().getPath();
                    if(entityId.equals(iX.getAttributeValue())){
                        entityCount += 1;
                        double dist = iX.getEntity().getPositionVec().distanceTo(entity.getPositionVec());
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
                throw new ChaosNetException("Invalid `attributeId`: " + iX.getAttributeId());
        }

        return null;
    }
    public Double getYawDelta(iHasAttributeIdValue x){
        Entity targetEntity =  getTarget(x);
        if(targetEntity == null){
            return null;
        }
        //Vec3d lookVec = x.getEntity().getLookVec();
        Vec3d vecToTarget = targetEntity.getPositionVec().subtract(x.getEntity().getEyePosition(1));
        double yaw = - Math.atan2(vecToTarget.x, vecToTarget.z);
        double degrees = Math.toDegrees(yaw);

        //double lookYaw = - Math.atan2(lookVec.x, lookVec.z);
        double lookDeg = x.getEntity().rotationYaw;//Math.toDegrees(lookYaw);
        degrees -= lookDeg;
        degrees = degrees % 360;
        if(degrees > 180){
            degrees += -360;
        }else if(degrees < -180){
            degrees += 360;
        }
        return degrees;
    }
    public Double getPitchDelta(iHasAttributeIdValue x){
        Entity targetEntity =  getTarget(x);
        if(targetEntity == null){
            return null;
        }
        //Vec3d lookVec = x.getEntity().getLookVec();
        Vec3d vecToTarget = targetEntity.getPositionVec().subtract(x.getEntity().getEyePosition(1));
        double pitch = -Math.atan2((vecToTarget.y + .5), Math.sqrt(Math.pow(vecToTarget.x, 2) + Math.pow(vecToTarget.z, 2)));
        double degrees = Math.toDegrees(pitch);

        //double lookPitch = -Math.atan2(lookVec.y, Math.sqrt(Math.pow(lookVec.x, 2) + Math.pow(lookVec.z, 2)));
        double lookDeg =  x.getEntity().rotationPitch;//Math.toDegrees(lookPitch);
        degrees -= lookDeg;
        return degrees;
    }



    public Double getLookYawDelta(iHasAttributeIdValue x){
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
        if(degrees > 180){
            degrees += -360;
        }else if(degrees < -180){
            degrees += 360;
        }
        return degrees;
    }
    public Double getLookPitchDelta(iHasAttributeIdValue x){
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
