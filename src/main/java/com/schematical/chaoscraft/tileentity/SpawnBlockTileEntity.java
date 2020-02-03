package com.schematical.chaoscraft.tileentity;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


public class SpawnBlockTileEntity  extends TileEntity implements ITickableTileEntity {
        private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
        private final int[] cookingTimes = new int[4];
        private final int[] cookingTotalTimes = new int[4];

        public SpawnBlockTileEntity() {
            super(ChaosTileEntity.SPAWN_TILE.get());
        }

    @Override
    public void tick() {
            if(!ChaosBlocks.spawnBlocks.contains(this.getPos())) {
                ChaosBlocks.spawnBlocks.add(this.getPos());
                ChaosCraft.LOGGER.info("SpawnBlock at " + this.getPos().getX() + ", " + this.getPos().getY() + ", " + this.getPos().getZ());

            }
        }


    @Override
    public void read(CompoundNBT compound) {
            super.read(compound);
            this.inventory.clear();
            ItemStackHelper.loadAllItems(compound, this.inventory);
            if (compound.contains("CookingTimes", 11)) {
                int[] aint = compound.getIntArray("CookingTimes");
                System.arraycopy(aint, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, aint.length));
            }

            if (compound.contains("CookingTotalTimes", 11)) {
                int[] aint1 = compound.getIntArray("CookingTotalTimes");
                System.arraycopy(aint1, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, aint1.length));
            }

        }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
            this.writeItems(compound);
            compound.putIntArray("CookingTimes", this.cookingTimes);
            compound.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
            return compound;
        }

        private CompoundNBT writeItems(CompoundNBT compound) {
            super.write(compound);
            ItemStackHelper.saveAllItems(compound, this.inventory, true);
            return compound;
        }

        /**
         * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
         * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
         */
        @Nullable
        @Override
        public SUpdateTileEntityPacket getUpdatePacket() {
            return new SUpdateTileEntityPacket(this.pos, 13, this.getUpdateTag());
        }

        /**
         * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
         * many blocks change at once. This compound comes back to you clientside in
         */
        @Override
        public CompoundNBT getUpdateTag() {
            return this.writeItems(new CompoundNBT());
        }



}
