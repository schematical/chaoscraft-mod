package com.schematical.chaoscraft.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.function.Supplier;
public class ClientAuthPacket {
    private final String accessToken;

    public ClientAuthPacket(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public static void encode(ClientAuthPacket pkt, PacketBuffer buf)
    {
        buf.writeString(pkt.accessToken);
    }

    public static ClientAuthPacket decode(PacketBuffer buf)
    {
        return new ClientAuthPacket(buf.readString());//(buf.readVarInt()
    }

    public static class Handler
    {
        public static void handle(final ClientAuthPacket message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {

                //Pretty sure the sever should get this


            });
            ctx.get().setPacketHandled(true);
        }
    }
}
