package com.schematical.chaoscraft.containers;

import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;


/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgInventoryContainer extends Container {

    public CCOrgInventoryContainer(EntityOrganism entity) {

        ItemStackHandler itemStackHandler = entity.getItemStack();
        // CONTAINER INVENTORY
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if(itemStackHandler.getSlots() < y * 3 + x) {
                    addSlotToContainer(new SlotItemHandler(itemStackHandler, x + (y * 3), 62 + x * 18, 17 + y * 18));
                }
            }
        }


    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(stackInSlot, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stackInSlot, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            slot.onTake(player, stackInSlot);

        }
        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}