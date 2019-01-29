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
import com.schematical.chaosnet.model.transform.GetUsernameTrainingroomsTrainingroomFitnessrulesResultJsonUnmarshaller;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
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
    public static List<EntityOrganism> organisims = new ArrayList<EntityOrganism>();
    public static ChaosCraftFitnessManager fitnessManager;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new CCEventListener().getClass());
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
                                .httpRequestTimeout(20000)
                                .totalExecutionTimeout(30000)
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
        loadFitnessFunctions();

    }

    public static void loadFitnessFunctions(){
        if(
            ChaosCraft.config.trainingRoomNamespace == null ||
            ChaosCraft.config.trainingRoomUsernameNamespace == null
        ){
            ChaosCraft.logger.error("Not enough TrainingRoom Data set");
        }

        GetUsernameTrainingroomsTrainingroomFitnessrulesRequest fitnessRulesRequest = new GetUsernameTrainingroomsTrainingroomFitnessrulesRequest();
        fitnessRulesRequest.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
        fitnessRulesRequest.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);

        GetUsernameTrainingroomsTrainingroomFitnessrulesResult result = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomFitnessrules(fitnessRulesRequest);
        String fitnessRulesRaw = result.getTrainingRoomFitnessRules().getFitnessRulesRaw();

        JSONParser parser = new JSONParser();
        try {
            JSONArray obj = (JSONArray) parser.parse(
                    fitnessRulesRaw
            );
            ChaosCraft.fitnessManager = new ChaosCraftFitnessManager();
            ChaosCraft.fitnessManager.parseData(obj);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
    public static TrainingRoomSessionNextResponse getNextOrgs(List<EntityOrganism> organismList){
        logger.info("GETTING getNextOrgs: " + ((organismList != null) ? organismList.size() : ""));
        PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest request = new PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest();
        request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
        request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
        request.setSession(ChaosCraft.config.sessionNamespace);

        TrainingRoomSessionNextRequest trainingRoomSessionNextRequest = new TrainingRoomSessionNextRequest();

        Collection<TrainingRoomSessionNextReport> report = new ArrayList<TrainingRoomSessionNextReport>();
        if(organismList != null) {
            for (EntityOrganism organism : organismList) {
                String namespace = organism.getCCNamespace();
                if(namespace != null) {
                    TrainingRoomSessionNextReport reportEntry = new TrainingRoomSessionNextReport();
                    reportEntry.setScore(organism.entityFitnessManager.totalScore());
                    reportEntry.setNamespace(organism.getCCNamespace());
                    report.add(reportEntry);
                }
            }

        }
        trainingRoomSessionNextRequest.setReport(report);
        trainingRoomSessionNextRequest.setNNetRaw(true);

        request.setTrainingRoomSessionNextRequest(trainingRoomSessionNextRequest);
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
    public static List<EntityOrganism> spawnOrgs() {
        return ChaosCraft.spawnOrgs(null);
    }
    public static List<EntityOrganism> spawnOrgs(List<EntityOrganism> reportOrgs){
        TrainingRoomSessionNextResponse response = ChaosCraft.getNextOrgs(reportOrgs);
        List<Organism> organismList = response.getOrganisms();
        List<EntityOrganism> spawnedEntityOrganisms = new ArrayList<EntityOrganism>();
        World world = rick.getEntityWorld();
        if(!world.isRemote) {
            for(int i = 0; i < organismList.size(); i++) {
                Organism organism = organismList.get(i);
                NNetRaw nNetRaw = null;
                String nNetRawStr = organism.getNNetRaw();
                if(nNetRawStr != null){
                    nNetRaw = new NNetRaw();
                    nNetRaw.setNNetRaw(nNetRawStr);
                }else{
                    GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest request = new GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest();
                    request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
                    request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
                    request.setOrganism(organism.getNamespace());
                    GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult result = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomOrganismsOrganismNnet(request);
                    nNetRaw = result.getNNetRaw();
                }

                EntityOrganism entityOrganism = new EntityOrganism(world, organism.getNamespace());
                entityOrganism.attachOrganism(organism);
                entityOrganism.attachNNetRaw(nNetRaw);
                entityOrganism.setCustomNameTag(organism.getName() + " - " + organism.getGeneration());
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


                ChaosCraft.organisims.add(entityOrganism);
                spawnedEntityOrganisms.add(entityOrganism);
                world.spawnEntity(entityOrganism);
            }
        }
        String message = "";
        message += "Gen Progress: " + response.getStats().getGenProgress() + "\n";
        message += "Spawned So Far: " + response.getStats().getOrgsSpawnedSoFar() + "\n";
        message += "Reported So Far: " + response.getStats().getOrgsReportedSoFar() + "\n";
        message += "Total: " + response.getStats().getTotalOrgsPerGen() + "\n";
        ChaosCraft.chat(message);
        return spawnedEntityOrganisms;
    }
    public static void chat(String message){
        PlayerList players =  rick.world.getMinecraftServer().getPlayerList();
        players.sendMessage(
                new TextComponentString(message)
        );
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