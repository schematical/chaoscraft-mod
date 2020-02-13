package com.schematical.chaoscraft.util;

import net.minecraft.entity.LivingEntity;

public interface iHasAttributeIdValue {
    String getAttributeId();
    String getAttributeValue();
    LivingEntity getEntity();
}
