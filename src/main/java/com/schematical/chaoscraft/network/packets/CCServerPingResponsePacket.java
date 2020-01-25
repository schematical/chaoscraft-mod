package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.gui.ChaosNetworkInfoOverlayGui;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.server.ChaosCraftServerPlayerInfo;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class CCServerPingResponsePacket {
    private static final String GLUE = "@";
    private String message;
    public CCServerPingResponsePacket(String message)
    {
        this.message = message;
    }


    public static void encode(CCServerPingResponsePacket pkt, PacketBuffer buf)
    {
        String payload = pkt.message;
        buf.writeString(payload);
    }

    public static CCServerPingResponsePacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
      ;
        return new CCServerPingResponsePacket(payload);
    }

    public static class Handler
    {
        public static void handle(final CCServerPingResponsePacket pkt, Supplier<NetworkEvent.Context> ctx) {
           // ChaosCraft.LOGGER.info("recived `CCClientSpawnPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                ChaosCraft.LOGGER.info("recived `CCServerPingResponsePacket` ");
                ChaosNetworkInfoOverlayGui.serverPingMessage = pkt.message;

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
