package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.server.ChaosCraftServerPlayerInfo;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientObserveStateChangePacket {
    private static final String GLUE = "@";
    private final ChaosCraftServerPlayerInfo.State state;
    private final String orgNamespace;


    public CCClientObserveStateChangePacket(ChaosCraftServerPlayerInfo.State state, String orgNamespace)
    {
        this.state = state;
        this.orgNamespace = orgNamespace;
    }
    public String getOrgNamespace(){
        return orgNamespace;
    }
    public ChaosCraftServerPlayerInfo.State getState(){
        return state;
    }

    public static void encode(CCClientObserveStateChangePacket pkt, PacketBuffer buf)
    {
        String payload = pkt.state.name() + GLUE + pkt.orgNamespace;
        buf.writeString(payload);
    }

    public static CCClientObserveStateChangePacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);

        return new CCClientObserveStateChangePacket(ChaosCraftServerPlayerInfo.State.valueOf(parts[0]), parts[1]);//parts[0], parts[1], parts[2]);//(buf.readVarInt()
    }

    public static class Handler
    {
        public static void handle(final CCClientObserveStateChangePacket message, Supplier<NetworkEvent.Context> ctx) {
           // ChaosCraft.LOGGER.info("recived `CCClientObserveStateChangePacket` 1 ");
            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCClientObserveStateChangePacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getServer().updatePlayerObserverState(message, ctx.get().getSender());

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
