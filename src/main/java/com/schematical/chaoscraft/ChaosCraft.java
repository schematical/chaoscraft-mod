package com.schematical.chaoscraft;


import com.amazonaws.ImmutableRequest;
import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;

import com.google.common.collect.Sets;
import com.schematical.chaoscraft.entities.ChaosCraftFitnessManager;
import com.schematical.chaoscraft.entities.EntityEvilRabbit;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.entities.EntityRick;
import com.schematical.chaoscraft.proxies.IProxy;

import com.schematical.chaosnet.ChaosNet;

import com.schematical.chaosnet.ChaosNetClientBuilder;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Mod(modid = ChaosCraft.MODID, name = ChaosCraft.NAME, version = ChaosCraft.VERSION)
public class ChaosCraft
{

    public static final String MODID = "chaoscraft";
    public static final String NAME = "ChaosCraft";
    public static final String VERSION = "1.0";
    public static ChaosNet sdk;

    @SidedProxy(modId=ChaosCraft.MODID,clientSide="com.schematical.chaoscraft.proxies.ClientProxy", serverSide="com.schematical.chaoscraft.proxies.ServerProxy")
    public static IProxy proxy;
    public static Logger logger;
    public static ChaosCraftConfig config;
    public static EntityRick rick;
    public static Set<EntityOrganism> organisims = Sets.<EntityOrganism>newLinkedHashSet();
    public static ChaosCraftFitnessManager fitnessManager;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        config = new ChaosCraftConfig();
        config.load();

        logger = event.getModLog();

        sdk =  ChaosNet.builder()
                .connectionConfiguration(
                        new ConnectionConfiguration()
                                .maxConnections(100)
                                .connectionMaxIdleMillis(1000)
                )
                .timeoutConfiguration(
                        new TimeoutConfiguration()
                                .httpRequestTimeout(10000)
                                .totalExecutionTimeout(20000)
                                .socketTimeout(10000)
                )
                .signer(
                        (ChaosnetCognitoUserPool) request -> ChaosCraft.config.accessToken
                        //new ChaosNetSigner()
                )
                .build();

        if(config.refreshToken != null){
            AuthTokenRequest authTokenRequest = new AuthTokenRequest();
            authTokenRequest.setUsername(config.username);
            authTokenRequest.setRefreshToken(config.refreshToken);
            PostAuthTokenRequest postAuthTokenRequest = new PostAuthTokenRequest();
            postAuthTokenRequest.authTokenRequest(authTokenRequest);

            try {
                AuthLoginResponse authLoginResponse = ChaosCraft.sdk.postAuthToken(postAuthTokenRequest).getAuthLoginResponse();
                config.accessToken = authLoginResponse.getAccessToken();
            }catch (ChaosNetException e) {
                logger.error(e);
            }

        }

        startTrainingSession();


    }
    public static void startTrainingSession(){
        if(
            ChaosCraft.config.trainingRoomNamespace == null ||
            ChaosCraft.config.trainingRoomUsernameNamespace == null
        ){
            return;
        }
        if(ChaosCraft.config.sessionNamespace != null){
            //Do stuff

        }
        PostUsernameTrainingroomsTrainingroomSessionsStartRequest startSessionRequest = new PostUsernameTrainingroomsTrainingroomSessionsStartRequest();
        startSessionRequest.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
        startSessionRequest.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);

        PostUsernameTrainingroomsTrainingroomSessionsStartResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsStart(startSessionRequest);
        ChaosCraft.config.sessionNamespace = result.getTrainingRoomSession().getNamespace();
        ChaosCraft.config.save();

    }
    public static TrainingRoomSessionNextResponse getNextOrgs(List<Organism> organismList){
        PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest request = new PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest();
        request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
        request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
        request.setSession(ChaosCraft.config.sessionNamespace);
        PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsSessionNext(request);
        TrainingRoomSessionNextResponse response = result.getTrainingRoomSessionNextResponse();
        return response;
    }


    @EventHandler
    public void init(FMLInitializationEvent event)
    {


        // some example code
        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());


    }
    public static List<EntityOrganism> spawnOrgs(){
        TrainingRoomSessionNextResponse response = ChaosCraft.getNextOrgs(null);
        List<Organism> organismList = response.getOrganisms();
        List<EntityOrganism> spawnedEntityOrganisms = new ArrayList<EntityOrganism>();
        World world = rick.getEntityWorld();
        if(!world.isRemote) {
            for(int i = 0; i < organismList.size(); i++) {
                Organism organism = organismList.get(i);
                GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest request = new GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest();
                request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
                request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
                request.setOrganism(organism.getNamespace());
                GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult result = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomOrganismsOrganismNnet(request);
                NNetRaw nNetRaw = result.getNNetRaw();

                EntityOrganism entityOrganism = new EntityOrganism(world, organism.getNamespace());
                entityOrganism.setCustomNameTag(organism.getName());
                BlockPos pos = rick.getPosition();
                int range = 3;
                Vec3d rndPos = null;
                int saftyCatch = 0;
                while(
                        rndPos == null &&
                                saftyCatch < 10
                        ) {
                    saftyCatch ++;
                    rndPos = new Vec3d(
                            pos.getX() + Math.floor((Math.random() * range * 2) - range),
                            pos.getY() + Math.floor((Math.random() * range * 2) - range),
                            pos.getZ() + Math.floor((Math.random() * range * 2) - range)
                    );
                    entityOrganism.setPosition(
                            rndPos.x,
                            rndPos.y,
                            rndPos.z
                    );
                    if(!entityOrganism.getCanSpawnHere()){
                        rndPos = null;
                    }
                }

                entityOrganism.attachOrganism(organism);
                entityOrganism.attachNNetRaw(nNetRaw);
                ChaosCraft.organisims.add(entityOrganism);
                spawnedEntityOrganisms.add(entityOrganism);
                world.spawnEntity(entityOrganism);
            }
        }
        return spawnedEntityOrganisms;
    }
    public static EntityRick spawnRick(World world, BlockPos pos ){
        if(ChaosCraft.rick != null) {
            return ChaosCraft.rick;
        }

        if (!world.isRemote) {

            EntityRick rick = new EntityRick(world, "Rick");
            rick.setCustomNameTag("Rick");

            rick.setPosition(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);

            world.spawnEntity(rick);
            ChaosCraft.rick = rick;

        }
        return ChaosCraft.rick;


    }
    public class ChaosNetSigner implements ChaosnetCognitoUserPool {
        @Override
        public String generateToken(ImmutableRequest<?> immutableRequest) {
            return ChaosCraft.config.accessToken;
        }
    }




}