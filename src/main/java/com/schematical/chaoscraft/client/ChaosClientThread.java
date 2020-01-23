package com.schematical.chaoscraft.client;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by user1a on 1/30/19.
 */
public class ChaosClientThread implements Runnable {

    public void run(){



        PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest request = new PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest();
        request.setTrainingroom(ChaosCraft.getClient().getTrainingRoomNamespace());
        request.setUsername(ChaosCraft.getClient().getTrainingRoomUsernameNamespace());
        request.setSession(ChaosCraft.getClient().getSessionNamespace());

        TrainingRoomSessionNextRequest trainingRoomSessionNextRequest = new TrainingRoomSessionNextRequest();
        Collection<ObservedAttributesElement> newAttributes = new ArrayList<ObservedAttributesElement>();

        Collection<TrainingRoomSessionNextReport> report = new ArrayList<TrainingRoomSessionNextReport>();

        for (ClientOrgManager clientOrgManager :  ChaosCraft.getClient().getOrgsWithState(ClientOrgManager.State.ReadyToReport)) {
            String orgNamespace = clientOrgManager.getCCNamespace();

            TrainingRoomSessionNextReport reportEntry = new TrainingRoomSessionNextReport();
            if(orgNamespace == null) {
                ChaosCraft.LOGGER.error("!!!Invalid CCNamespace == null ");
            }else {
                if(ChaosCraft.getClient()._debugReportedOrgNamespaces.contains(clientOrgManager.getCCNamespace())){
                    ChaosCraft.LOGGER.error("Client has already reported org: " + clientOrgManager.getCCNamespace());
                }
                reportEntry.setScore(clientOrgManager.getServerScoreEventTotal());
                reportEntry.setNamespace(clientOrgManager.getCCNamespace());
                report.add(reportEntry);
                ChaosCraft.getClient()._debugReportedOrgNamespaces.add(clientOrgManager.getCCNamespace());
                clientOrgManager.markAttemptingReport();

            }
            newAttributes.addAll(clientOrgManager.getEntity().observableAttributeManager.newAttributes);
        }


        trainingRoomSessionNextRequest.setReport(report);
        trainingRoomSessionNextRequest.setNNetRaw(true);
        trainingRoomSessionNextRequest.setObservedAttributes(newAttributes);


        try {
            request.setTrainingRoomSessionNextRequest(trainingRoomSessionNextRequest);
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsSessionNext(request);


            ChaosCraft.getClient().lastResponse = result.getTrainingRoomSessionNextResponse();
            for (Organism organism : ChaosCraft.getClient().lastResponse.getOrganisms()) {
                ClientOrgManager clientOrgManager = new ClientOrgManager();
                clientOrgManager.attachOrganism(organism);

                ChaosCraft.getClient().myOrganisims.put(clientOrgManager.getCCNamespace(), clientOrgManager);
            }


            for (ClientOrgManager clientOrgManager :  ChaosCraft.getClient().getOrgsWithState(ClientOrgManager.State.AttemptingToReport)) {
                clientOrgManager.markReported();
            }

            ChaosCraft.getClient().consecutiveErrorCount = 0;
            ChaosCraft.getClient().thread = null;
        }catch(ChaosNetException exception){
            //logger.error(exeception.getMessage());
            ChaosCraft.getClient().consecutiveErrorCount += 1;

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
            ChaosCraft.LOGGER.error("ChaosClientThread `/next` Error: " + message + " - statusCode: " + statusCode);
            ChaosCraft.getClient().thread = null;

        }catch(Exception exception){
            ChaosCraft.getClient().consecutiveErrorCount += 1;

            ChaosCraft.LOGGER.error("ChaosClientThread `/next` Error: " + exception.getMessage() + " - exception type: " + exception.getClass().getName());
            ChaosCraft.getClient().thread = null;

        }


    }

}
