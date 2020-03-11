package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.entities.OrgEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface iTargetable {
    Double getYawDelta();
    Double getPitchDelta();
    Double getDist();
    CCObserviableAttributeCollection getObservedAttributes(OrgEntity orgEntity);
    Entity getTargetEntity();
    BlockPos getTargetBlockPos();
    Vec3d getTargetPosition();
    Vec3d getTargetPositionCenter();
}
