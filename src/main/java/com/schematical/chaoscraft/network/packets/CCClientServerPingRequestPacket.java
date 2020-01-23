package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ChaosCraftClient;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.server.ChaosCraftServer;
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

public class CCClientServerPingRequestPacket {


    public CCClientServerPingRequestPacket()
    {

    }


    public static void encode(CCClientServerPingRequestPacket pkt, PacketBuffer buf)
    {

        //buf.writeString(payload);
    }

    public static CCClientServerPingRequestPacket decode(PacketBuffer buf)
    {

        //String[] parts = payload.split(GLUE);
        return new CCClientServerPingRequestPacket();//parts[0], parts[1], parts[2]);//(buf.readVarInt()
    }

    public static class Handler
    {
        public static void handle(final CCClientServerPingRequestPacket message, Supplier<NetworkEvent.Context> ctx) {
           // ChaosCraft.LOGGER.info("recived `CCClientSpawnPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                ChaosCraft.LOGGER.info("recived `CCClientServerPingRequestPacket` ");
                //Pretty sure the server should get this
                ChaosCraftServer chaosCraftServer = ChaosCraft.getServer();
                //Load the NNet into memory
                String payload = "Server: \n";
                payload += "Users(" + chaosCraftServer.userMap.size() + "): \n";
                for (ChaosCraftServerPlayerInfo serverPlayerInfo : chaosCraftServer.userMap.values()) {
                    payload += serverPlayerInfo.authWhoamiResponse.getUsername() + " - Org Count: " + serverPlayerInfo.organisims.size() + "\n";
                }
                payload += "\n";

                payload += "orgNamepacesQueuedToSpawn Count: " + chaosCraftServer.orgNamepacesQueuedToSpawn.size() + "\n";
                payload += "orgsToSpawn Count: " + chaosCraftServer.orgsToSpawn.size() + "\n";
                payload += "consecutiveErrorCount: " + chaosCraftServer.consecutiveErrorCount + "\n";
                payload += "organisims: " + chaosCraftServer.organisims.size() + "\n";


                ChaosNetworkManager.sendTo(new CCServerPingResponsePacket(payload), ctx.get().getSender());


            });
            ctx.get().setPacketHandled(true);
        }
    }
}
