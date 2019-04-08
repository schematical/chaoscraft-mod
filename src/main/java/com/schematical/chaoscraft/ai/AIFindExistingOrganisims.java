package com.schematical.chaoscraft.ai;

import com.google.common.base.Predicate;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.entities.EntityRick;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * Created by user1a on 12/7/18.
 */
public class AIFindExistingOrganisims<T extends EntityLivingBase> extends EntityAIBase {

  protected final EntityRick rick;
  protected final Class<T> targetClass;
  protected final Predicate<? super T> targetEntitySelector;

  public AIFindExistingOrganisims(EntityRick owner, Class<T> classTarget) {
    rick = owner;
    targetClass = classTarget;
    targetEntitySelector = (Predicate) null;

  }

  @Override
  public boolean shouldExecute() {
    List<T> list = this.rick.world.<T>getEntitiesWithinAABB(this.targetClass,
        this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);
    for (int i = 0; i < list.size(); i++) {
      EntityOrganism entityOrganism = (EntityOrganism) list.get(i);

    }

    return false;
  }

  protected AxisAlignedBB getTargetableArea(double targetDistance) {
    return this.rick.getEntityBoundingBox().grow(targetDistance, 4.0D, targetDistance);
  }

  protected double getTargetDistance() {
    IAttributeInstance iattributeinstance = this.rick
        .getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
    return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
  }

}
