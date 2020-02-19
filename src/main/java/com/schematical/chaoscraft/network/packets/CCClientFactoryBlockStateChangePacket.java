package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.tileentity.FactoryTileEntity;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientFactoryBlockStateChangePacket {
    private static final String GLUE = "@";

    private final String entityType;
    private final BlockPos pos;
    public final int count;
    public final int range;


    public CCClientFactoryBlockStateChangePacket(String entityType, BlockPos pos, int count, int range)
    {
        this.count = count;
        this.pos = pos;
        this.range = range;
        this.entityType = entityType;

    }
    public String getEntityType(){
        return entityType;
    }
    public int getRange(){
        return range;
    }
    public int getCount(){
        return count;
    }

    public static void encode(CCClientFactoryBlockStateChangePacket pkt, PacketBuffer buf)
    {
        String payload =  pkt.entityType + GLUE +  pkt.pos.getX() + GLUE + pkt.pos.getY() + GLUE + pkt.pos.getZ() + GLUE + pkt.count + GLUE + pkt.range;
        buf.writeString(payload);
    }

    public static CCClientFactoryBlockStateChangePacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);

        return new CCClientFactoryBlockStateChangePacket(
                parts[0],
                new BlockPos(
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])
                ),
                Integer.parseInt(parts[4]),
                Integer.parseInt(parts[5])
        );
    }

    public static class Handler
    {
        public static void handle(final CCClientFactoryBlockStateChangePacket message, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {
                TileEntity tileEntity = ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD).getTileEntity(message.pos);
                ((FactoryTileEntity)tileEntity).updateFactoryInfo(message);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
