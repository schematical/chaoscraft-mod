package com.schematical.chaoscraft.tileentity;

import com.schematical.chaoscraft.blocks.ChaosBlocks;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;


public class MatchManagerBlockTileEntity extends TileEntity implements ITickableTileEntity {

    private String matchId = "default";
    private int matchDurationTicks = 20 * 15;

    private int matchTicksRemaining = -1;
    protected ArrayList<ServerOrgManager> entities = new ArrayList<ServerOrgManager>();
    public MatchManagerBlockTileEntity() {
            super(ChaosTileEntity.SPAWN_TILE.get());
        }

    @Override
    public void tick() {
        if(!ChaosBlocks.matchManagerBlocks.contains(this.getPos())) {
            ChaosBlocks.matchManagerBlocks.add(this.getPos());
        }
        if(matchTicksRemaining <= 0){
            resetMatch();
            return;
        }
        matchTicksRemaining -= 1;
    }
    public void resetMatch(){
        //Report the old ones and wait....

        //Iterate through the roles.

        //Get the bot MATCH_BOT_COUNT

        //Find the spawn blocks

        //reset the counter
        matchTicksRemaining = matchDurationTicks;

    }
    public int getMatchDurationTicks(){
        return matchDurationTicks;
    }
    public void setMatchDurationTicks(int matchDurationTicks){
        this.matchDurationTicks = matchDurationTicks;
        this.markForUpdate();
    }



    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("matchId")) {
           matchId = compound.getString("matchId");
        }
        if (compound.contains("matchDuration")) {
            matchDurationTicks = compound.getInt("matchDuration");
        }


    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putString("spawnPointId", this.matchId);
        compound.putInt("matchDuration", this.matchDurationTicks);

        return compound;
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, SUpdateTileEntityPacket pkt){
        //super.onDataPacket(net, pkt);
        this.read(pkt.getNbtCompound());
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Nullable
   // @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        //return new SUpdateTileEntityPacket(this.pos, 99, this.getUpdateTag());
        return new SUpdateTileEntityPacket(pos, 99, this.write(new CompoundNBT()));
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in
     */
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }


    public String getMatchId() {
        return matchId;
    }
    public void setMatchId(String matchId){
        setSpawnPointId(matchId, false);
    }
    public void setSpawnPointId(String matchId, boolean setSiblings) {
        this.matchId = matchId;
        this.markForUpdate();

    }
    private void markForUpdate() {
        this.markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
    }


}
