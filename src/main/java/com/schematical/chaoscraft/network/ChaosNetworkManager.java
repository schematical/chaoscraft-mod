package com.schematical.chaoscraft.network;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.network.packets.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ChaosNetworkManager {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChaosCraft.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int disc = 0;

        INSTANCE.registerMessage(disc++, ClientAuthPacket.class, ClientAuthPacket::encode, ClientAuthPacket::decode, ClientAuthPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, ServerIntroInfoPacket.class, ServerIntroInfoPacket::encode, ServerIntroInfoPacket::decode, ServerIntroInfoPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientSpawnPacket.class, CCClientSpawnPacket::encode, CCClientSpawnPacket::decode, CCClientSpawnPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientOutputNeuronActionPacket.class, CCClientOutputNeuronActionPacket::encode, CCClientOutputNeuronActionPacket::decode, CCClientOutputNeuronActionPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCServerEntitySpawnedPacket.class, CCServerEntitySpawnedPacket::encode, CCServerEntitySpawnedPacket::decode, CCServerEntitySpawnedPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientServerPingRequestPacket.class, CCClientServerPingRequestPacket::encode, CCClientServerPingRequestPacket::decode, CCClientServerPingRequestPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCServerPingResponsePacket.class, CCServerPingResponsePacket::encode, CCServerPingResponsePacket::decode, CCServerPingResponsePacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientOrgDebugStateChangeRequestPacket.class, CCClientOrgDebugStateChangeRequestPacket::encode, CCClientOrgDebugStateChangeRequestPacket::decode, CCClientOrgDebugStateChangeRequestPacket.Handler::handle);

    }
    /**
     * Sends a packet to the server.<br>
     * Must be called Client side.
     */
    public static void sendToServer(Object msg)
    {
        INSTANCE.sendToServer(msg);
    }

    /**
     * Send a packet to a specific player.<br>
     * Must be called Server side.
     */
    public static void sendTo(Object msg, ServerPlayerEntity player)
    {
        if (!(player instanceof FakePlayer))
        {
            INSTANCE.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }
}