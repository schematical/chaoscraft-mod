package com.schematical.chaoscraft.server;

import com.amazonaws.opensdk.SdkErrorHttpMetadata;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user1a on 1/30/19.
 */
public class ChaosServerThread implements Runnable {
    public void end(){
        ChaosCraft.getServer().thread = null;
    }
    @Override
    public void run(){
        try{
            if(
                ChaosCraft.config.trainingRoomNamespace == null ||
                ChaosCraft.config.trainingRoomUsernameNamespace == null
            ){
               //No training room set :(
                end();
                return;
            }
            GetUsernameTrainingroomsTrainingroomOrganismsRequest request = new GetUsernameTrainingroomsTrainingroomOrganismsRequest();
            request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
            request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);

            List<ServerOrgManager> serverOrgManagers = ChaosCraft.getServer().getOrgsWithState(ServerOrgManager.State.PlayerAttached);
            ArrayList<String> orgNamespaces = new ArrayList<String>();
            for (ServerOrgManager serverOrgManager : serverOrgManagers) {
                orgNamespaces.add(serverOrgManager.getTmpNamespace());
            }
            String orgNamespacesStr = String.join(",", orgNamespaces);
            request.setOrgNamespaces(orgNamespacesStr);


            GetUsernameTrainingroomsTrainingroomOrganismsResult response = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomOrganisms(request);
            List<Organism> organisms = response.getOrganismCollection();


            for (Organism organism : organisms) {
                if(!ChaosCraft.getServer().organisms.containsKey(organism.getNamespace())){
                    ChaosCraft.LOGGER.error("Server - Cant find `ChaosCraft.getServer().organisms` org: " + organism.getNamespace());
                }else {
                    ServerOrgManager serverOrgManager = ChaosCraft.getServer().organisms.get(organism.getNamespace());
                    serverOrgManager.attachOrganism(organism);
                    serverOrgManager.queueForSpawn();
                }

            }





        }catch(ChaosNetException exception){
            //logger.error(exeception.getMessage());
             ChaosCraft.getServer().consecutiveErrorCount += 1;
            SdkErrorHttpMetadata sdkErrorHttpMetadata = exception.sdkHttpMetadata();
            Integer statusCode = null;
            String message = exception.getMessage();
            if(sdkErrorHttpMetadata != null) {
                statusCode = sdkErrorHttpMetadata.httpStatusCode();
                switch (statusCode) {
                    case (400):

                        //ChaosCraft.getServer().repair();
                        break;
                    case (401):
                        ChaosCraft.auth();
                        break;
                    case (409):
                        //ChaosCraft.auth();
                        break;
                }
                ByteBuffer byteBuffer = sdkErrorHttpMetadata.responseContent();
                message = StandardCharsets.UTF_8.decode(byteBuffer).toString();//new String(byteBuffer.as().array(), StandardCharsets.UTF_8 );
            }

            ChaosCraft.LOGGER.error("ChaosServerThread  Error: " + message + " - statusCode: " + statusCode);


        }catch(Exception exception){
            ChaosCraft.getServer().consecutiveErrorCount += 1;

            ChaosCraft.LOGGER.error("XChaosServerThread Error: " + exception.getMessage() + " - exception type: " + exception.getClass().getName() + "\n\n" + exception.getStackTrace());
           // ChaosCraft.getClient().thread = null;//End should cover this


        }
        end();
    }

}
