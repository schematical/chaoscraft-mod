package com.schematical.chaoscraft.server;

import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.ServerIntroInfoPacket;
import com.schematical.chaosnet.ChaosNet;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.*;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChaosCraftServer {
    public HashMap<String, AuthWhoamiResponse> userMap = new HashMap<String, AuthWhoamiResponse>();
    public ArrayList<String> orgNamepacesQueuedToSpawn = new  ArrayList<String>();
    public List<Organism> orgsToSpawn = new ArrayList<Organism>();
    public int consecutiveErrorCount;
    public Thread thread;

    public void tick(){
        if(orgNamepacesQueuedToSpawn.size() > 0){
            thread = new Thread(new ChaosServerThread(), "ChaosServerThread");
            thread.start();
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

   /* public OrgEntity spawnOrg(Organism organism){
        //Minecraft.getInstance().world.getPlayers()[0];

        OrgEntity orgEntity = OrgEntity.ORGANISM_TYPE.create(Minecraft.getInstance().world.getWorld());
       *//* Vec3d pos = context.getSource().getPos();
        rick.setLocationAndAngles(pos.x, pos.y, pos.z, context.getSource().getEntity().rotationYaw, context.getSource().getEntity().rotationPitch);
        Minecraft.getInstance().world.getWorld().summonEntity(rick);*//*
       return orgEntity;
    }*/
}
