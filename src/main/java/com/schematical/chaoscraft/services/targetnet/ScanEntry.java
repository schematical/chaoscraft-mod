package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;

public class ScanEntry {
    public Entity entity;
    public BlockPos blockPos;
    public CCObserviableAttributeCollection atts;
    private HashMap<String, Float> scores = new HashMap<String, Float>();
    public void setScore(String targetSlotId, float score){
        this.scores.put(targetSlotId, score);
    }
    public float getScore(String targetSlotId){
        return this.scores.get(targetSlotId);
    }
    public HashMap<String, Float> getScores(){
        return this.scores;
    }

    public Vec3d getPosition() {
        if(entity != null){
            return entity.getPositionVec();
        }
        if(blockPos != null) {
            return new Vec3d(
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ()
            );
        }
        return null;
    }
    public BlockPos getTargetBlockPos() {
        if(entity != null){
            return new BlockPos(
                    Math.round(entity.getEyePosition(1).getX()),
                    Math.round(entity.getEyePosition(1).getY()),
                    Math.round(entity.getEyePosition(1).getZ())
            );
        }
        if(blockPos != null) {
            return blockPos;
        }
        return null;
    }
    public ChaosTarget getChaosTarget(){
        if(entity != null) {
          return new ChaosTarget(entity);
        }else if(blockPos != null){
            return new ChaosTarget(blockPos);
        }else{
            throw new ChaosNetException("Invalid ScanEntry: No blockPos nor Entity");
        }
    }
}