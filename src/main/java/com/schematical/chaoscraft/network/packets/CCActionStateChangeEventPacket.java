package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.action.ActionBase;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCActionStateChangeEventPacket {
    private static final String GLUE = "@";
    public final String orgNamespace;
    public ActionBase.ActionState actionState;


    public CCActionStateChangeEventPacket(String orgNamespace, ActionBase.ActionState actionState)
    {
        this.orgNamespace = orgNamespace;
        this.actionState = actionState;

    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCActionStateChangeEventPacket pkt, PacketBuffer buf)
    {

        buf.writeString( pkt.orgNamespace);
        buf.writeString(pkt.actionState.toString());
    }

    public static CCActionStateChangeEventPacket decode(PacketBuffer buf)
    {

        return new CCActionStateChangeEventPacket(
            buf.readString(32767),
            ActionBase.ActionState.valueOf( buf.readString(32767))
        );
    }

    public static class Handler
    {
        public static void handle(final CCActionStateChangeEventPacket message, Supplier<NetworkEvent.Context> ctx) {
            //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getClient().attachActionStateChange(message);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
