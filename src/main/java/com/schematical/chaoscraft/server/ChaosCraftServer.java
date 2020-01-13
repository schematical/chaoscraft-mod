package com.schematical.chaoscraft.server;

import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.SpawnBlock;
import com.schematical.chaoscraft.entities.OrgEntity;
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
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ChaosCraftServer {
    public HashMap<String, AuthWhoamiResponse> userMap = new HashMap<String, AuthWhoamiResponse>();
    public ArrayList<String> orgNamepacesQueuedToSpawn = new  ArrayList<String>();
    public List<Organism> orgsToSpawn = new ArrayList<Organism>();
    public int consecutiveErrorCount;
    public Thread thread;
    public MinecraftServer server;
    public static HashMap<String, OrgEntity> organisims = new HashMap<String, OrgEntity>();

    public ChaosCraftServer(MinecraftServer server) {
        this.server = server;
    }

    public void tick(){
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
    }
    public void queueOrgToSpawn(String orgNamespace){
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
       /* player.setCustomName(new TextComponent() {
            @Override
            public String getUnformattedComponentText() {
                return  authWhoamiResponse.getUsername();
            }

            @Override
            public ITextComponent shallowCopy() {
                return this;
            }
        });*/
        userMap.put( player.getUniqueID().toString(),    getAuthWhoamiResult.getAuthWhoamiResponse());

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
            int range = 10;
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
                    pos = blockPos2;
                }

            }
            //pos = serverWorld.getDimension().findSpawn(pos1.getX(), pos1.getZ(), false);

        }

        ChaosCraft.LOGGER.info("Spawning: " + pos.getX() +", " + pos.getY()+", " +  pos.getZ());
        orgEntity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), playerEntity.rotationYaw, playerEntity.rotationPitch);

        serverWorld.summonEntity(orgEntity);
        orgEntity.attachOrganism(organism);
        orgEntity.attachNNetRaw(organism.getNNetRaw());
        organisims.put(organism.getNamespace(), orgEntity);
        ChaosNetworkManager.sendToServer(new CCServerEntitySpawnedPacket(organism.getNamespace(), orgEntity.getEntityId()));

        return orgEntity;
    }
    public void processClientOutputNeuronActionPacket(CCClientOutputNeuronActionPacket message){
        if(!organisims.containsKey(message.getOrgNamespace())){
            ChaosCraft.LOGGER.error("Cannot find : " + message.getOrgNamespace());
            return;
        }
        OrgEntity orgEntity = organisims.get(message.getOrgNamespace());
        orgEntity.queueOutputNeuronAction(message);

    }
}
