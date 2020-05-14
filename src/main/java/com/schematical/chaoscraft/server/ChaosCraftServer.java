package com.schematical.chaoscraft.server;

import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.TrainingRoomRoleHolder;
import com.schematical.chaoscraft.entities.AlteredBlockInfo;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.*;
import com.schematical.chaoscraft.server.spawnproviders.SpawnBlockPosProvider;
import com.schematical.chaoscraft.server.spawnproviders.iServerSpawnProvider;
import com.schematical.chaosnet.ChaosNet;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.*;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityEvent;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ChaosCraftServer {
    public HashMap<String, ChaosCraftServerPlayerInfo> userMap = new HashMap<String, ChaosCraftServerPlayerInfo>();
    private State state = State.Unitilized;
    public int consecutiveErrorCount;
    public Thread thread;
    public MinecraftServer server;
    public static int spawnHash;
    public static HashMap<String, ServerOrgManager> organisms = new HashMap<String, ServerOrgManager>();
    public HashMap<String, TrainingRoomRoleHolder> trainingRoomRoles = new HashMap<>();
    public int longTickCount = 0;
    public int ticksSinceLastThread = -1;
    public iServerSpawnProvider spawnProvider = new SpawnBlockPosProvider();//PlayerSpawnPosProvider();
    static HashMap<ChunkPos, Integer> chunkLoadCount = new HashMap<>();
    public HashMap<BlockPos, AlteredBlockInfo> alteredBlocks = new HashMap<BlockPos, AlteredBlockInfo>();
    public ChaosCraftServer(MinecraftServer server) {

        this.server = server;
        spawnHash = (int)Math.round(Math.random() * 9999);
    }

    public void tick(){
        if(
           state.equals(State.Unitilized)
        ){
            loadTrainingRoomPackage();
            return;
        }





        checkForDeadOrgs();
        List<ServerOrgManager> orgNamepacesQueuedToSpawn = getOrgsWithState(ServerOrgManager.State.PlayerAttached);
        ticksSinceLastThread += 1;
        if(
            orgNamepacesQueuedToSpawn.size() > 0 &&
            (
                    thread == null||
                    ticksSinceLastThread > (120 * 20)//TWO MINutes?
            )
        ){
            if(thread != null){
                thread.interrupt();
            }
            thread = new Thread(new ChaosServerThread(), "ChaosServerThread");
            thread.start();
            ticksSinceLastThread = 0;
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
        updateObservers();
        for (ChaosCraftServerPlayerInfo value : userMap.values()) {
            if(value.organisims.size() < ChaosCraft.config.maxBotCount){
                //Request more bots

                /*//TODO: Clean up the Dead orgs out of the list
                List<ServerOrgManager> deadServerOrgManagers = getOrgsWithState(ServerOrgManager.State.Dead);
                for (ServerOrgManager deadServerOrgManager : deadServerOrgManagers) {
                    ChaosCraft.getServer().removeEntityFromWorld(deadServerOrgManager);
                }
*/
            }
        }
        for (ServerOrgManager serverOrgManager : organisms.values()) {
            serverOrgManager.longServerTick();
        }
        cleanUp();
        longTickCount = 0;
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
        authWhoamiRequest.accessToken(accessToken);
        getAuthWhoamiRequest.setAuthWhoamiRequest(authWhoamiRequest);
        GetAuthWhoamiResult getAuthWhoamiResult = sdk.getAuthWhoami(getAuthWhoamiRequest);
        AuthWhoamiResponse authWhoamiResponse = getAuthWhoamiResult.getAuthWhoamiResponse();



        ChaosCraft.LOGGER.info("CheckingAuth Response: " + authWhoamiResponse.getUsername());
        player.setCustomName(new TranslationTextComponent(authWhoamiResponse.getUsername()));
        ChaosCraftServerPlayerInfo playerInfo = new ChaosCraftServerPlayerInfo();
        playerInfo.authWhoamiResponse = getAuthWhoamiResult.getAuthWhoamiResponse();
        playerInfo.playerUUID = player.getUniqueID();
        player.setCustomNameVisible(true);//new TranslationTextComponent(authWhoamiResponse.getUsername()));
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
                ChaosCraft.config.env
        );

        ChaosNetworkManager.sendTo(serverIntroInfoPacket, player);
        ChaosCraft.LOGGER.info("SENT `serverIntroInfoPacket`: " + serverIntroInfoPacket.getTrainingRoomNamespace() + ", " + serverIntroInfoPacket.getTrainingRoomUsernameNamespace() + ", " + serverIntroInfoPacket.getEnv());

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
        BlockPos pos = spawnProvider.getSpawnPos(serverOrgManager);
        if(pos == null) {
            return null;//Now we play the waiting game
        }

        ServerWorld serverWorld = server.getWorld(DimensionType.OVERWORLD);


        OrgEntity orgEntity = OrgEntity.ORGANISM_TYPE.create(serverWorld);
        orgEntity.setSpawnHash(ChaosCraft.getServer().spawnHash);

        float yaw = (float)( Math.random() * 360f);
        float pitch = (float) (Math.random() * 360f);
        orgEntity.setDesiredYaw(yaw);
        orgEntity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, pitch);


        serverOrgManager.attachOrgEntity(orgEntity);
        serverWorld.summonEntity(orgEntity);



        sendChaosCraftEntitySpawnInfo(serverOrgManager);
        serverOrgManager.triggerOnSpawned();
        return orgEntity;
    }
    protected  void sendChaosCraftEntitySpawnInfo(ServerOrgManager serverOrgManager){
        ServerPlayerEntity serverPlayerEntity = serverOrgManager.getServerPlayerEntity();
        ChaosNetworkManager.sendTo(
                new CCServerEntitySpawnedPacket(
                        serverOrgManager.getCCNamespace(),
                        serverOrgManager.getEntity().getEntityId(),
                        (int) (serverOrgManager.getEntity().world.getGameTime() + ((serverOrgManager.getMaxLife() - serverOrgManager.getAgeSeconds()) * 20))
                ),
                serverPlayerEntity
        );
    }
   /* public void processClientOutputNeuronActionPacket(CCClientOutputNeuronActionPacket message){
        if(!organisms.containsKey(message.getOrgNamespace())){
            //ChaosCraft.LOGGER.error("Server Cannot find org to process neuron action packet: " + message.getOrgNamespace());
            return;
        }
        ServerOrgManager serverOrgManager = organisms.get(message.getOrgNamespace());
        serverOrgManager.queueOutputNeuronAction(message);

    }*/
    public List<ServerOrgManager> checkForDeadOrgs(){

        List<ServerOrgManager> serverOrgManagers = getOrgsWithState(ServerOrgManager.State.Ticking);
        for (ServerOrgManager serverOrgManager : serverOrgManagers) {

            if (!serverOrgManager.getEntity().isAlive()) {

                serverOrgManager.markDead();

            }
        }
        return serverOrgManagers;
    }
    public void loadTrainingRoomPackage(){


        if(
            ChaosCraft.config.trainingRoomNamespace == null ||
            ChaosCraft.config.trainingRoomUsernameNamespace == null
        ){
            ChaosCraft.LOGGER.error("Not enough TrainingRoom Data set");
            return;
        }
        //Load the roles... package this as a single request
        GetUsernameTrainingroomsTrainingroomPackageRequest request = new GetUsernameTrainingroomsTrainingroomPackageRequest();
        request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
        request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);


        try {
            GetUsernameTrainingroomsTrainingroomPackageResult result = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomPackage(request);
            TrainingRoomPackage trainingRoomPackage = result.getTrainingRoomPackage();
            for (TrainingRoomRole role : trainingRoomPackage.getRoles()) {




                TrainingRoomRoleHolder trainingRoomRoleHolder = new TrainingRoomRoleHolder(role);

                trainingRoomRoles.put(role.getNamespace(), trainingRoomRoleHolder);
                state = State.FitnessLoaded;
            }


        }catch(ChaosNetException exception) {
            //logger.error(exeception.getMessage());
            ChaosCraft.getServer().consecutiveErrorCount += 1;

            int statusCode = exception.sdkHttpMetadata().httpStatusCode();
            switch (statusCode) {
                case (400):

                    ChaosCraft.getServer().repair();
                    break;
                case (401):
                    ChaosCraft.auth();
                    break;
                case (409):
                    //ChaosCraft.auth();
                    break;
            }
            ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
            String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();//new String(byteBuffer.as().array(), StandardCharsets.UTF_8 );
            ChaosCraft.LOGGER.error("loadRoles  Error: " + message + " - statusCode: " + statusCode);
            exception.printStackTrace();
        }catch(Exception exception){
           // ChaosCraft.getServer().consecutiveErrorCount += 1;

            ChaosCraft.LOGGER.error("loadRoles Error: " + exception.getMessage() + " - exception type: " + exception.getClass().getName());
            // ChaosCraft.getClient().thread = null;//End should cover this


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

    public void removeEntityFromWorld(ServerOrgManager serverOrgManager) {
       if(!organisms.containsKey(serverOrgManager.getCCNamespace())){
           ChaosCraft.LOGGER.error("Server is trying to remove an org from its `organisims` but it is not there: " + serverOrgManager.getCCNamespace());
           return;
       }
        organisms.remove(serverOrgManager.getCCNamespace());
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
        Iterator<AlteredBlockInfo> iterator2 = alteredBlocks.values().iterator();
        while (iterator2.hasNext()) {
            AlteredBlockInfo alteredBlockInfo = iterator2.next();
            if(!alteredBlockInfo.serverOrgManager.getState().equals(ServerOrgManager.State.Ticking)) {
                boolean result = replaceAlteredBlock(alteredBlockInfo);
                if(result) {
                    iterator2.remove();
                }
            }
        }


    }
    public boolean replaceAlteredBlock(AlteredBlockInfo alteredBlockInfo) {
        return replaceAlteredBlock(alteredBlockInfo,  false);
    }
    public boolean replaceAlteredBlock(AlteredBlockInfo alteredBlockInfo, boolean force){
        World world = server.getWorld(DimensionType.OVERWORLD);
        if(!force) {
            List<OrgEntity> orgEntites = world.getEntitiesWithinAABB(
                    OrgEntity.class,
                    new AxisAlignedBB(
                            alteredBlockInfo.blockPos
                    )
            );
            if (orgEntites.size() > 0) {
                OrgEntity orgEntity = orgEntites.get(0);

                if (
                        orgEntity.isAlive() &&
                                !orgEntity.getCCNamespace().equals(alteredBlockInfo.serverOrgManager.getCCNamespace())
                ) {
                    alteredBlockInfo.serverOrgManager = orgEntity.getServerOrgManager();
                }
                return false;
            }
        }
        List<ItemEntity> items = world.getEntitiesWithinAABB(
                ItemEntity.class,
                new AxisAlignedBB(
                        alteredBlockInfo.blockPos
                )
        );
        for (ItemEntity itemEntity : items) {
            itemEntity.detach();
            itemEntity.remove();
        }
        return world.setBlockState(alteredBlockInfo.blockPos, alteredBlockInfo.state, world.isRemote ? 11 : 3);



    }
    public void repair(){
        //TODO: Remove this server cannot repair
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
    public void setTrainingRoom(String trainingRoomUsernameNamespace, String trainingRoomNamespace, String env){

        ChaosCraft.config.trainingRoomUsernameNamespace = trainingRoomUsernameNamespace;
        ChaosCraft.config.trainingRoomNamespace = trainingRoomNamespace;
        if(!env.equals(ChaosCraft.config.env)) {
            ChaosCraft.config.env = env;
            ChaosCraft.setupSDK(ChaosCraft.config.env);
        }
        ChaosCraft.config.save();

        for (ChaosCraftServerPlayerInfo serverPlayerInfo : userMap.values()) {
            sendServerInfoPacket(server.getPlayerList().getPlayerByUUID(serverPlayerInfo.playerUUID));
        }

    }

    public void updatePlayerObserverState(CCClientObserveStateChangePacket message, ServerPlayerEntity player) {
        if(!userMap.containsKey(player.getUniqueID().toString())){
            ChaosCraft.LOGGER.error("Could not find ServerPlayerEntity in `userMap` with UUID: " + player.getUniqueID().toString());
            return;
        }
        ChaosCraftServerPlayerInfo serverPlayerInfo = userMap.get(player.getUniqueID().toString());
        serverPlayerInfo.state = message.getState();
        serverPlayerInfo.getServerPlayerEntity().setGameType(GameType.SPECTATOR);
        if(serverPlayerInfo.state.equals(ChaosCraftServerPlayerInfo.State.ObservingActive)) {

            if (!organisms.containsKey(message.getOrgNamespace())) {
                serverPlayerInfo.state = ChaosCraftServerPlayerInfo.State.None;
                ChaosCraft.LOGGER.error("Could not set player observing state because find Organism:" + message.getOrgNamespace());
                return;
            }
            serverPlayerInfo.observingEntity = organisms.get(message.getOrgNamespace());

        }else if(serverPlayerInfo.state.equals(ChaosCraftServerPlayerInfo.State.None)) {
            serverPlayerInfo.getServerPlayerEntity().setGameType(GameType.CREATIVE);
                ServerPlayerEntity serverPlayerEntity  = server.getPlayerList().getPlayerByUUID(serverPlayerInfo.playerUUID);
                serverPlayerEntity.setSpectatingEntity(null);

        }
        updateObservers();

    }
    public void updateObservers(){
        //Find high scoring org
        ArrayList<ChaosCraftServerPlayerInfo> observingPlayers = new ArrayList<ChaosCraftServerPlayerInfo>();
        for (ChaosCraftServerPlayerInfo serverPlayerInfo : userMap.values()) {
            if(
                serverPlayerInfo.state.equals(ChaosCraftServerPlayerInfo.State.ObservingPassive)
            ) {
                observingPlayers.add(serverPlayerInfo);
            }else if( serverPlayerInfo.state.equals(ChaosCraftServerPlayerInfo.State.ObservingActive)) {
                if(
                    !serverPlayerInfo.observingEntity.getState().equals(ServerOrgManager.State.Ticking)
                ) {
                    serverPlayerInfo.state = ChaosCraftServerPlayerInfo.State.None;
                    serverPlayerInfo.observingEntity = null;
                }
            }

        }
        if(observingPlayers.size() == 0) {
            return;
        }

        //Find highest scoring bot
        ServerOrgManager highScoringServerOrgManager = null;
        Double highScore = -99999d;
        List<ServerOrgManager> orgs = getOrgsWithState(ServerOrgManager.State.Ticking);
        for (ServerOrgManager org : orgs) {
            Double orgTotalScore = org.getEntityFitnessManager().getCurrFitnessRun().totalScore();
            if(orgTotalScore > highScore){
                highScore = orgTotalScore;
                highScoringServerOrgManager = org;
            }
        }
        if(highScoringServerOrgManager == null){
            return;
        }
        //Set to observe it
        for (ChaosCraftServerPlayerInfo observingPlayer : observingPlayers) {
            observingPlayer.getServerPlayerEntity().setSpectatingEntity(highScoringServerOrgManager.getEntity());

            CCServerObserverOrgChangeEventPacket packet = new CCServerObserverOrgChangeEventPacket(
                    highScoringServerOrgManager.getCCNamespace(),
                    (int) Math.round(highScoringServerOrgManager.getEntityFitnessManager().getCurrFitnessRun().totalScore()),
                    (int) (highScoringServerOrgManager.getEntity().world.getGameTime() + ((highScoringServerOrgManager.getMaxLife() - highScoringServerOrgManager.getAgeSeconds()) * 20))
            );
            ChaosNetworkManager.sendTo(packet, observingPlayer.getServerPlayerEntity());
        }

    }

    public ServerOrgManager findOrgThatAlteredBlock(BlockPos blockPos) {
        if(!alteredBlocks.containsKey(blockPos)) {
            return null;
        }

        return alteredBlocks.get(blockPos).serverOrgManager;
    }

    public void forceLoadChunk(ChunkPos currChunkPos, ChunkPos newChunkPos){
        DimensionType dimensiontype = DimensionType.OVERWORLD;//server.getWorld().getDimension().getType();
        ServerWorld serverworld = server.getWorld(dimensiontype);
        LongSet longset = serverworld.getForcedChunks();
        longset.forEach((long p_212720_1_) -> {
            serverworld.forceChunk(ChunkPos.getX(p_212720_1_), ChunkPos.getZ(p_212720_1_), false);
        });
    }



    public void chunkEnterEvent(EntityEvent.EnteringChunk event) {
        if(
            event.getEntity() instanceof OrgEntity &&
            event.getEntity().world instanceof ServerWorld
        ) {
            ServerWorld world = (ServerWorld) event.getEntity().world;

            ChunkPos oldPos = new ChunkPos(event.getOldChunkX(), event.getOldChunkZ());
            ChunkPos newPos = new ChunkPos(event.getNewChunkX(), event.getNewChunkZ());

            //This should technically always be true, but you never know
            if(chunkLoadCount.containsKey(oldPos)) {
                //Get the old amount of bots in a chunk and distract one because this one just left
                int newCount = chunkLoadCount.get(oldPos) - 1;

                //If there are now less than 0 bots in it we don't need the chunks anymore so unload it
                if(newCount <= 0) {
                    chunkLoadCount.remove(oldPos); //I remove it here to make sure we don't get negative values or something strange

                    world.forceChunk(oldPos.x, oldPos.z, false);
                } else {
                    //If there are more than 0 zero bots in this chunk it still needs to be loaded
                    chunkLoadCount.put(oldPos, newCount);
                }
            }

            //New count is _at least_ 1
            int newCount = 1;

            if(chunkLoadCount.containsKey(newPos)) {
                //We have a count saved, so add that
                newCount += chunkLoadCount.get(newPos);
            }else {
                //world.forceChunk(newPos.x, newPos.z, true);
            }

            //Write it!
            chunkLoadCount.put(newPos, newCount);
        }
    }

    public void markBlockAltered(BlockPos pos, BlockState state, ServerOrgManager serverOrgManager) {
        if(serverOrgManager == null){
            ChaosCraft.LOGGER.error("serverOrgManager is null");
            return;
        }
        if(alteredBlocks.containsKey(pos)){
            alteredBlocks.get(pos).serverOrgManager = serverOrgManager;
        }
        alteredBlocks.put(
            pos,
            new AlteredBlockInfo(
                pos,
                state,
                serverOrgManager
            )
        );
    }

    public void replaceAlteredBlocks(ServerOrgManager serverOrgManager) {

            Iterator<AlteredBlockInfo> iterator = alteredBlocks.values().iterator();
            while (iterator.hasNext()) {
                AlteredBlockInfo alteredBlockInfo = iterator.next();
                if(alteredBlockInfo.serverOrgManager.getCCNamespace().equals(serverOrgManager.getCCNamespace())) {

                    boolean result = replaceAlteredBlock(alteredBlockInfo);
                    if(result) {
                        iterator.remove();
                    }
                }

            }

    }

    public void replaceAllAlteredBlocks() {
        Iterator<AlteredBlockInfo> iterator = alteredBlocks.values().iterator();
        while (iterator.hasNext()) {
            AlteredBlockInfo alteredBlockInfo = iterator.next();
            boolean result = replaceAlteredBlock(alteredBlockInfo, true);
            if(result) {
                iterator.remove();
            }


        }
    }

    public void handleClientOrgUpdatePacket(CCClientOrgUpdatePacket message) {
        if(!organisms.containsKey(message.getOrgNamespace())){
            return;
        }
        organisms.get(message.getOrgNamespace()).handleClientOrgUpdatePacket(message);
    }

    public HashMap<String, ServerOrgManager> getOrganisms() {
        return organisms;
    }

    public enum State{
        Unitilized,
        FitnessLoaded
    }
}
