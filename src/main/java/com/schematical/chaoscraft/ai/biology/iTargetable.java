package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.entities.OrgEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public interface iTargetable {
    Double getYawDelta();
    Double getPitchDelta();
    Double getDist();
    CCObserviableAttributeCollection getObservedAttributes(OrgEntity orgEntity);
    Entity getTargetEntity();
    BlockPos getTargetBlockPos();
}
