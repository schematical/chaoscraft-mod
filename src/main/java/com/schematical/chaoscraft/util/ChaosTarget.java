package com.schematical.chaoscraft.util;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.entities.OrgEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;

public class ChaosTarget {
    private Entity targetEntity;
    private BlockPos blockPos;
    public ChaosTarget() {
    }

    public ChaosTarget(Entity entity) {
        targetEntity = entity;
    }
    public ChaosTarget(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void setTarget(Entity entity){
        this.blockPos = null;
        this.targetEntity = entity;
    }
    public void setTarget(BlockPos blockPos){
        this.targetEntity = null;
        this.blockPos = blockPos;
    }
    public Vec3d getTargetPosition(){
        if(targetEntity != null){
            return targetEntity.getPositionVector();
        }
        if(blockPos != null) {
            return new Vec3d(
                    this.blockPos.getX(),
                    this.blockPos.getY(),
                    this.blockPos.getZ()
            );
        }
        return null;
    }
    public Vec3d getTargetPositionCenter(){
        if(targetEntity != null){
            Vec3d vec3d = targetEntity.getPositionVector();
            AxisAlignedBB box = targetEntity.getBoundingBox();
            return vec3d.add(
                    box.getCenter()
            );
        }
        if(blockPos != null) {
            return new Vec3d(
                    this.blockPos.getX() + .5f,
                    this.blockPos.getY()+ .5f,
                    this.blockPos.getZ()+ .5f
            );
        }
        return null;
    }
    public CCObserviableAttributeCollection getObservedAttributes(OrgEntity orgEntity){
        if(this.targetEntity != null){
            return orgEntity.observableAttributeManager.Observe(this.targetEntity);
        }
        if(this.blockPos != null){
            return orgEntity.observableAttributeManager.Observe(this.blockPos, orgEntity.world);
        }
        return null;

    }


    public Entity getTargetEntity() {
        return targetEntity;
    }


    public BlockPos getTargetBlockPos() {
        return blockPos;
    }
    public boolean canEntityTouch(OrgEntity orgEntity){
        BlockPos myPos = orgEntity.getPosition();
        double distTo = blockPos.distanceSq(myPos);
        boolean withInDist = distTo < OrgEntity.REACH_DISTANCE;
        if (
            // !this.world.getWorldBorder().contains(pos) ||
            !withInDist
        ) {
            return false;
        }
        return true;
    }


    public Double getYawDelta(Entity entity) {
        Vec3d targetPosition = getTargetPositionCenter();
        if (targetPosition == null) {
            return null;
        }
        return TargetHelper.getYawDelta(
                targetPosition,
                entity.getEyePosition(1),
                entity.rotationYaw
        );

    }

    public Double getPitchDelta(Entity entity) {
        Vec3d targetPosition = getTargetPositionCenter();
        if (targetPosition == null) {
            return null;
        }

        return TargetHelper.getPitchDelta(
                targetPosition,
                entity.getEyePosition(1),//getEntity().getPositionVec(),
                entity.rotationPitch //getEntity().getLookVec()
        );
    }

    public Double getDist(Entity entity) {
        Vec3d targetPosition = getTargetPositionCenter();
        if (targetPosition == null) {
            return null;
        }
        return TargetHelper.getDistDelta(
                entity.getPositionVector(),
                targetPosition
        );
    }
    public boolean hasTarget() {
        if(targetEntity != null){
            return true;
        }
        if(blockPos != null){
            return true;
        }
        return false;
    }


    public boolean isEntityLookingAt(OrgEntity orgEntity) {
        Vec3d vec3d = orgEntity.getPositionVec();
        Vec3d vec3d1 = getTargetPosition();
        boolean canBeSeen = orgEntity.world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, orgEntity)).getType() == RayTraceResult.Type.MISS;

        if(!canBeSeen) {
            return false;
        }

        return true;
    }
}
