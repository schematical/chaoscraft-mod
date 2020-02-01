package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCServerRequestTrainingRoomGUIPacket {
    private static final String GLUE = "@";


    public CCServerRequestTrainingRoomGUIPacket()
    {

    }

    public static void encode(CCServerRequestTrainingRoomGUIPacket pkt, PacketBuffer buf)
    {
        String payload = "";//pkt.orgNamespace + GLUE + pkt.score + GLUE + pkt.life + GLUE + pkt.fitnessRuleId + GLUE + pkt.multiplier;
        buf.writeString(payload);
    }

    public static CCServerRequestTrainingRoomGUIPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        return new CCServerRequestTrainingRoomGUIPacket();
    }

    public static class Handler
    {
        public static void handle(final CCServerRequestTrainingRoomGUIPacket message, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {
                ChaosCraft.LOGGER.info("recived `CCServerRequestTrainingRoomGUIPacket` 2: ");
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getClient().displayTrainingRoomSelectionOverlayGui();

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
