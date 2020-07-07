package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientSpawnBlockStateChangePacket {

    private final BlockPos pos;
    private final String spawnPointId;
    private int maxLivingEntites;
    private String matchId;


    public CCClientSpawnBlockStateChangePacket( String spawnPointId, BlockPos pos, int maxLivingEntites, String matchId)
    {
        this.pos = pos;
        this.spawnPointId = spawnPointId;
        this.maxLivingEntites = maxLivingEntites;
        this.matchId = matchId;
    }
    public String getSpawnPointId(){
        return spawnPointId;
    }
    public BlockPos getBlockPos(){
        return pos;
    }
    public int getMaxLivingEntites(){ return maxLivingEntites; }
    public String getMatchId(){
        return matchId;
    }

    public static void encode(CCClientSpawnBlockStateChangePacket pkt, PacketBuffer buf)
    {

        buf.writeString(pkt.spawnPointId);
        buf.writeBlockPos(pkt.pos);
        buf.writeInt(pkt.maxLivingEntites);
        buf.writeString(pkt.matchId);
    }

    public static CCClientSpawnBlockStateChangePacket decode(PacketBuffer buf)
    {
        String spawnPointId = buf.readString(32767);
        BlockPos blockPos = buf.readBlockPos();
        int maxLivingEntites = buf.readInt();
        String matchId = buf.readString(32767);

        return new CCClientSpawnBlockStateChangePacket(
                spawnPointId,
                blockPos,
                maxLivingEntites,
                matchId
        );
    }

    public static class Handler
    {
        public static void handle(final CCClientSpawnBlockStateChangePacket message, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {
                TileEntity tileEntity = ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD).getTileEntity(message.pos);
                SpawnBlockTileEntity spawnBlockTileEntity = ((SpawnBlockTileEntity)tileEntity);
                spawnBlockTileEntity.setSpawnPointId(message.spawnPointId, true);
                spawnBlockTileEntity.setMaxLivingEntities(message.maxLivingEntites);
                spawnBlockTileEntity.setMatchId(message.matchId);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
