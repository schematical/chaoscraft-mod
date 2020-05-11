package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientOrgUpdatePacket {
    public final String orgNamespace;
    public final Action action;
    public float value;


    public CCClientOrgUpdatePacket(String orgNamespace,  Action action, float value)
    {
        this.orgNamespace = orgNamespace;
        this.action = action;
        this.value = value;
    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCClientOrgUpdatePacket pkt, PacketBuffer buf)
    {
        buf.writeString(pkt.orgNamespace);
        buf.writeString(pkt.action.toString());
        buf.writeFloat(pkt.value);
    }

    public static CCClientOrgUpdatePacket decode(PacketBuffer buf)
    {

        return new CCClientOrgUpdatePacket(
            buf.readString(32767),
            Action.valueOf(buf.readString(32767)),
            buf.readFloat()
        );
    }

    public static class Handler
    {
        public static void handle(final CCClientOrgUpdatePacket message, Supplier<NetworkEvent.Context> ctx) {
            //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getServer().handleClientOrgUpdatePacket(message);

            });
            ctx.get().setPacketHandled(true);
        }
    }
    public enum Action{
        UpdateLifeEnd
    }

}
