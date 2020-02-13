package com.schematical.chaoscraft.util;

import com.schematical.chaoscraft.ai.CCAttributeId;
import net.minecraft.entity.LivingEntity;

public class DebugTargetHolder implements iHasAttributeIdValue {
    private LivingEntity entity;
    public DebugTargetHolder(LivingEntity entity){
        this.entity = entity;
    }
    @Override
    public String getAttributeId() {
        return CCAttributeId.ENTITY_ID;
    }

    @Override
    public String getAttributeValue() {
        return "minecraft:chicken";
    }

    @Override
    public LivingEntity getEntity() {
        return entity;
    }
}
