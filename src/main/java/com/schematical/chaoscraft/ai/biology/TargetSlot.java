package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 2/26/19.
 */
public class TargetSlot extends BiologyBase {
    public Entity entity;
    public BlockPos blockPos;

    public void setTarget(Entity entity){
        this.entity = entity;
    }
    public void setTarget(BlockPos blockPos){
        this.blockPos = blockPos;
    }
    public Vec3d getTargetPosition(){
        if(entity != null){
            return entity.getPositionVector();
        }
        return new Vec3d(
            this.blockPos.getX(),
            this.blockPos.getY(),
            this.blockPos.getZ()
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
        if(entity != null){
            message += entity.getType().getRegistryName();
        }else if(blockPos != null){

            BlockState blockState = Minecraft.getInstance().world.getBlockState(blockPos);
            message += blockState.getBlock().getRegistryName();
        }else{
            message += " null";
        }

        return message;
    }

}

