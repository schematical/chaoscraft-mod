package com.schematical.chaoscraft;

import com.amazonaws.opensdk.SdkErrorHttpMetadata;
import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import com.schematical.chaoscraft.client.*;
import com.schematical.chaoscraft.commands.*;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.entities.OrgEntityRenderer;
import com.schematical.chaoscraft.items.ChaosItems;
import com.schematical.chaoscraft.network.ChaosNetworkManager;

import com.schematical.chaoscraft.server.ChaosCraftServer;

import com.schematical.chaoscraft.tileentity.ChaosTileEntity;
import com.schematical.chaosnet.ChaosNetClientBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schematical.chaosnet.ChaosNet;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("chaoscraft")
public class ChaosCraft
{

    public static final String MODID = "chaoscraft";
    public static ChaosCraftConfig config;
    public static ChaosNet sdk;
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static float activationThreshold = .1f;

    private static ChaosCraftClient client;
    private static ChaosCraftServer server;


    public ChaosCraft() {
   /*     if(true){
            return;
        }*/

        config = new ChaosCraftConfig();
        config.load();
        LOGGER.info("Config Loaded - Env: " + config.env);
        LOGGER.info("Config Username: " + config.username);

        setupSDK(config.env);
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onPlayerLoggedOutEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onWorldUnloadEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLivingUpdateEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onKeyInputEvent);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEntitySpawn);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::chunkEnterEvent);

        if(FMLEnvironment.dist.equals(Dist.CLIENT)) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onRenderWorldLastEvent);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::renderOverlayEvent);
        }
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, this::onEntityRegistry);

        ChaosBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ChaosTileEntity.TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ChaosItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ChaosNetworkManager.register();
    }
@SubscribeEvent
@OnlyIn(Dist.CLIENT)
    public void renderOverlayEvent(RenderGameOverlayEvent.Post event) {
        if(event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        client.render();
    }

    public static ChaosCraftServer getServer(){
        return server;
    }
    public static void setupSDK(String env){
        ChaosNetClientBuilder builder = ChaosNet.builder()
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
                );
        if(env != null){
            env = env.toString();
        }
        switch(env){
            case("prod"):
            case("pre-alpha"):
                builder = builder.endpoint("https://api.chaosnet.ai");
                break;
            case("dev"):
                builder = builder.endpoint("https://dev-api.chaosnet.ai");
        }
        sdk =  builder.build();
    }
    public static void auth(){

        if(config.refreshToken != null){
            LOGGER.info("REFRESH TOKEN:" + config.refreshToken.substring(0, 10) );
            AuthTokenRequest authTokenRequest = new AuthTokenRequest();
            authTokenRequest.setUsername(config.username);
            authTokenRequest.setRefreshToken(config.refreshToken);
            PostAuthTokenRequest postAuthTokenRequest = new PostAuthTokenRequest();
            postAuthTokenRequest.authTokenRequest(authTokenRequest);

            try {
                AuthLoginResponse authLoginResponse = ChaosCraft.sdk.postAuthToken(postAuthTokenRequest).getAuthLoginResponse();
                config.accessToken = authLoginResponse.getAccessToken();
                LOGGER.info("ACCESS TOKEN:" + config.refreshToken.substring(0, 10) + "'....");
            }catch (ChaosNetException exception) {
                SdkErrorHttpMetadata sdkErrorHttpMetadata = exception.sdkHttpMetadata();
                Integer statusCode = null;
                if(sdkErrorHttpMetadata != null) {
                    statusCode = sdkErrorHttpMetadata.httpStatusCode();
                    switch (statusCode) {
                        case (400):

                            //ChaosCraft.getServer().repair();
                            break;
                        case (401):
                        case (403):
                            ChaosCraft.config.refreshToken = null;
                            ChaosCraft.config.accessToken = null;
                            ChaosCraft.config.save();
                            break;

                    }
                }
                ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
                String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();//new String(byteBuffer.as().array(), StandardCharsets.UTF_8 );
                ChaosCraft.LOGGER.error("ChaosServerThread  Error: " + message + " - statusCode: " + statusCode);

            }

        }
    }

    public static ChaosCraftClient getClient() {
        return client;
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
       //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

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
        /*LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));*/
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent

    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts

        server = new ChaosCraftServer(event.getServer());

        CCSummonCommand.register(event.getCommandDispatcher());
        CCAuthCommand.register(event.getCommandDispatcher());
        CCTestCommand.register(event.getCommandDispatcher());
        CCHardResetCommand.register(event.getCommandDispatcher());
        CCBlockResetCommand.register(event.getCommandDispatcher());
        server.loadTrainingRoomPackage();
    }

    @SubscribeEvent
    public void onClientStarting(FMLClientSetupEvent event) {
       // do something when the server starts

        client = new ChaosCraftClient(event.getMinecraftSupplier().get());
        client.preInit();

        //If they are not logged in we need to do that



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
        //setTrackingRange
        event.getRegistry().registerAll(
            EntityType.Builder.create(OrgEntity::new, EntityClassification.CREATURE).setTrackingRange(128).build(MODID + ":org_entity").setRegistryName(MODID, "org_entity")
        );

    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    }

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent clientTickEvent){
        if(ChaosCraft.client == null){

            client = new ChaosCraftClient(null);
        }
        if(ChaosCraft.client != null){

            ChaosCraft.client.tick();
        }

    }
    @SubscribeEvent
    public void onLivingUpdateEvent(PlayerEvent.LivingUpdateEvent livingUpdateEvent){



        if(
            ChaosCraft.client != null &&
            ChaosCraft.client.getState().equals(ChaosCraftClient.State.Uninitiated)
        ){
            ChaosCraft.client.init();
        }
    }
    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent){



        if(
            ChaosCraft.client != null &&
            ChaosCraft.client.getState().equals(ChaosCraftClient.State.Uninitiated)
        ){



        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent playerLoggedOutEvent){


        if(
                ChaosCraft.server != null
        ){

           ChaosCraft.server.logOutPlayer(playerLoggedOutEvent.getPlayer());
        }

        if(
            ChaosCraft.client != null
        ){

            //ChaosCraft.client.init();
        }
    }
    @SubscribeEvent
    public void onWorldUnloadEvent(WorldEvent.Unload worldUnloadEvent) {
        if(client != null) {
           client.onWorldUnload();
       }
    }
    @SubscribeEvent
    public void onKeyInputEvent(final InputEvent.KeyInputEvent event)  {
        if(client == null){
            return;
        }
        client.onKeyInputEvent(event);
    }
    @SubscribeEvent
    public void onEntitySpawn(EntityJoinWorldEvent entityJoinWorldEvent){
        Entity entity = entityJoinWorldEvent.getEntity();
        if(entity instanceof MonsterEntity){
            MonsterEntity monsterEntity = (MonsterEntity) entity;
            monsterEntity.targetSelector.addGoal(2, new NearestAttackableTargetGoal(monsterEntity, OrgEntity.class, true));
        }

    }
    @SubscribeEvent
    public void chunkEnterEvent(EntityEvent.EnteringChunk event) {
        if(server != null){
            server.chunkEnterEvent(event);
        }
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if(client != null){
            client.onRenderWorldLastEvent(event);
        }
    }

}
