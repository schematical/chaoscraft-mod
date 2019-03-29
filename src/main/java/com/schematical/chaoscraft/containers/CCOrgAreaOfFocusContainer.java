package com.schematical.chaoscraft.containers;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.biology.AreaOfFocus;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;


/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgAreaOfFocusContainer extends Container {

    public CCOrgAreaOfFocusContainer(EntityOrganism entity) {
        NeuralNet neuralNet = entity.getNNet();
        if(neuralNet == null){
            return;
        }
        AreaOfFocus areaOfFocus = (AreaOfFocus)neuralNet.getBiology("AreaOfFocus_0");

        ItemStackHandler itemStackHandler = new ItemStackHandler();
        itemStackHandler.setSize(64);
        // CONTAINER INVENTORY

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                CCObserviableAttributeCollection observiableAttributeCollection = areaOfFocus.canSeenBlock(x, y, 0);
                Block block = Block.getBlockFromName(observiableAttributeCollection.resourceId);
                ItemStack itemStack = new ItemStack(block);
                itemStack.setCount(1);
                itemStackHandler.setStackInSlot((y  * 5) + x, itemStack);
                addSlotToContainer(new SlotItemHandler(itemStackHandler, x + (y * 5), 5 + x * 18, 5 + (4 - y) * 18));

            }
        }


    }

    //@Override
    public ItemStack transferStackInSlot(EntityOrganism organism, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            int containerSlots = inventorySlots.size() - organism.inventory.mainInventory.size();//TODO: Matt look here

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

            //slot.onTake(organism, stackInSlot);

        }
        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return false;
    }
}