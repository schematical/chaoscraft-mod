package com.schematical.chaoscraft.server;

import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.ServerIntroInfoPacket;
import com.schematical.chaosnet.ChaosNet;
import com.schematical.chaosnet.auth.ChaosnetCognitoUserPool;
import com.schematical.chaosnet.model.AuthWhoamiRequest;
import com.schematical.chaosnet.model.AuthWhoamiResponse;
import com.schematical.chaosnet.model.GetAuthWhoamiRequest;
import com.schematical.chaosnet.model.GetAuthWhoamiResult;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;

import java.util.HashMap;

public class ChaosCraftServer {
    public HashMap<String, AuthWhoamiResponse> userMap = new HashMap<String, AuthWhoamiResponse>();
    public void tick(){

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
}
