package com.schematical.chaoscraft.network;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.network.packets.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.IndexedMessageCodec;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ChaosNetworkManager {
    private static HashMap<Integer, Class> map = new HashMap<Integer, Class>();
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
        INSTANCE.registerMessage(disc++, CCServerScoreEventPacket.class, CCServerScoreEventPacket::encode, CCServerScoreEventPacket::decode, CCServerScoreEventPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientStartTrainingSessionPacket.class, CCClientStartTrainingSessionPacket::encode, CCClientStartTrainingSessionPacket::decode, CCClientStartTrainingSessionPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCServerRequestTrainingRoomGUIPacket.class, CCServerRequestTrainingRoomGUIPacket::encode, CCServerRequestTrainingRoomGUIPacket::decode, CCServerRequestTrainingRoomGUIPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientObserveStateChangePacket.class, CCClientObserveStateChangePacket::encode, CCClientObserveStateChangePacket::decode, CCClientObserveStateChangePacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCServerObserverOrgChangeEventPacket.class, CCServerObserverOrgChangeEventPacket::encode, CCServerObserverOrgChangeEventPacket::decode, CCServerObserverOrgChangeEventPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientSpawnBlockStateChangePacket.class, CCClientSpawnBlockStateChangePacket::encode, CCClientSpawnBlockStateChangePacket::decode, CCClientSpawnBlockStateChangePacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientFactoryBlockStateChangePacket.class, CCClientFactoryBlockStateChangePacket::encode, CCClientFactoryBlockStateChangePacket::decode, CCClientFactoryBlockStateChangePacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientActionPacket.class, CCClientActionPacket::encode, CCClientActionPacket::decode, CCClientActionPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCInventoryChangeEventPacket.class, CCInventoryChangeEventPacket::encode, CCInventoryChangeEventPacket::decode, CCInventoryChangeEventPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCClientSetCurrActionPacket.class, CCClientSetCurrActionPacket::encode, CCClientSetCurrActionPacket::decode, CCClientSetCurrActionPacket.Handler::handle);
        INSTANCE.registerMessage(disc++, CCActionStateChangeEventPacket.class, CCActionStateChangeEventPacket::encode, CCActionStateChangeEventPacket::decode, CCActionStateChangeEventPacket.Handler::handle);

    }
    protected  <MSG> void registerMessage(int index, Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer){
        map.put(index, messageType);
        INSTANCE.registerMessage(index, messageType, encoder, decoder, messageConsumer);
    }
    /**
     * Sends a packet to the server.<br>
     * Must be called Client side.
     */
    public static void sendToServer(Object msg)
    {
       /* if(
            ChaosCraft.getServer() != null &&
            ChaosCraft.getClient() != null
        ){
            if(!map.values().contains(msg)){
                ChaosCraft.LOGGER.error("Shit");
                return;
            }
            Class T = null;
            ((T) msg);
        }*/
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