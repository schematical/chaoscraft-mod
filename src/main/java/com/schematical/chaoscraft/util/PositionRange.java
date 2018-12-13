package com.schematical.chaoscraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/10/18.
 */
public class PositionRange {
    public float minX;
    public float maxX;
    public float minY;
    public float maxY;
    public float minZ;
    public float maxZ;
    public Entity orientedToEntity;
    public PositionRange originalPositionRange;
    public PositionRange orientToEntity(Entity entity){
        PositionRange _originalPositionRange = this;
        if(_originalPositionRange.originalPositionRange != null){
            _originalPositionRange = _originalPositionRange.originalPositionRange;
        }
        PositionRange positionRange = new PositionRange();
        positionRange.originalPositionRange = _originalPositionRange;
        positionRange.orientedToEntity = entity;
        BlockPos blockPos = new BlockPos(positionRange.orientedToEntity);
        positionRange.maxX = blockPos.getX() + positionRange.originalPositionRange.maxX;
        positionRange.minX = blockPos.getX() + positionRange.originalPositionRange.minX;
        positionRange.maxY = blockPos.getY() + positionRange.originalPositionRange.maxY;
        positionRange.minY = blockPos.getY() + positionRange.originalPositionRange.minY;
        positionRange.maxZ = blockPos.getZ() + positionRange.originalPositionRange.maxZ;
        positionRange.minZ = blockPos.getZ() + positionRange.originalPositionRange.minZ;
        return positionRange;

    }
    public void parseData(JSONObject jsonObject){
        minX = Float.parseFloat(jsonObject.get("minX").toString());
        maxX = Float.parseFloat(jsonObject.get("maxX").toString());
        minY = Float.parseFloat(jsonObject.get("minY").toString());
        maxY = Float.parseFloat(jsonObject.get("maxY").toString());
        minZ = Float.parseFloat(jsonObject.get("minZ").toString());
        maxZ = Float.parseFloat(jsonObject.get("maxZ").toString());
    }

}
