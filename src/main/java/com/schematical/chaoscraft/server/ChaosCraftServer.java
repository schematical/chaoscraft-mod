package com.schematical.chaoscraft.server;

import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.fitness.ChaosCraftFitnessManager;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientOutputNeuronActionPacket;
import com.schematical.chaoscraft.network.packets.CCServerEntitySpawnedPacket;
import com.schematical.chaoscraft.network.packets.CCServerRequestTrainingRoomGUIPacket;
import com.schematical.chaoscraft.network.packets.ServerIntroInfoPacket;
import com.schematical.chaosnet.ChaosNet;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ChaosCraftServer {
    public HashMap<String, ChaosCraftServerPlayerInfo> userMap = new HashMap<String, ChaosCraftServerPlayerInfo>();

    public int consecutiveErrorCount;
    public Thread thread;
    public MinecraftServer server;
    public static int spawnHash;
    public static HashMap<String, ServerOrgManager> organisms = new HashMap<String, ServerOrgManager>();
    public ChaosCraftFitnessManager fitnessManager;
    public int longTickCount = 0;

    public ChaosCraftServer(MinecraftServer server) {

        this.server = server;
        spawnHash = (int)Math.round(Math.random() * 9999);
    }

    public void tick(){
        if(
            ChaosCraft.getServer().fitnessManager == null
        ){
            loadFitnessFunctions();
            return;
        }





        checkForDeadOrgs();
        List<ServerOrgManager> orgNamepacesQueuedToSpawn = getOrgsWithState(ServerOrgManager.State.PlayerAttached);
        if(
            orgNamepacesQueuedToSpawn.size() > 0 &&
            thread == null
        ){
            thread = new Thread(new ChaosServerThread(), "ChaosServerThread");
            thread.start();
        }
        List<ServerOrgManager> orgsToSpawn = getOrgsWithState(ServerOrgManager.State.QueuedForSpawn);
        if(orgsToSpawn.size() > 0){
            for (ServerOrgManager serverOrgManager : orgsToSpawn) {
                spawnOrg(serverOrgManager);
            }
        }


        longTickCount += 1;
        if(longTickCount > 5 * 20){
            longTick();
        }
    }
    public List<ServerOrgManager> getOrgsWithState(ServerOrgManager.State state){
        List<ServerOrgManager> orgManagers = new ArrayList<ServerOrgManager>();
        for (ServerOrgManager serverOrgManager : organisms.values()) {
            if(serverOrgManager.getState().equals(state)){
                orgManagers.add(serverOrgManager);
            }
        }
        return orgManagers;
    }

    private void longTick() {
        for (ChaosCraftServerPlayerInfo value : userMap.values()) {
            if(value.organisims.size() < ChaosCraft.config.maxBotCount){
                //Request more bots

                //TODO: Clean up the Dead orgs out of the list
            }
        }
        cleanUp();
    }

    public void queueOrgToSpawn(String orgNamespace, ServerPlayerEntity player){
        if(!userMap.containsKey(player.getUniqueID().toString())){
            ChaosCraft.LOGGER.error("Could not find ServerPlayerEntity in `userMap` with UUID: " + player.getUniqueID().toString());
            return;
        }
        ChaosCraftServerPlayerInfo serverPlayerInfo = userMap.get(player.getUniqueID().toString());
        serverPlayerInfo.organisims.add(orgNamespace);
        ServerOrgManager serverOrgManager = new ServerOrgManager();
        serverOrgManager.setTmpNamespace(orgNamespace);
        serverOrgManager.setPlayerEntity(player);

        organisms.put(orgNamespace, serverOrgManager);

    }


    public void checkAuth(String accessToken, ServerPlayerEntity player) {
        ChaosCraft.LOGGER.info("CheckingAuth: " + accessToken.substring(0, 10) + "...");


        ChaosNet sdk =  ChaosNet.builder()
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
                        (ChaosnetCognitoUserPool) request -> accessToken
                        //new ChaosNetSigner()
                )
                .build();
        GetAuthWhoamiRequest getAuthWhoamiRequest = new GetAuthWhoamiRequest();
        AuthWhoamiRequest authWhoamiRequest = new AuthWhoamiRequest();
       // authWhoamiRequest.accessToken(accessToken);
        getAuthWhoamiRequest.setAuthWhoamiRequest(authWhoamiRequest);
        GetAuthWhoamiResult getAuthWhoamiResult = sdk.getAuthWhoami(getAuthWhoamiRequest);
        AuthWhoamiResponse authWhoamiResponse = getAuthWhoamiResult.getAuthWhoamiResponse();



        ChaosCraft.LOGGER.info("CheckingAuth Response: " + authWhoamiResponse.getUsername());
        player.setCustomName(new TranslationTextComponent(authWhoamiResponse.getUsername()));
        ChaosCraftServerPlayerInfo playerInfo = new ChaosCraftServerPlayerInfo();
        playerInfo.authWhoamiResponse = getAuthWhoamiResult.getAuthWhoamiResponse();
        playerInfo.playerUUID = player.getUniqueID();

        userMap.put( player.getUniqueID().toString(), playerInfo);


        if(
            ChaosCraft.config.trainingRoomNamespace == null ||
            ChaosCraft.config.trainingRoomUsernameNamespace == null
        ){
            CCServerRequestTrainingRoomGUIPacket serverIntroInfoPacket = new CCServerRequestTrainingRoomGUIPacket( );

            ChaosNetworkManager.sendTo(serverIntroInfoPacket, player);
            ChaosCraft.LOGGER.info("SENT `CCServerRequestTrainingRoomGUIPacket` ");

        }else {
            sendServerInfoPacket(player);
        }
    }

    private void sendServerInfoPacket(ServerPlayerEntity player) {
        //Send that user the training Room info from here
        ServerIntroInfoPacket serverIntroInfoPacket = new ServerIntroInfoPacket(
                ChaosCraft.config.trainingRoomNamespace,
                ChaosCraft.config.trainingRoomUsernameNamespace,
                ChaosCraft.config.sessionNamespace
        );

        ChaosNetworkManager.sendTo(serverIntroInfoPacket, player);
        ChaosCraft.LOGGER.info("SENT `serverIntroInfoPacket`: " + serverIntroInfoPacket.getTrainingRoomNamespace() + ", " + serverIntroInfoPacket.getTrainingRoomUsernameNamespace() + ", " + serverIntroInfoPacket.getSessionNamespace());

    }

    public OrgEntity spawnOrg(ServerOrgManager serverOrgManager) {
        if(!serverOrgManager.getState().equals(ServerOrgManager.State.QueuedForSpawn)) {



            String debugMessage = "Server - " + serverOrgManager.getCCNamespace() + " has invalid spawn state: " + serverOrgManager.getState()  + "organisms: " + organisms.size();

            if(serverOrgManager.getEntity() != null){
                ServerPlayerEntity serverPlayerEntity = serverOrgManager.getServerPlayerEntity();
                double dist = serverPlayerEntity.getPositionVec().distanceTo(serverOrgManager.getEntity().getPositionVec());
                debugMessage += " EntityId: " + serverOrgManager.getEntity().getEntityId() + " dist: " + dist;
            }
            ChaosCraft.LOGGER.error(debugMessage);

           return null;
        }



        ServerWorld serverWorld = this.server.getWorld(DimensionType.OVERWORLD);
        PlayerEntity playerEntity = serverWorld.getPlayers().get(0);

        OrgEntity orgEntity = OrgEntity.ORGANISM_TYPE.create(serverWorld);
        orgEntity.setSpawnHash(ChaosCraft.getServer().spawnHash);
        BlockPos pos = null;
        int saftyCatch = 0;
        while(pos == null){
            saftyCatch ++;
            if (saftyCatch > 20) {
                ChaosCraft.LOGGER.error("Safty Catch Hit: " + saftyCatch);
                return orgEntity;
            }
            BlockPos pos1 = serverWorld.getSpawnPoint();
            int range = 32;
            pos1 = pos1.add(
                new Vec3i(
                    Math.floor(Math.random() *  range * 2) - range,
                    5,
                    Math.floor(Math.random() *  range * 2) - range
                )
            );
            int y = pos1.getY();
            boolean blnFound = false;
            int saftyCatch2 = 0;
            while(!blnFound && saftyCatch2 < 80) {
                saftyCatch2 ++;
                y--;
                BlockPos blockPos2 = new BlockPos(
                        pos1.getX(),
                        y,
                        pos1.getZ()
                );
                BlockState blockState = server.getWorld(DimensionType.OVERWORLD).getBlockState(blockPos2);
                //ChaosCraft.LOGGER.info("blockState: " + blockState.toString() + "  --? " + blockState.getBlock().toString());
                if(!blockState.getBlock().equals(Blocks.AIR)){
                    y += 2;
                    blnFound = true;
                    pos = new BlockPos(
                        blockPos2.getX(),
                        y,
                        blockPos2.getZ()
                    );
                }

            }
            //pos = serverWorld.getDimension().findSpawn(pos1.getX(), pos1.getZ(), false);

        }

        ChaosCraft.LOGGER.info("Spawning: " + pos.getX() +", " + pos.getY()+", " +  pos.getZ());
        orgEntity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), playerEntity.rotationYaw, playerEntity.rotationPitch);

        serverOrgManager.attachOrgEntity(orgEntity);
        serverWorld.summonEntity(orgEntity);



        sendChaosCraftServerPlayerInfo(serverOrgManager);

        return orgEntity;
    }
    protected  void sendChaosCraftServerPlayerInfo(ServerOrgManager serverOrgManager){
        ServerPlayerEntity serverPlayerEntity = serverOrgManager.getServerPlayerEntity();
        ChaosNetworkManager.sendTo(
                new CCServerEntitySpawnedPacket(
                        serverOrgManager.getCCNamespace(),
                        serverOrgManager.getEntity().getEntityId()
                ),
                serverPlayerEntity
        );
    }
    public void processClientOutputNeuronActionPacket(CCClientOutputNeuronActionPacket message){
        if(!organisms.containsKey(message.getOrgNamespace())){
            ChaosCraft.LOGGER.error("Server Cannot find org to process neuron action packet: " + message.getOrgNamespace());
            return;
        }
        ServerOrgManager serverOrgManager = organisms.get(message.getOrgNamespace());
        serverOrgManager.queueOutputNeuronAction(message);

    }
    public List<ServerOrgManager> checkForDeadOrgs(){

        List<ServerOrgManager> serverOrgManagers = getOrgsWithState(ServerOrgManager.State.Ticking);
        for (ServerOrgManager serverOrgManager : serverOrgManagers) {

            if (!serverOrgManager.getEntity().isAlive()) {

                serverOrgManager.markDead();

            }
        }
        return serverOrgManagers;
    }
    public void loadFitnessFunctions(){
        if(
            fitnessManager != null ||
            ChaosCraft.config.trainingRoomNamespace == null ||
            ChaosCraft.config.trainingRoomUsernameNamespace == null
        ){
            ChaosCraft.LOGGER.error("Not enough TrainingRoom Data set");
            return;

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
            ChaosCraft.getServer().fitnessManager = new ChaosCraftFitnessManager();
            ChaosCraft.getServer().fitnessManager.parseData(obj);
        } catch (ChaosNetException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void logOutPlayer(PlayerEntity player) {
        //Iterate through all orgs and kill them
        ChaosCraftServerPlayerInfo craftServerPlayerInfo = userMap.get(player.getUniqueID().toString());
        if(craftServerPlayerInfo != null && craftServerPlayerInfo.organisims != null) {
            for (String orgNamespace : craftServerPlayerInfo.organisims) {
                if (!organisms.containsKey(orgNamespace)) {
                    ChaosCraft.LOGGER.error("Cannot find " + player.getName().getString() + "'s org `" + orgNamespace + "` for logout destruction");
                } else {
                    if(organisms.get(orgNamespace).getEntity() != null) {
                        organisms.get(orgNamespace).getEntity().killWithNoReport();
                        ChaosCraft.LOGGER.info("Logout killing " + player.getName().getString() + "'s org `" + orgNamespace + "` for logout destruction");
                    }
                }
            }
        }
        userMap.remove(player.getUniqueID().toString());
    }

    public void removeEntityFromWorld(OrgEntity orgEntity) {
       if(!organisms.containsKey(orgEntity.getCCNamespace())){
           ChaosCraft.LOGGER.error("Server is trying to remove an org from its `organisims` but it is not there: " + orgEntity.getCCNamespace());
           return;
       }
        organisms.remove(orgEntity.getCCNamespace());
    }


    public HashMap<ServerOrgManager.State, ArrayList<ServerOrgManager>> getOrgsSortedByState(){
        HashMap<ServerOrgManager.State, ArrayList<ServerOrgManager>> coll = new HashMap<ServerOrgManager.State, ArrayList<ServerOrgManager>>();
        for (ServerOrgManager serverOrgManager : organisms.values()) {
            if(!coll.containsKey(serverOrgManager.getState())){
                coll.put(serverOrgManager.getState(), new ArrayList<ServerOrgManager>());
            }
            coll.get(serverOrgManager.getState()).add(serverOrgManager);
        }
        return coll;
    }

    public ServerOrgManager getOrgByNamespace(String orgNamespace) {
        if(!organisms.containsKey(orgNamespace)){
            return null;
        }
        return organisms.get(orgNamespace);
    }
    public void cleanUp(){
        Iterator<ServerOrgManager> iterator = organisms.values().iterator();
        while(iterator.hasNext()){

            ServerOrgManager serverOrgManager = iterator.next();
            if(serverOrgManager.getState().equals(ServerOrgManager.State.Dead)){
                if(!userMap.containsKey(serverOrgManager.serverPlayerEntity.getUniqueID().toString())){
                    ChaosCraft.LOGGER.error("No valid player found for: " + serverOrgManager.getCCNamespace() + " player: " + serverOrgManager.serverPlayerEntity.getUniqueID() + " - " + serverOrgManager.serverPlayerEntity.getDisplayName().getString());
                }else{
                    userMap.get(serverOrgManager.serverPlayerEntity.getUniqueID().toString()).organisims.remove(serverOrgManager.getCCNamespace());
                }
                iterator.remove();
            }
        }
    }
    public void repair(){
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
    public void startTrainingSession(String trainingRoomUsernameNamespace, String trainingRoomNamespace){

        ChaosCraft.config.trainingRoomUsernameNamespace = trainingRoomUsernameNamespace;
        ChaosCraft.config.trainingRoomNamespace = trainingRoomNamespace;
        ChaosCraft.config.save();

        try {
            PostUsernameTrainingroomsTrainingroomSessionsStartRequest startSessionRequest = new PostUsernameTrainingroomsTrainingroomSessionsStartRequest();
            startSessionRequest.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
            startSessionRequest.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);

            PostUsernameTrainingroomsTrainingroomSessionsStartResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsStart(startSessionRequest);
            ChaosCraft.config.sessionNamespace = result.getTraningRoomSessionStartResponse().getSession().getNamespace();
            ChaosCraft.config.save();

            for (ChaosCraftServerPlayerInfo serverPlayerInfo : userMap.values()) {
                sendServerInfoPacket(server.getPlayerList().getPlayerByUUID(serverPlayerInfo.playerUUID));
            }
        }catch(ChaosNetException exception){
            if(exception.sdkHttpMetadata().httpStatusCode() == 401){
                ChaosCraft.LOGGER.error(exception.getMessage());
                String message = "Your login has expired. Please re-run `/chaoscraft-auth {username} {password}`";
                //ChaosCraft.chat(message);
                ChaosCraft.LOGGER.error(message);
            }else{
                throw exception;
            }

        }

    }
}
