package com.schematical.chaoscraft.tileentity;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.network.packets.CCClientFactoryBlockStateChangePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;


public class FactoryTileEntity extends TileEntity implements ITickableTileEntity {
    public EntityType entityType = EntityType.CHICKEN;
    protected int entityCount = 1;
    protected int entityRange = 20;
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
        for(int i = liveEntities; i < entityCount; i ++){
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
        if (compound.contains("entityRange")) {
            entityRange = compound.getInt("entityRange");
        }
        if (compound.contains("entityCount")) {
            entityCount = compound.getInt("entityCount");
        }
        if(compound.contains("entityType")){
            Optional<EntityType<?>> et = Registry.ENTITY_TYPE.getValue(new ResourceLocation( compound.getString("entityType")));
            this.entityType = et.get();
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putString("entityType", this.entityType.getRegistryName().getNamespace() + ":" + this.entityType.getRegistryName().getPath());
        compound.putInt("entityRange", this.entityRange);
        compound.putInt("entityCount", this.entityCount);
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
        return this.write(new CompoundNBT());
    }


    public void updateFactoryInfo(CCClientFactoryBlockStateChangePacket message) {
        Optional<EntityType<?>> et = Registry.ENTITY_TYPE.getValue(new ResourceLocation(message.getEntityType()));
        this.entityType = et.get();
        this.entityCount = message.getCount();
        this.entityRange = message.getRange();
        this.markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);

    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getEntityCount() {
        return entityCount;
    }

    public int getEntityRange() {
        return entityRange;
    }
}
