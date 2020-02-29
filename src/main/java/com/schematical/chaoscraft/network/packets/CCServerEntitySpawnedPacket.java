package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCServerEntitySpawnedPacket {
    private static final String GLUE = "@";
    private final String orgNamespace;
    private final int entityId;

    public CCServerEntitySpawnedPacket(String orgNamespace, int entityId)
    {
        this.orgNamespace = orgNamespace;
        this.entityId = entityId;
    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCServerEntitySpawnedPacket pkt, PacketBuffer buf)
    {
        String payload = pkt.orgNamespace + GLUE + pkt.entityId;
        buf.writeString(payload);
    }

    public static CCServerEntitySpawnedPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);
        return new CCServerEntitySpawnedPacket(parts[0], Integer.parseInt(parts[1]));//(buf.readVarInt()
    }

    public static class Handler
    {
        public static void handle(final CCServerEntitySpawnedPacket message, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCServerEntitySpawnedPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getClient().attachOrgToEntity(message.orgNamespace, message.entityId);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
