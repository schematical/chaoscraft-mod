package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientSpawnPacket {
    private static final String GLUE = "@";
    private final String orgNamespace;


    public CCClientSpawnPacket(String orgNamespace)
    {
        this.orgNamespace = orgNamespace;
    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCClientSpawnPacket pkt, PacketBuffer buf)
    {
        String payload = pkt.orgNamespace;
        buf.writeString(payload);
    }

    public static CCClientSpawnPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        //String[] parts = payload.split(GLUE);
        return new CCClientSpawnPacket(payload);//parts[0], parts[1], parts[2]);//(buf.readVarInt()
    }

    public static class Handler
    {
        public static void handle(final CCClientSpawnPacket message, Supplier<NetworkEvent.Context> ctx) {
           // ChaosCraft.LOGGER.info("recived `CCClientSpawnPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                ChaosCraft.LOGGER.info("recived `CCClientSpawnPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getServer().queueOrgToSpawn(message.orgNamespace, ctx.get().getSender());

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
