package com.schematical.chaoscraft.ai;

import com.schematical.chaoscraft.entities.OrgEntity;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Created by user1a on 2/26/19.
 */
public class CCObserviableAttributeCollection{
    public Entity _entity;
//    public Block _block;
    public String resourceId;
    public String resourceType;
    public Vec3d position;
    public BlockPos _blockPos;
    public Team team;

    public double getDist(OrgEntity orgEntity){
        Vec3d eyePos = orgEntity.getEyePosition(1);
        return eyePos.distanceTo(position);
    }
}