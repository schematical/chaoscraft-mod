package com.schematical.chaoscraft.tileentity;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.FireworkRocketEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;


public class FactoryTileEntity extends TileEntity implements ITickableTileEntity {
    public EntityType entityType = EntityType.CHICKEN;
    public final int MAX_ENTITIES = 10;
    public final int RANGE = 20;
    protected ArrayList<Entity> entities = new ArrayList<Entity>();
    public FactoryTileEntity() {
            super(ChaosTileEntity.FACTORY_TILE.get());
        }

    @Override
    public void tick() {
        if(world.isRemote){
            return;
        }
       int liveEntities = 0;
        Iterator<Entity> iterator = entities.iterator();
        while(iterator.hasNext()){
            Entity entity = iterator.next();
            if(!entity.isAlive()){
                iterator.remove();
            }else{
                liveEntities += 1;
            }
        }
        for(int i = liveEntities; i < MAX_ENTITIES; i ++){
            ServerWorld serverWorld = ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD);
            Entity entity = entityType.create(serverWorld);
            entity.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0);
            entities.add(entity);
            serverWorld.summonEntity(entity);
        }
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
