package com.schematical.chaoscraft.util;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

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
            //Vec3d vec3d = targetEntity.getPositionVector();
            AxisAlignedBB box = targetEntity.getBoundingBox();
            return box.getCenter();
            /*return vec3d.add(
                    box.getCenter()
            );*/
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
        Vec3d myPos = orgEntity.getPositionVector();
        Vec3d targetPosCenter = getTargetPositionCenter();
        if(targetPosCenter == null){
            return false;
        }
        double distTo = targetPosCenter.distanceTo(myPos);
        boolean withInDist = distTo < OrgEntity.REACH_DISTANCE - 1;
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


    public boolean isVisiblyBlocked(OrgEntity orgEntity) {
        Vec3d vec3d = orgEntity.getPositionVec();
        Vec3d vec3d1 = getTargetPositionCenter();
        boolean canBeSeen = orgEntity.world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, orgEntity)).getType() == RayTraceResult.Type.MISS;

        if(!canBeSeen) {
            return false;
        }

        return true;
    }
    public String toString(){

        String message = "";
        if(getTargetEntity() != null){
            message += getTargetEntity().getType().getRegistryName().toString();
        }else if(getTargetBlockPos() != null){

            BlockState blockState = Minecraft.getInstance().world.getBlockState(getTargetBlockPos());
            message += blockState.getBlock().getRegistryName().toString();
        }else{
            message += "null";
        }

        return message;
    }

    public String getSerializedString() {
        if(getTargetEntity() != null){
            return "entity:" + getTargetEntity().getEntityId();
        }else if(getTargetBlockPos() != null){

            BlockPos blockPos = getTargetBlockPos();
            return "blockpos:" + blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ();
        }else{
            return "null";
        }

    }
    public static ChaosTarget deserializeTarget(World world, String payload) {
        ChaosTarget chaosTarget = new ChaosTarget();
        String[] parts = payload.split(":");
        switch(parts[0]){
            case("entity"):
                chaosTarget.targetEntity = world.getEntityByID(Integer.parseInt(parts[1]));
                break;
            case("blockpos"):
                String[] parts2 = parts[1].split(",");
                chaosTarget.blockPos = new BlockPos(
                    Integer.parseInt(parts2[0]),
                    Integer.parseInt(parts2[1]),
                    Integer.parseInt(parts2[2])
                );
                break;
            case("null"):
                throw new ChaosNetException("TODO: Figure out what to do with this");
            default:
                throw new ChaosNetException("Invalid TargetType: " + parts[0]);
        }
       return chaosTarget;

    }

    public boolean isEntityLookingAt(OrgEntity orgEntity) {
        if(blockPos != null) {
            BlockRayTraceResult rayTraceResult = orgEntity.rayTraceBlocks(orgEntity.REACH_DISTANCE);
            if (rayTraceResult == null) {
                return false;
            }
            if(!rayTraceResult.getPos().equals(blockPos)){
                return false;
            }
            return true;
        }else if(targetEntity != null){
            Vec3d vec3d = orgEntity.getEyePosition(1);
            Vec3d vec3d1 = orgEntity.getLook(1);
            Vec3d vec3d2 = vec3d.add(
                    new Vec3d(
                            vec3d1.x * orgEntity.REACH_DISTANCE,
                            vec3d1.y * orgEntity.REACH_DISTANCE,
                            vec3d1.z * orgEntity.REACH_DISTANCE
                    )
            );

            return targetEntity.getBoundingBox().rayTrace(vec3d, vec3d2).isPresent();
        }else{
            //throw new ChaosNetException("No available targets");
            return false;
        }

    }
}
