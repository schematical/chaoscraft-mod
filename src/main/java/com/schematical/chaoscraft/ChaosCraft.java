package com.schematical.chaoscraft;


import com.amazonaws.ImmutableRequest;
import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;

import com.schematical.chaoscraft.client.ChaosCraftClient;
import com.schematical.chaoscraft.commands.CommandChaosCraftObserve;
import com.schematical.chaoscraft.commands.CommandChaosCraftRefresh;
import com.schematical.chaoscraft.commands.CommandChaosCraftSessionStart;
import com.schematical.chaoscraft.commands.CommandDebug;
import com.schematical.chaoscraft.entities.ChaosCraftFitnessManager;

import com.schematical.chaoscraft.proxies.IProxy;
import com.schematical.chaoscraft.server.ChaosCraftServer;
import com.schematical.chaosnet.ChaosNet;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.*;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


@Mod(modid = ChaosCraft.MODID, name = ChaosCraft.NAME, version = ChaosCraft.VERSION)
public class ChaosCraft
{

    public static final String MODID = "chaoscraft";
    public static final String NAME = "ChaosCraft";
    public static final String VERSION = "1.0";
    public static ChaosNet sdk;
    public enum CCNNetTickMode {
            Client,
            Server
    }
    public static CCNNetTickMode nNetTickMode = CCNNetTickMode.Client;

    @SidedProxy(modId=ChaosCraft.MODID,clientSide="com.schematical.chaoscraft.proxies.ClientProxy", serverSide="com.schematical.chaoscraft.proxies.ServerProxy")
    public static IProxy proxy;
    public static Logger logger;
    public static ChaosCraftConfig config;


    public static ChaosCraftFitnessManager fitnessManager;
    public static int ticksSinceLastSpawn = 0;



    public static TrainingRoomSessionNextResponse lastResponse;



    public static int spawnHash;
    public static float activationThreshold = .3f;

    public static SimpleNetworkWrapper networkWrapper;
    public static ChaosCraftServer server;
    public static ChaosCraftClient client;
    @Mod.Instance
    public static ChaosCraft INSTANCE;


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

        if(config.accessToken!=null) {
            startTrainingSession();
            loadFitnessFunctions();
        }

        ForgeChunkManager.setForcedChunkLoadingCallback(this, new ForcedChunkLoadingCallback());
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
        try {
            GetUsernameTrainingroomsTrainingroomFitnessrulesResult result = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomFitnessrules(fitnessRulesRequest);
            String fitnessRulesRaw = result.getTrainingRoomFitnessRules().getFitnessRulesRaw();

            JSONParser parser = new JSONParser();

            JSONArray obj = (JSONArray) parser.parse(
                    fitnessRulesRaw
            );
            ChaosCraft.fitnessManager = new ChaosCraftFitnessManager();
            ChaosCraft.fitnessManager.parseData(obj);
        } catch (ChaosNetException e) {
            e.printStackTrace();
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
        try {
            PostUsernameTrainingroomsTrainingroomSessionsStartRequest startSessionRequest = new PostUsernameTrainingroomsTrainingroomSessionsStartRequest();
            startSessionRequest.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
            startSessionRequest.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);

            PostUsernameTrainingroomsTrainingroomSessionsStartResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsStart(startSessionRequest);
            ChaosCraft.config.sessionNamespace = result.getTrainingRoomSession().getNamespace();
            ChaosCraft.config.save();
        }catch(ChaosNetException exception){
            if(exception.sdkHttpMetadata().httpStatusCode() == 401){
                ChaosCraft.logger.error(exception.getMessage());
                String message = "Your login has expired. Please re-run `/chaoscraft-auth {username} {password}`";
                //ChaosCraft.chat(message);
                ChaosCraft.logger.error(message);
            }else{
                throw exception;
            }

        }

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





    }




    public class ChaosNetSigner implements ChaosnetCognitoUserPool {
        @Override
        public String generateToken(ImmutableRequest<?> immutableRequest) {
            return ChaosCraft.config.accessToken;
        }
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // register server commands
        event.registerServerCommand(new CommandChaosCraftObserve());
        event.registerServerCommand(new CommandDebug());
        event.registerServerCommand(new CommandChaosCraftSessionStart());
        event.registerServerCommand(new CommandChaosCraftRefresh());

    }
    public static void repair(){
        try{

            PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest request = new PostUsernameTrainingroomsTrainingroomSessionsSessionRepairRequest();
            request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
            request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
            request.setSession(ChaosCraft.config.sessionNamespace);
            PostUsernameTrainingroomsTrainingroomSessionsSessionRepairResult response = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsSessionRepair(request);

        }catch(ChaosNetException exception){
            ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
            String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();
            exception.setMessage(message);
            throw exception;
        }
    }





}