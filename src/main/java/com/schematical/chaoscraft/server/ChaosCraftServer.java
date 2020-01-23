package com.schematical.chaoscraft.server;

import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.blocks.SpawnBlock;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.fitness.ChaosCraftFitnessManager;
import com.schematical.chaoscraft.fitness.EntityFitnessManager;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientOutputNeuronActionPacket;
import com.schematical.chaoscraft.network.packets.CCServerEntitySpawnedPacket;
import com.schematical.chaoscraft.network.packets.ClientAuthPacket;
import com.schematical.chaoscraft.network.packets.ServerIntroInfoPacket;
import com.schematical.chaosnet.ChaosNet;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ChaosCraftServer {
    public HashMap<String, ChaosCraftServerPlayerInfo> userMap = new HashMap<String, ChaosCraftServerPlayerInfo>();
    public ArrayList<String> orgNamepacesQueuedToSpawn = new  ArrayList<String>();
    public List<Organism> orgsToSpawn = new ArrayList<Organism>();
    public int consecutiveErrorCount;
    public Thread thread;
    public MinecraftServer server;
    public static int spawnHash;
    public static HashMap<String, OrgEntity> organisims = new HashMap<String, OrgEntity>();
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
            return;
        }
        if(
            orgNamepacesQueuedToSpawn.size() > 0 &&
            thread == null
        ){
            thread = new Thread(new ChaosServerThread(), "ChaosServerThread");
            thread.start();
        }
        if(orgsToSpawn.size() > 0){
            for (Organism organism : orgsToSpawn) {
                spawnOrg(organism);
            }
            orgsToSpawn.clear();
        }
        longTickCount += 1;
        if(longTickCount > 5 * 20){
            longTick();
        }
    }

    private void longTick() {
        for (ChaosCraftServerPlayerInfo value : userMap.values()) {
            if(value.organisims.size() < ChaosCraft.config.maxBotCount){
                //Request more bots

            }
        }
    }

    public void queueOrgToSpawn(String orgNamespace, ServerPlayerEntity player){
        if(!userMap.containsKey(player.getUniqueID().toString())){
            ChaosCraft.LOGGER.error("Could not find ServerPlayerEntity in `userMap` with UUID: " + player.getUniqueID().toString());
            return;
        }
        ChaosCraftServerPlayerInfo serverPlayerInfo = userMap.get(player.getUniqueID().toString());
        serverPlayerInfo.organisims.add(orgNamespace);
        orgNamepacesQueuedToSpawn.add(orgNamespace);
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

        //Send that user the training Room info from here
        ServerIntroInfoPacket serverIntroInfoPacket = new ServerIntroInfoPacket(
            ChaosCraft.config.trainingRoomNamespace,
            ChaosCraft.config.trainingRoomUsernameNamespace,
            ChaosCraft.config.sessionNamespace
        );
        ChaosCraft.LOGGER.info("Sending `serverIntroInfoPacket`");
        ChaosNetworkManager.sendTo(serverIntroInfoPacket,  player);
        ChaosCraft.LOGGER.info("SENT `serverIntroInfoPacket`: " + serverIntroInfoPacket.getTrainingRoomNamespace() + ", " + serverIntroInfoPacket.getTrainingRoomUsernameNamespace() + ", " + serverIntroInfoPacket.getSessionNamespace());
    }

    public OrgEntity spawnOrg(Organism organism) {

        if(organisims.containsKey(organism.getNamespace())){
            OrgEntity orgEntity = organisims.get(organism.getNamespace());
            ServerPlayerEntity serverPlayerEntity = orgEntity.getServerPlayerEntity();
            double dist = serverPlayerEntity.getPositionVec().distanceTo(orgEntity.getPositionVec());
            ChaosCraft.LOGGER.error("Server already have a living org: " + organism.getNamespace() + " - EntityId: " + orgEntity.getEntityId() + " orgsToSpawn: " + orgsToSpawn.size() + "organisms: " + organisims.size() + " dist: " + dist);
            sendChaosCraftServerPlayerInfo(organism, orgEntity);
            return null;
        }


        ServerWorld serverWorld = this.server.getWorld(DimensionType.OVERWORLD);
        PlayerEntity playerEntity = serverWorld.getPlayers().get(0);

        OrgEntity orgEntity = OrgEntity.ORGANISM_TYPE.create(serverWorld);
        BlockPos pos = null;
        int saftyCatch = 0;
        while(pos == null){
            saftyCatch ++;
            if (saftyCatch > 20) {
                ChaosCraft.LOGGER.error("Safty Catch Hit: " + saftyCatch);
                return orgEntity;
            }
            BlockPos pos1 = serverWorld.getSpawnPoint();
            int range = 5;
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

        serverWorld.summonEntity(orgEntity);
        orgEntity.attachOrganism(organism);
        orgEntity.attachNNetRaw(organism.getNNetRaw());
        orgEntity.entityFitnessManager = new EntityFitnessManager(orgEntity);
        orgEntity.observableAttributeManager = new CCObservableAttributeManager(organism);
        orgEntity.setCustomName(new TranslationTextComponent(orgEntity.getCCNamespace()));
        orgEntity.setSpawnHash(spawnHash);
        //ServerPlayerEntity serverPlayerEntity = orgEntity.getServerPlayerEntity();
        //orgEntity.addTrackingPlayer(serverPlayerEntity);

        organisims.put(organism.getNamespace(), orgEntity);
        sendChaosCraftServerPlayerInfo(organism, orgEntity);

        return orgEntity;
    }
    protected  void sendChaosCraftServerPlayerInfo(Organism organism, OrgEntity orgEntity){
        ServerPlayerEntity serverPlayerEntity = orgEntity.getServerPlayerEntity();
        ChaosNetworkManager.sendTo(new CCServerEntitySpawnedPacket(organism.getNamespace(), orgEntity.getEntityId()), serverPlayerEntity);
    }
    public void processClientOutputNeuronActionPacket(CCClientOutputNeuronActionPacket message){
        if(!organisims.containsKey(message.getOrgNamespace())){
            ChaosCraft.LOGGER.error("Server Cannot find org: " + message.getOrgNamespace());
            return;
        }
        OrgEntity orgEntity = organisims.get(message.getOrgNamespace());
        orgEntity.queueOutputNeuronAction(message);

    }
    public static void loadFitnessFunctions(){
        if(
            ChaosCraft.config.trainingRoomNamespace == null ||
            ChaosCraft.config.trainingRoomUsernameNamespace == null
        ){
            ChaosCraft.LOGGER.error("Not enough TrainingRoom Data set");
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
                if (!organisims.containsKey(orgNamespace)) {
                    ChaosCraft.LOGGER.error("Cannot find " + player.getName().getString() + "'s org `" + orgNamespace + "` for logout destruction");
                } else {

                    organisims.get(orgNamespace).killWithNoReport();
                    ChaosCraft.LOGGER.info("Logout killing " + player.getName().getString() + "'s org `" + orgNamespace + "` for logout destruction");
                }
            }
        }
        userMap.remove(player.getUniqueID().toString());
    }

    public void removeEntityFromWorld(OrgEntity orgEntity) {
       if(!organisims.containsKey(orgEntity.getCCNamespace())){
           ChaosCraft.LOGGER.error("Server is trying to remove an org from its `organisims` but it is not there: " + orgEntity.getCCNamespace());
           return;
       }
        organisims.remove(orgEntity.getCCNamespace());
    }
}
