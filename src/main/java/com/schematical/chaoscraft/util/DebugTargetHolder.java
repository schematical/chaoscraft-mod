package com.schematical.chaoscraft.util;

import com.schematical.chaoscraft.ai.CCAttributeId;
import net.minecraft.entity.LivingEntity;

public class DebugTargetHolder implements iHasAttributeIdValue {
    public static final String ATTRIBUTE_VALUE = "minecraft:bee";//"chaoscraft:waypoint";
    public static final String ATTRIBUTE_ID = CCAttributeId.ENTITY_ID;;
    private LivingEntity entity;
    public DebugTargetHolder(LivingEntity entity){
        this.entity = entity;
    }
    @Override
    public String getAttributeId() {
        return ATTRIBUTE_ID;
    }

    @Override
    public String getAttributeValue() {
        return ATTRIBUTE_VALUE;
    }

    @Override
    public LivingEntity getEntity() {
        return entity;
    }
}
