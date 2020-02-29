package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.util.TargetHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 2/26/19.
 */
public class TargetSlot extends BiologyBase implements iTargetable {
    private Entity targetEntity;
    private BlockPos blockPos;

    public void setTarget(Entity entity){
        this.targetEntity = entity;
    }
    public void setTarget(BlockPos blockPos){
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
    public Double getYawDelta() {
        Vec3d targetPosition = getTargetPosition();
        if (targetPosition == null) {
            return null;
        }
        return TargetHelper.getYawDelta(
                targetPosition,
                getEntity().getEyePosition(1),
                getEntity() .rotationYaw
        );

    }

    public Double getPitchDelta() {
        Vec3d targetPosition = getTargetPosition();
        if (targetPosition == null) {
            return null;
        }

        return TargetHelper.getPitchDelta(
                targetPosition,
                getEntity().getPositionVec(),
                getEntity().getLookVec()
        );
    }

    public Double getDist() {
        Vec3d targetPosition = getTargetPosition();
        if (targetPosition == null) {
            return null;
        }
        return TargetHelper.getDistDelta(
                getEntity().getPositionVector(),
                targetPosition
        );
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);


    }
    @Override
    public void reset() {

    }
    public String toString(){

        String message = id + ": ";
        if(targetEntity != null){
            message += targetEntity.getType().getRegistryName();
        }else if(blockPos != null){

            BlockState blockState = Minecraft.getInstance().world.getBlockState(blockPos);
            message += blockState.getBlock().getRegistryName();
        }else{
            message += " null";
        }

        return message;
    }

}

