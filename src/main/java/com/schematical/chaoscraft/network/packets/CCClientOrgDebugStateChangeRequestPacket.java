package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.gui.ChaosOrgBiologyButton;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.server.ChaosCraftServer;
import com.schematical.chaoscraft.server.ChaosCraftServerPlayerInfo;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class CCClientOrgDebugStateChangeRequestPacket {
    private static final String GLUE = "@";
    public String orgNamespace;
    public ServerOrgManager.DebugState debugState;
    public CCClientOrgDebugStateChangeRequestPacket(ServerOrgManager.DebugState debugState, String orgNamespace)
    {
        this.debugState = debugState;
        this.orgNamespace = orgNamespace;
    }


    public static void encode(CCClientOrgDebugStateChangeRequestPacket pkt, PacketBuffer buf)
    {
        String payload = pkt.debugState.name() + GLUE + pkt.orgNamespace;
        buf.writeString(payload);
    }

    public static CCClientOrgDebugStateChangeRequestPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);

        String strState = parts[0];
        ServerOrgManager.DebugState debugState = null;
        for (ServerOrgManager.DebugState state : ServerOrgManager.DebugState.values()) {
            if(state.name().equals(strState)){
                debugState = state;
            }
        }
        return new CCClientOrgDebugStateChangeRequestPacket(debugState, parts[1]);
    }

    public static class Handler
    {
        public static void handle(final CCClientOrgDebugStateChangeRequestPacket message, Supplier<NetworkEvent.Context> ctx) {
           // ChaosCraft.LOGGER.info("recived `CCClientSpawnPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                ChaosCraft.getServer().getOrgByNamespace(message.orgNamespace).setDebugState(message.debugState);


            });
            ctx.get().setPacketHandled(true);
        }
    }
}
