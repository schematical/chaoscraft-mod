package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.server.ChaosCraftServerPlayerInfo;
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


    public CCClientSpawnBlockStateChangePacket( String spawnPointId, BlockPos pos)
    {
        this.pos = pos;
        this.spawnPointId = spawnPointId;
    }
    public String getSpawnPointId(){
        return spawnPointId;
    }
    public BlockPos getState(){
        return pos;
    }

    public static void encode(CCClientSpawnBlockStateChangePacket pkt, PacketBuffer buf)
    {
        String payload =  pkt.spawnPointId + GLUE + pkt.pos.getX() + GLUE + pkt.pos.getY() + GLUE + pkt.pos.getZ();
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
                )
        );
    }

    public static class Handler
    {
        public static void handle(final CCClientSpawnBlockStateChangePacket message, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {
                TileEntity tileEntity = ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD).getTileEntity(message.pos);
                ((SpawnBlockTileEntity)tileEntity).setSpawnPointId(message.spawnPointId, true);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
