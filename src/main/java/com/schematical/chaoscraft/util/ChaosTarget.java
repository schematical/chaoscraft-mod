package com.schematical.chaoscraft.util;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

import java.util.ArrayList;

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
    public BlockPos getPosition(){
        if(targetEntity != null){
            return targetEntity.getPosition();
        }
        if(blockPos != null){
            return blockPos;
        }
        return null;
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
            return (new AxisAlignedBB(blockPos)).getCenter();
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
        BlockPos targetBlockPos = getPosition();
        if(targetBlockPos == null){
            return false;
        }
        if( targetBlockPos.withinDistance(myPos, orgEntity.REACH_DISTANCE - 1)){
            return true;
        }
        return false;
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

    public Double getLookYawDelta(Entity entity) {
        Vec3d targetPosition = getTargetPositionCenter();
        if (targetPosition == null) {
            return null;
        }
        return TargetHelper.getYawDelta(
                targetPosition,
                entity.getEyePosition(1),
                entity.getRotationYawHead()
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
            return true;
        }

        return false;
    }
    public boolean isSurroundedBySolid(World world) {
       if(targetEntity != null){
           return false;//Unlikely
       }
       if(blockPos == null){
           throw new ChaosNetException("`blockPos` should not be null if `targetEntity` is null");
       }

       boolean foundNotSolid = true;
        ArrayList<BlockState> blockStates = getSurroundingBlockStates(world);
        for (BlockState blockState : blockStates) {
            if(!blockState.isSolid()){
                foundNotSolid = false;
            }
        }
        return foundNotSolid;
    }
    public ArrayList<BlockState> getSurroundingBlockStates(World world){
        ArrayList<BlockState> blockStates = new ArrayList();
        BlockPos testBlockPos = getTargetBlockPos();
        blockStates.add(world.getBlockState(blockPos));
        blockStates.add(world.getBlockState(blockPos.offset(Direction.DOWN)));
        blockStates.add(world.getBlockState(blockPos.offset(Direction.UP)));
        blockStates.add(world.getBlockState(blockPos.offset(Direction.WEST)));
        blockStates.add(world.getBlockState(blockPos.offset(Direction.EAST)));
        blockStates.add(world.getBlockState(blockPos.offset(Direction.NORTH)));
        blockStates.add(world.getBlockState(blockPos.offset(Direction.SOUTH)));
        return blockStates;
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
    public String getActionStatString(World world) {
        if(getTargetEntity() != null){
            return getTargetEntity().getEntity().getType().getRegistryName().toString();
        }else if(getTargetBlockPos() != null){

            BlockPos blockPos = getTargetBlockPos();
            return world.getBlockState(blockPos).getBlock().getRegistryName().toString();
        }else{
            return "null";
        }

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
                /*if(chaosTarget.targetEntity == null){
                    throw new ChaosNetException("No targetEntity with entityId: " + parts[1]);
                }*/
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
    public AxisAlignedBB getBoundingBox(){
        if(targetEntity != null) {
            return targetEntity.getBoundingBox();
        }
        if(blockPos != null){
            return new AxisAlignedBB(blockPos);
        }
        return null;
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

    public AxisAlignedBB getTargetBoundingBox(World world) {
        if(targetEntity != null){
            return targetEntity.getBoundingBox();
        }
        if(blockPos != null){
            BlockState state = world.getBlockState(blockPos);
            VoxelShape shape = state.getShape(world, blockPos);
           return shape.isEmpty() ? VoxelShapes.fullCube().getBoundingBox() : shape.getBoundingBox();
        }
        throw new ChaosNetException("missing `targetEntity` and `blockPos`");
    }
    public boolean equals(Object target){
        if(!(target instanceof  ChaosTarget)){
            return false;
        }
        ChaosTarget chaosTarget = (ChaosTarget)target;
        if(
            this.targetEntity != null &&
            chaosTarget.getTargetEntity() != null
        ) {
            if (this.targetEntity.equals(chaosTarget)) {
                return true;
            }
        }else if(
            this.blockPos != null &&
            chaosTarget.getTargetBlockPos() != null
        ) {
            if (this.blockPos.equals(chaosTarget.getTargetBlockPos())) {
                return true;
            }
        }
        return false;
    }
}
