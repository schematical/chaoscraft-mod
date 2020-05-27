package com.schematical.chaoscraft.entities;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Created by user1a on 3/15/19.
 */
public class CCPlayerEntityWrapper extends PlayerEntity {
    public OrgEntity entityOrganism;

    public CCPlayerEntityWrapper(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    public CCPlayerEntityWrapper(World worldIn) {
        super(worldIn, null);
    }
   /* @Override
    public ItemStack getHeldItem(EnumHand hand){
        return entityOrganism.getHeldItem(hand);
    }*/
    @Override
    public boolean equals(Object object){
        if(object instanceof OrgEntity){
            OrgEntity comp = (OrgEntity) object;
            return comp.equals(entityOrganism);
        }
        return super.equals(object);
    }
    public ItemStack getHeldItem(Hand hand){
        return entityOrganism.getHeldItem(hand);
    }
    public void setHeldItem(Hand hand, ItemStack stack) {
        entityOrganism.setHeldItem(hand, stack);
    }
  /*  @Override
    public Vec3d getLook(float partialTicks){
        return this.entityOrganism.getLook(partialTicks);
    }*/
    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
    @Override
    public void setPositionAndUpdate(double x, double y, double z){
        super.setPositionAndUpdate(x,y,z);
        this.entityOrganism.setPositionAndUpdate(x, y, z);
    }
}
