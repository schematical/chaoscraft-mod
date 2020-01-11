package com.schematical.chaoscraft;

import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;
import com.schematical.chaoscraft.blocks.SpawnBlock;
import com.schematical.chaoscraft.client.ChaosCraftClient;
import com.schematical.chaoscraft.commands.CCAuthCommand;
import com.schematical.chaoscraft.commands.CCSummonCommand;
import com.schematical.chaoscraft.commands.CCTestCommand;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.entities.OrgEntityRenderer;
import com.schematical.chaoscraft.fitness.ChaosCraftFitnessManager;
import com.schematical.chaoscraft.fitness.EntityFitnessManager;
import com.schematical.chaoscraft.network.ChaosNetworkManager;

import com.schematical.chaoscraft.server.ChaosCraftServer;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.FMLWorldPersistenceHook;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.stream.Collectors;
import com.schematical.chaosnet.ChaosNet;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.*;
import sun.nio.ch.Net;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("chaoscraft")
public class ChaosCraft
{

    public static final String MODID = "chaoscraft";
    public static ChaosCraftConfig config;
    public static ChaosNet sdk;
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static ChaosCraftFitnessManager fitnessManager;
    public static float activationThreshold = .3f;
    public static ArrayList<SpawnBlock> spawnBlocks = new ArrayList<SpawnBlock>();
    private static ChaosCraftClient client;
    private static ChaosCraftServer server;
    public ChaosCraft() {

        config = new ChaosCraftConfig();
        config.load();
        LOGGER.info("Config Loaded...");
        LOGGER.info("Config Username: " + config.username);
        sdk =  ChaosNet.builder()
                .connectionConfiguration(
                        new ConnectionConfiguration()
                                .maxConnections(100)
                                .connectionMaxIdleMillis(1000)
                )
                .timeoutConfiguration(
                        new TimeoutConfiguration()
                                .httpRequestTimeout(60000)
                                .totalExecutionTimeout(60000)
                                .socketTimeout(60000)
                )
                .signer(
                        (ChaosnetCognitoUserPool) request -> ChaosCraft.config.accessToken
                        //new ChaosNetSigner()
                )
                .build();

        auth();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onWorldTickEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onServerTickEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientTickEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onPlayerLoggedInEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, this::onEntityRegistry);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ChaosNetworkManager.register();
    }

    public static ChaosCraftServer getServer(){
        return server;
    }

    public static void auth(){
        LOGGER.info("REFRESH TOKEN:" + config.refreshToken );
        if(config.refreshToken != null){
            AuthTokenRequest authTokenRequest = new AuthTokenRequest();
            authTokenRequest.setUsername(config.username);
            authTokenRequest.setRefreshToken(config.refreshToken);
            PostAuthTokenRequest postAuthTokenRequest = new PostAuthTokenRequest();
            postAuthTokenRequest.authTokenRequest(authTokenRequest);

            try {
                AuthLoginResponse authLoginResponse = ChaosCraft.sdk.postAuthToken(postAuthTokenRequest).getAuthLoginResponse();
                config.accessToken = authLoginResponse.getAccessToken();
                LOGGER.info("ACCESS TOKEN:" + config.refreshToken.substring(0, 10) + "'....");
            }catch (ChaosNetException e) {
                LOGGER.error(e);
            }

        }
    }

    public static ChaosCraftClient getClient() {
        return client;
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

        RenderingRegistry.registerEntityRenderingHandler(
                OrgEntity.ORGANISM_TYPE,
                OrgEntityRenderer::new
        );

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("chaoscraft", "helloworld", () -> { LOGGER.info("Hello world from the ChaosCraft"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
        server = new ChaosCraftServer();

        CCSummonCommand.register(event.getCommandDispatcher());
        CCAuthCommand.register(event.getCommandDispatcher());
        CCTestCommand.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public void onClientStarting(FMLClientSetupEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from Client starting");
        client = new ChaosCraftClient();

    }

    @SubscribeEvent
    public void onWorldTickEvent(TickEvent.WorldTickEvent worldTickEvent) {
        //LOGGER.info("WorldTickEvent...." + worldTickEvent.side);

    }
    @SubscribeEvent
    public void onServerTickEvent(TickEvent.ServerTickEvent serverTickEvent) {
        //LOGGER.info("ServerTickEvent...." + serverTickEvent.side);
        if(server == null){
            return;
        }
        server.tick();

    }
    public void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
                EntityType.Builder.create(OrgEntity::new, EntityClassification.MISC).build(MODID + ":org_entity").setRegistryName(MODID, "org_entity")
        );
    }
    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
            blockRegistryEvent.getRegistry().registerAll(
                    new SpawnBlock(Block.Properties.create(Material.BARRIER).hardnessAndResistance(-1.0F, 3600000.0F)).setRegistryName(ChaosCraft.MODID, "spawn_block.json")
            );
        }


    }
    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent clientTickEvent){
        if(ChaosCraft.client == null){
            LOGGER.info("HELLO from Client starting");
            client = new ChaosCraftClient();
        }
        if(ChaosCraft.client != null){

            ChaosCraft.client.tick();
        }


    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.LivingUpdateEvent playerLoggedInEvent){

        if(
                ChaosCraft.client != null &&
                ChaosCraft.client.getState().equals(ChaosCraftClient.State.Uninitiated)
        ){
            LOGGER.info("onPlayerLoggedInEvent FIRING  2");
            ChaosCraft.client.init();
        }
    }




}
