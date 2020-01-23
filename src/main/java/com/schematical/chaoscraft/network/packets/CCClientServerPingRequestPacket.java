package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.server.ChaosCraftServer;
import com.schematical.chaoscraft.server.ChaosCraftServerPlayerInfo;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
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


                payload += "consecutiveErrorCount: " + chaosCraftServer.consecutiveErrorCount + "\n";
                payload += "organisms: " + chaosCraftServer.organisms.size() + "\n";

                HashMap<ServerOrgManager.State, ArrayList<ServerOrgManager>> coll = chaosCraftServer.getOrgsSortedByState();
                for ( ServerOrgManager.State state : coll.keySet() ) {
                    payload += " - " + state + ": " + coll.get(state).size() + "\n";
                }
                payload += "\n";
                ChaosNetworkManager.sendTo(new CCServerPingResponsePacket(payload), ctx.get().getSender());


            });
            ctx.get().setPacketHandled(true);
        }
    }
}
