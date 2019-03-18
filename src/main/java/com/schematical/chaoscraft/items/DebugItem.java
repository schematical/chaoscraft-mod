package com.schematical.chaoscraft.items;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityEvilRabbit;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebugItem extends Item {

    public DebugItem() {
        this.setRegistryName(new ResourceLocation(ChaosCraft.MODID, "debug_item"));
        this.setUnlocalizedName("debug_item");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(!worldIn.isRemote) {
            EntityEvilRabbit man = new EntityEvilRabbit(worldIn, player.getName() + "'s bot");
            man.setCustomNameTag( player.getName() + "'s rabbit");
            man.setPosition(pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ);

            worldIn.spawnEntity(man);
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);


    }



    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
                                            EnumHand hand) {

        if(target instanceof EntityEvilRabbit) {
            EntityEvilRabbit man = (EntityEvilRabbit)target;
            //man.leftClicking = !man.leftClicking;
            return true;
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }



}