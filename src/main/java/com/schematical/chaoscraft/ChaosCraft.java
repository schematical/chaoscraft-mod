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
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
    public static int ticksSinceLastSpawn = 0;
    public static Thread thread;

    public static List<Organism> orgsToSpawn;
    public static List<EntityOrganism> orgsToReport = new ArrayList<EntityOrganism>();
    public static TrainingRoomSessionNextResponse lastResponse;
    public static int consecutiveErrorCount = 0;
    public static FontRenderer fontRenderer;
    public static String topLeftMessage;
    public static int spawnHash;
    public static float activationThreshold = .3f;
    public static EntityOrganism adam = null;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new CCEventListener().getClass());
        proxy.preInit(event);
        config = new ChaosCraftConfig();
        config.load();
        spawnHash = (int)Math.round(Math.random() * 9999);


        logger = event.getModLog();


        //thread.start();

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

        startTrainingSession();
        loadFitnessFunctions();



    }
    public static void auth(){
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
    }
    public static void queueSpawn(List<EntityOrganism> _orgsToReport){
        _orgsToReport.forEach((EntityOrganism organism)->{
            ChaosCraft.orgsToReport.add(organism);
        });
        if(thread != null){
            return;
        }
        ChaosCraft.ticksSinceLastSpawn = 0;

        thread = new Thread(new ChaosThread(), "ChaosThread");
        thread.start();
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
    /*public static TrainingRoomSessionNextResponse getNextOrgs(List<EntityOrganism> organismList){
        String namespaces = "";

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
                namespaces += namespace + "    ";
            }

        }
        logger.info("GETTING getNextOrgs: " + ((organismList != null) ? organismList.size() : "") + " - " + namespaces + " - Ticks: " + ChaosCraft.ticksSinceLastSpawn);

        trainingRoomSessionNextRequest.setReport(report);
        trainingRoomSessionNextRequest.setNNetRaw(true);
        TrainingRoomSessionNextResponse response = null;
        try {
            request.setTrainingRoomSessionNextRequest(trainingRoomSessionNextRequest);
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsSessionNext(request);


            response = result.getTrainingRoomSessionNextResponse();
        }catch(ChaosNetException exeception){
            //logger.error(exeception.getMessage());
            throw exeception;
        }
        return response;
    }*/


    @EventHandler
    public void init(FMLInitializationEvent event)
    {


        // some example code
        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());


    }
    public static List<EntityOrganism> spawnOrgs() {
        return ChaosCraft.spawnOrgs(null);
    }
    public static List<EntityOrganism> spawnOrgs(List<Organism> organismList){

        List<EntityOrganism> spawnedEntityOrganisms = new ArrayList<EntityOrganism>();
        World world = rick.getEntityWorld();
        String generation = "-1";
        if(!world.isRemote) {
            if(organismList != null) {
                for (int i = 0; i < organismList.size(); i++) {
                    Organism organism = organismList.get(i);
                    generation =  organism.getGeneration().toString();

                    boolean orgIsAlreadyAlive = false;
                    for (int ii = 0; ii < organisims.size(); ii++) {
                        EntityOrganism entityOrganism = organisims.get(ii);
                        if (entityOrganism.getCCNamespace().equals(organism.getNamespace())) {
                            orgIsAlreadyAlive = true;
                        }
                    }


                    if (!orgIsAlreadyAlive) {
                        NNetRaw nNetRaw = null;
                        String nNetRawStr = organism.getNNetRaw();
                        if (nNetRawStr != null) {
                            nNetRaw = new NNetRaw();
                            nNetRaw.setNNetRaw(nNetRawStr);
                        } else {
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
                        int range = 5;
                        int yRange = 1;
                        Vec3d rndPos = null;
                        int saftyCatch = 0;
                        while (
                                rndPos == null &&
                                        saftyCatch < 10
                                ) {
                            saftyCatch++;
                            rndPos = new Vec3d(
                                    pos.getX() + Math.floor((Math.random() * range * 2) - range),
                                    pos.getY() + Math.floor((Math.random() * yRange)),
                                    pos.getZ() + Math.floor((Math.random() * range * 2) - range)
                            );
                            entityOrganism.setPosition(
                                    rndPos.x,
                                    rndPos.y,
                                    rndPos.z
                            );

                            if (!entityOrganism.getCanSpawnHere()) {
                                rndPos = null;
                            }else {
                                BlockPos blockPos = new BlockPos(rndPos);
                            /*if(!rick.world.getWorldBorder().contains(blockPos)){
                                rndPos = null;
                            }*/
                                IBlockState state = world.getBlockState(pos);
                                Material material = state.getMaterial();
                                if (
                                        !(
                                                material == Material.WATER ||
                                                        material == Material.AIR
                                        )
                                        ) {
                                    rndPos = null;
                                }
                            }
                        }

                        entityOrganism.setSpawnHash(ChaosCraft.spawnHash);
                        ChaosCraft.organisims.add(entityOrganism);
                        spawnedEntityOrganisms.add(entityOrganism);
                        world.spawnEntity(entityOrganism);
                    }
                }
            }
        }
        String message = "";

        message += "Generation: " + generation + "\n";
        if(ChaosCraft.lastResponse != null) {
            Stats stats = ChaosCraft.lastResponse.getStats();
            if (stats != null) {
                message += "Gen Progress: " + stats.getGenProgress() + "\n";
            } else {
                message += "MISSING STATS";
            }
        }
        //message += "Spawned So Far: " + ChaosCraft.lastResponse.getStats().getOrgsSpawnedSoFar() + "\n";
        //message += "Reported So Far: " + ChaosCraft.lastResponse.getStats().getOrgsReportedSoFar() + "\n";
        //message += "Total: " + ChaosCraft.lastResponse.getStats().getTotalOrgsPerGen() + "\n";
        //message += "Ticks Since Last Spawn: " + ticksSinceLastSpawn + "\n";
        message += "Orgs in memory: " + ChaosCraft.organisims.size() + "\n";
        topLeftMessage = message;

        return spawnedEntityOrganisms;
    }
    public static void chat(String message){
        PlayerList players =  rick.world.getMinecraftServer().getPlayerList();
        players.sendMessage(
                new TextComponentString(message)
        );
    }
    public static EntityOrganism getEntityOrganismByName(String name){
        for(EntityOrganism org : ChaosCraft.organisims){
            if(org.getName() == name){
                return org;
            }
        }
        return null;
    }
    public static EntityOrganism getEntityOrganismById(String id){
        for(EntityOrganism org : ChaosCraft.organisims){
            if(org.getCCNamespace() == id){
                return org;
            }
        }
        return null;
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