package com.schematical.chaoscraft.entities;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
}
