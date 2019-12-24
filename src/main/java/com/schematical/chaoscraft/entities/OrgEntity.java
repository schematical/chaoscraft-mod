package com.schematical.chaoscraft.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;

public class OrgEntity extends LivingEntity {

    @ObjectHolder("chaoscraft:org_entity")
    public static final EntityType<OrgEntity> ORGANISM_TYPE = null;
    //The current selected item
    private int currentItem = 0;

    //Whether the bot has tried left clicking last tick.
    private boolean lastTickLeftClicked;

    //Mining related variables
    private float hardness = 0;
    private BlockPos lastMinePos = BlockPos.ZERO;
    private int blockSoundTimer;

    public OrgEntity(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return new ArrayList<ItemStack>();
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {


        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override
    public HandSide getPrimaryHand() {
        return null;
    }


}