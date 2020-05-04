package com.schematical.chaoscraft.client;

import com.amazonaws.opensdk.SdkErrorHttpMetadata;
import com.schematical.chaoscraft.ChaosCraft;
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

                reportEntry.setScore(clientOrgManager.getServerScoreEventTotal());
                reportEntry.setNamespace(clientOrgManager.getCCNamespace());
                report.add(reportEntry);

                clientOrgManager.markAttemptingReport();

            }
            newAttributes.addAll(clientOrgManager.getEntity().observableAttributeManager.newAttributes);
        }
        for (ClientOrgManager clientOrgManager :  ChaosCraft.getClient().getOrgsWithState(ClientOrgManager.State.Invalid)) {
            String orgNamespace = clientOrgManager.getCCNamespace();

            TrainingRoomSessionNextReport reportEntry = new TrainingRoomSessionNextReport();
            if(orgNamespace == null) {
                ChaosCraft.LOGGER.error("!!!Invalid CCNamespace == null ");
            }else {

                reportEntry.setScore(-10000d);
                reportEntry.setNamespace(clientOrgManager.getCCNamespace());
                report.add(reportEntry);

                clientOrgManager.markAttemptingReport();

            }
        }
        trainingRoomSessionNextRequest.setReport(report);
        trainingRoomSessionNextRequest.setNNetRaw(true);
        trainingRoomSessionNextRequest.setObservedAttributes(newAttributes);


        try {
            request.setTrainingRoomSessionNextRequest(trainingRoomSessionNextRequest);
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsSessionNext(request);


            ChaosCraft.getClient().lastResponse = result.getTrainingRoomSessionNextResponse();
            for (Organism organism : ChaosCraft.getClient().lastResponse.getOrganisms()) {
                if(
                    !ChaosCraft.getClient().myOrganisms.containsKey(organism.getNamespace())

                ) {
                    ClientOrgManager clientOrgManager = new ClientOrgManager();
                    clientOrgManager.attachOrganism(organism);

                    ChaosCraft.getClient().newOrganisms.put(clientOrgManager.getCCNamespace(), clientOrgManager);
                }else{
                    ClientOrgManager clientOrgManager = ChaosCraft.getClient().myOrganisms.get(organism.getNamespace());
                    if(clientOrgManager.getState().equals(ClientOrgManager.State.FinishedReport)){
                        clientOrgManager.markForRetryReport();
                    }
                }
            }


            for (ClientOrgManager clientOrgManager :  ChaosCraft.getClient().getOrgsWithState(ClientOrgManager.State.AttemptingToReport)) {
                clientOrgManager.markReported();
            }

            ChaosCraft.getClient().consecutiveErrorCount = 0;
            ChaosCraft.getClient().thread = null;
        }catch(ChaosNetException exception){
            //logger.error(exeception.getMessage());
            ChaosCraft.getClient().consecutiveErrorCount += 1;

            SdkErrorHttpMetadata sdkErrorHttpMetadata = exception.sdkHttpMetadata();
            Integer statusCode = null;
            if(sdkErrorHttpMetadata != null) {
                switch (statusCode) {
                    case (502):
                    case (504):
                    case (500):
                    case (400):

                        ChaosCraft.getClient().repair();
                        break;

                    case (401):
                        ChaosCraft.auth();
                        break;
                    case (409):
                        //ChaosCraft.auth();
                        break;
                }
            }
            ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
            String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();//new String(byteBuffer.as().array(), StandardCharsets.UTF_8 );
            ChaosCraft.LOGGER.error("ChaosClientThread `/next` Error: " + message + " - statusCode: " + statusCode);
            ChaosCraft.getClient().thread = null;
            ChaosCraft.getClient().setTicksRequiredToCallChaosNet(1000);

        }catch(Exception exception){
            ChaosCraft.getClient().consecutiveErrorCount += 1;

            ChaosCraft.LOGGER.error("ChaosClientThread `/next` Error: " + exception.getMessage() + " - exception type: " + exception.getClass().getName());
            ChaosCraft.getClient().thread = null;
            ChaosCraft.getClient().setTicksRequiredToCallChaosNet(1000);

        }



    }

}
