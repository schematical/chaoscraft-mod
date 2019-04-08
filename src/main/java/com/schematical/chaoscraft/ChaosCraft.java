package com.schematical.chaoscraft;


import com.amazonaws.ImmutableRequest;
import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.commands.CommandChaosCraftObserve;
import com.schematical.chaoscraft.commands.CommandChaosCraftRefresh;
import com.schematical.chaoscraft.commands.CommandChaosCraftSessionStart;
import com.schematical.chaoscraft.commands.CommandDebug;
import com.schematical.chaoscraft.entities.ChaosCraftFitnessManager;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.entities.EntityRick;
import com.schematical.chaoscraft.proxies.IProxy;
import com.schematical.chaosnet.ChaosNet;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public static String topLeftMessage;
    public static int spawnHash;
    public static float activationThreshold = .3f;
    public static EntityOrganism adam = null;
    public static List<EntityPlayerMP> observingPlayers = new ArrayList<EntityPlayerMP>();
    public static double highScore = -99999;
    public static EntityOrganism highScoreOrg;
    public static BlockPos rickPos;
    public static SimpleNetworkWrapper networkWrapper;

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
    public static void queueSpawn(List<EntityOrganism> _orgsToReport){
        _orgsToReport.forEach((EntityOrganism organism)->{
            double totalScore = organism.entityFitnessManager.totalScore();
            if(totalScore > ChaosCraft.highScore){
                ChaosCraft.highScore = totalScore;
                ChaosCraft.highScoreOrg = organism;
            }
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

            if(organismList != null && ChaosCraft.lastResponse != null) {
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
                        entityOrganism.observableAttributeManager = new CCObservableAttributeManager(organism);
                        if(!organism.getName().equals("adam")){
                            TaxonomicRank taxonomicRank = null;
                            if (ChaosCraft.lastResponse == null) {
                                throw new ChaosNetException("`ChaosCraft.lastResponse` is `null`");
                            }
                            for (TaxonomicRank _taxonomicRank : ChaosCraft.lastResponse.getSpecies()) {
                                //ChaosCraft.logger.info(_taxonomicRank.getNamespace() + " == " + organism.getSpeciesNamespace());
                                if (_taxonomicRank.getNamespace().equals(organism.getSpeciesNamespace())) {
                                    taxonomicRank = _taxonomicRank;
                                }
                            }
                            if (taxonomicRank == null) {
                                throw new ChaosNetException("Could not find species: " + organism.getSpeciesNamespace());
                            }
                            String observedAttributesRaw = taxonomicRank.getObservedAttributesRaw();
                            if (
                                    observedAttributesRaw == null ||
                                            observedAttributesRaw.length() == 0
                                    ) {

                            } else {
                                try {
                                    JSONParser parser = new JSONParser();
                                    JSONObject jsonObject = (JSONObject) parser.parse(
                                            taxonomicRank.getObservedAttributesRaw()
                                    );
                                    entityOrganism.observableAttributeManager.parseData(jsonObject);
                                } catch (Exception exeception) {
                                    throw new ChaosNetException("Invalid `species.observedAttributes` JSON Found: " + taxonomicRank.getObservedAttributesRaw() + "\n\n-- " + exeception.getMessage());
                                }
                            }
                        }

                        entityOrganism.setCustomNameTag(organism.getName() + " - " + organism.getGeneration());

                        BlockPos pos = rick.getPosition();
                        if(organism.getName().equals("adam")) {

                            entityOrganism.setPosition(
                                pos.getX(),
                                pos.getY() + 2,
                                pos.getZ()
                            );

                        }else{
                           placeEntity(pos, entityOrganism);


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
                message += "MISSING STATS \n";
            }

        }
        if(ChaosCraft.highScoreOrg != null){
            message += "High Score: " + ChaosCraft.highScore + " - " + ChaosCraft.highScoreOrg.getCCNamespace() + "\n";
        }
        message += "Orgs in memory: " + ChaosCraft.organisims.size() + "\n";
        topLeftMessage = message;

        return spawnedEntityOrganisms;
    }
    public static void placeEntity(BlockPos pos, EntityOrganism entityOrganism){
        int range = 30;
        int minRange = 5;
        int yRange = 20;
        Vec3d rndPos = null;
        int saftyCatch = 0;
        int saftyMax = 10;
        while (
                rndPos == null &&
                        saftyCatch < saftyMax
                ) {
            saftyCatch++;
            double xPos = Math.floor((Math.random() * (range) * 2) - range);
            if (xPos > 0) {
                xPos += minRange;
            } else {
                xPos -= minRange;
            }
            double zPos = Math.floor((Math.random() * (range) * 2) - range);
            if (zPos > 0) {
                zPos += minRange;
            } else {
                zPos -= minRange;
            }
            rndPos = new Vec3d(
                    pos.getX() + xPos,
                    pos.getY() + Math.floor((Math.random() * yRange)),
                    pos.getZ() + zPos
            );
            if (!entityOrganism.getCanSpawnHere()) {
                rndPos = null;
            } else {
                BlockPos blockPos = new BlockPos(rndPos);

                IBlockState state = entityOrganism.world.getBlockState(blockPos);
                Material material = state.getMaterial();
                if (
                    !(
                        material == Material.WATER ||
                        material == Material.AIR
                    )
                ) {
                    rndPos = null;
                }else{
                    int saftyCatch2 = 0;
                    Vec3d lastPos = null;
                    boolean foundBottom = false;
                    int saftyMax2 = 40;
                    while(
                            saftyCatch2 < saftyMax2 &&
                            !foundBottom
                    ){
                        saftyCatch2 += 1;
                        lastPos = rndPos;
                        rndPos = new Vec3d(lastPos.x, lastPos.y - 1, lastPos.z);
                        blockPos = new BlockPos(rndPos);

                        state = entityOrganism.world.getBlockState(blockPos);
                        material = state.getMaterial();
                        if (material != Material.AIR){
                            rndPos = lastPos;
                            foundBottom = true;
                        }
                    }
                    if(saftyCatch2 >= saftyMax2){
                        rndPos = null;
                        //throw new ChaosNetException("Could not find good spawn pos after " + saftyCatch2 + " attempts");
                    }
                }
            }
            if(rndPos != null){
                entityOrganism.setPositionAndRotation(
                    rndPos.x,
                    rndPos.y,
                    rndPos.z,
                    (float) Math.random() * 360,
                    (float)Math.random() * 360
                );

                if(entityOrganism.isEntityInsideOpaqueBlock()){
                    rndPos = null;
                }
            }


        }
        if(saftyCatch == saftyMax){
            throw new ChaosNetException("Could not find good spawn pos after " + saftyCatch + " attempts");
        }
        if(rndPos == null){
            throw new ChaosNetException("This should be impossible");
        }

    }
    public static void chat(String message){
        PlayerList players =  rick.world.getMinecraftServer().getPlayerList();
        players.sendMessage(
                new TextComponentString(message)
        );
    }
    public static EntityOrganism getEntityOrganismByName(String name){
        Iterator<EntityOrganism> iterator = ChaosCraft.organisims.iterator();
        while(iterator.hasNext()){
            EntityOrganism org = iterator.next();
            if(org.getName().equals(name)){
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
            ChaosCraft.rickPos = pos;
            EntityRick rick = new EntityRick(world, "Rick");
            rick.setCustomNameTag("Rick");

            rick.setPosition(ChaosCraft.rickPos.getX(), ChaosCraft.rickPos.getY(),ChaosCraft.rickPos.getZ());

            world.spawnEntity(rick);
            ChaosCraft.rick = rick;

        }
        return ChaosCraft.rick;


    }

    public static void toggleObservingPlayer(EntityPlayerMP player) {
        if(ChaosCraft.observingPlayers.contains(player)){
            ChaosCraft.observingPlayers.remove(player);
            player.setGameType(GameType.CREATIVE);
        }else {
            player.setGameType(GameType.SPECTATOR);
            ChaosCraft.observingPlayers.add(player);
        }
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