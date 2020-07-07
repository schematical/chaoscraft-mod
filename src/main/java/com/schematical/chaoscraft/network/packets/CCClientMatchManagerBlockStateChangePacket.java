package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.tileentity.MatchManagerBlockTileEntity;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientMatchManagerBlockStateChangePacket {
    private static final String GLUE = "@";
    private final BlockPos pos;
    private final String matchId;
    private int matchDurationTicks;


    public CCClientMatchManagerBlockStateChangePacket(String matchId, BlockPos pos, int matchDurationTicks)
    {
        this.pos = pos;
        this.matchId = matchId;
        this.matchDurationTicks = matchDurationTicks;
    }
    public String getMatchId(){
        return matchId;
    }
    public BlockPos getState(){
        return pos;
    }

    public static void encode(CCClientMatchManagerBlockStateChangePacket pkt, PacketBuffer buf)
    {
        String payload =  pkt.matchId + GLUE + pkt.pos.getX() + GLUE + pkt.pos.getY() + GLUE + pkt.pos.getZ() + GLUE + pkt.matchDurationTicks;
        buf.writeString(payload);
    }

    public static CCClientMatchManagerBlockStateChangePacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);

        return new CCClientMatchManagerBlockStateChangePacket(
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
        public static void handle(final CCClientMatchManagerBlockStateChangePacket message, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {
                TileEntity tileEntity = ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD).getTileEntity(message.pos);
                MatchManagerBlockTileEntity matchManagerBlockTileEntity = ((MatchManagerBlockTileEntity)tileEntity);
                matchManagerBlockTileEntity.setMatchId(message.matchId);
                matchManagerBlockTileEntity.setMatchDurationTicks(message.matchDurationTicks);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
