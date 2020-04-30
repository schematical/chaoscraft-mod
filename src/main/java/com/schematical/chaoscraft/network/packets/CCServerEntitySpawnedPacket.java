package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCServerEntitySpawnedPacket {
    private static final String GLUE = "@";
    public final String orgNamespace;
    public final int entityId;
    public final int expectedLifeEndTime;

    public CCServerEntitySpawnedPacket(String orgNamespace, int entityId, int expectedLifeEndTime)
    {
        this.orgNamespace = orgNamespace;
        this.entityId = entityId;
        this.expectedLifeEndTime = expectedLifeEndTime;
    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCServerEntitySpawnedPacket pkt, PacketBuffer buf)
    {
        String payload = pkt.orgNamespace + GLUE + pkt.entityId;
        buf.writeString(pkt.orgNamespace);
        buf.writeInt(pkt.entityId);
        buf.writeInt(pkt.expectedLifeEndTime);
    }

    public static CCServerEntitySpawnedPacket decode(PacketBuffer buf)
    {
        String orgNamespace = buf.readString(32767);
       int entityId =  buf.readInt();
       int expectedLifeEndTime = buf.readInt();
        return new CCServerEntitySpawnedPacket(orgNamespace, entityId,expectedLifeEndTime);
    }

    public static class Handler
    {
        public static void handle(final CCServerEntitySpawnedPacket message, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCServerEntitySpawnedPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getClient().attachOrgToEntity(message);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
