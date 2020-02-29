package com.schematical.chaoscraft.tileentity;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;


public class WaypointBlockTileEntity extends TileEntity implements ITickableTileEntity {

        public WaypointBlockTileEntity() {
            super(ChaosTileEntity.WAYPOINT_TILE.get());
        }

    @Override
    public void tick() {
            if(!ChaosBlocks.waypointsBlocks.contains(this.getPos())) {
                ChaosBlocks.waypointsBlocks.add(this.getPos());
                //ChaosCraft.LOGGER.info("Waypoint at " + this.getPos().getX() + ", " + this.getPos().getY() + ", " + this.getPos().getZ());

            }
        }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SUpdateTileEntityPacket pkt){

        this.read(pkt.getNbtCompound());
    }
    @Override
    public void read(CompoundNBT compound) {
            super.read(compound);

        }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
            this.writeItems(compound);

            return compound;
        }

        private CompoundNBT writeItems(CompoundNBT compound) {
            super.write(compound);

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
