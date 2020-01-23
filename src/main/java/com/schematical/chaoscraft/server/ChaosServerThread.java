package com.schematical.chaoscraft.server;

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

    public void run(){
        try{

            GetUsernameTrainingroomsTrainingroomOrganismsRequest request = new GetUsernameTrainingroomsTrainingroomOrganismsRequest();
            request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
            request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);

            String orgNamespaces = String.join(",", ChaosCraft.getServer().orgNamepacesQueuedToSpawn);
            request.setOrgNamespaces(orgNamespaces);


            GetUsernameTrainingroomsTrainingroomOrganismsResult response = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomOrganisms(request);
            List<Organism> organisms = response.getOrganismCollection();
            ChaosCraft.getServer().orgNamepacesQueuedToSpawn.clear();
            for (Organism organism : organisms) {
                ServerOrgManager serverOrgManager = new ServerOrgManager();
                serverOrgManager.attachOrganism(organism);
                ChaosCraft.getServer().queueForSpawn(serverOrgManager);
            }


            ChaosCraft.getServer().thread = null;


        }catch(ChaosNetException exception){
            //logger.error(exeception.getMessage());
            ChaosCraft.getServer().consecutiveErrorCount += 1;

            int statusCode = exception.sdkHttpMetadata().httpStatusCode();
            switch(statusCode){
                case(400):

                    //ChaosCraft.getClient().repair();
                    break;
                case(401):
                    ChaosCraft.auth();
                    break;
                case(409):
                    //ChaosCraft.auth();
                    break;
            }
            ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
            String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();//new String(byteBuffer.as().array(), StandardCharsets.UTF_8 );
            ChaosCraft.LOGGER.error("ChaosClientThread  Error: " + message + " - statusCode: " + statusCode);
            ChaosCraft.getServer().thread = null;

        }
    }

}
