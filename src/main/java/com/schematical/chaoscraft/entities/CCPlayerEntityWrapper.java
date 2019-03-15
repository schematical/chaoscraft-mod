package com.schematical.chaoscraft.entities;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by user1a on 3/15/19.
 */
public class CCPlayerEntityWrapper extends EntityPlayer {
    public EntityOrganism entityOrganism;

    public CCPlayerEntityWrapper(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    public CCPlayerEntityWrapper(World worldIn) {
        super(worldIn, null);
    }
    @Override
    public ItemStack getHeldItem(EnumHand hand){
        return entityOrganism.getHeldItem(hand);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
