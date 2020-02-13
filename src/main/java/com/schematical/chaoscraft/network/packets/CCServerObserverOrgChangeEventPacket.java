package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCServerObserverOrgChangeEventPacket {
    private static final String GLUE = "@";
    public final String orgNamespace;
    public int score;

    public int expectedLifeEndTime;

    public CCServerObserverOrgChangeEventPacket(String orgNamespace, int score, int expectedLifeEndTime)
    {
        this.orgNamespace = orgNamespace;
        this.score = score;
        this.expectedLifeEndTime = expectedLifeEndTime;
    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCServerObserverOrgChangeEventPacket pkt, PacketBuffer buf)
    {
        String payload = pkt.orgNamespace + GLUE + pkt.score + GLUE + pkt.expectedLifeEndTime;
        buf.writeString(payload);
    }

    public static CCServerObserverOrgChangeEventPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);
        return new CCServerObserverOrgChangeEventPacket(
            parts[0],
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2])
        );
    }

    public static class Handler
    {
        public static void handle(final CCServerObserverOrgChangeEventPacket message, Supplier<NetworkEvent.Context> ctx) {
            //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getClient().updateObservingEntity(message);

            });
            ctx.get().setPacketHandled(true);
        }
    }

}
