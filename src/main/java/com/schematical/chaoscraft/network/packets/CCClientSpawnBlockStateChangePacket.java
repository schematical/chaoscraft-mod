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
    private static final String GLUE = "@";
    private final BlockPos pos;
    private final String spawnPointId;
    private int maxLivingEntites;


    public CCClientSpawnBlockStateChangePacket( String spawnPointId, BlockPos pos, int maxLivingEntites)
    {
        this.pos = pos;
        this.spawnPointId = spawnPointId;
        this.maxLivingEntites = maxLivingEntites;
    }
    public String getSpawnPointId(){
        return spawnPointId;
    }
    public BlockPos getState(){
        return pos;
    }

    public static void encode(CCClientSpawnBlockStateChangePacket pkt, PacketBuffer buf)
    {
        String payload =  pkt.spawnPointId + GLUE + pkt.pos.getX() + GLUE + pkt.pos.getY() + GLUE + pkt.pos.getZ() + GLUE + pkt.maxLivingEntites;
        buf.writeString(payload);
    }

    public static CCClientSpawnBlockStateChangePacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);

        return new CCClientSpawnBlockStateChangePacket(
                parts[0],
                new BlockPos(
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])
                ),
                Integer.parseInt(parts[4])
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

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
