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
        request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
        request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
        request.setSession(ChaosCraft.config.sessionNamespace);

        TrainingRoomSessionNextRequest trainingRoomSessionNextRequest = new TrainingRoomSessionNextRequest();
        Collection<ObservedAttributesElement> newAttributes = new ArrayList<ObservedAttributesElement>();

        Collection<TrainingRoomSessionNextReport> report = new ArrayList<TrainingRoomSessionNextReport>();
        if( ChaosCraft.getClient().orgsToReport != null) {
            for (OrgEntity organism :  ChaosCraft.getClient().orgsToReport) {
                String namespace = organism.getCCNamespace();
                if(namespace != null) {
                    TrainingRoomSessionNextReport reportEntry = new TrainingRoomSessionNextReport();
                    reportEntry.setScore(organism.entityFitnessManager.totalScore());
                    reportEntry.setNamespace(organism.getCCNamespace());
                    report.add(reportEntry);
                    /*if(organism.hasAttemptedReport){
                        ChaosCraft.LOGGER.info(organism.getCCNamespace() + " has already attempted a report");
                    }
                    organism.hasAttemptedReport = true;*/
                    newAttributes.addAll(organism.observableAttributeManager.newAttributes);
                }

            }

        }

        trainingRoomSessionNextRequest.setReport(report);
        trainingRoomSessionNextRequest.setNNetRaw(true);
        trainingRoomSessionNextRequest.setObservedAttributes(newAttributes);


        try {
            request.setTrainingRoomSessionNextRequest(trainingRoomSessionNextRequest);
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsSessionNext(request);


            ChaosCraft.getClient().lastResponse = result.getTrainingRoomSessionNextResponse();
            ChaosCraft.getClient().orgsToSpawn = ChaosCraft.getClient().lastResponse.getOrganisms();


            for (OrgEntity organism :  ChaosCraft.getClient().orgsToReport) {
                //organism.hasFinishedReport = true;
            }
            ChaosCraft.getClient().orgsToReport.clear();
            ChaosCraft.getClient().consecutiveErrorCount = 0;
            ChaosCraft.getClient().thread = null;
        }catch(ChaosNetException exception){
            //logger.error(exeception.getMessage());
            ChaosCraft.getClient().consecutiveErrorCount += 1;

            int statusCode = exception.sdkHttpMetadata().httpStatusCode();
            switch(statusCode){
                case(400):
                    ChaosCraft.getClient().orgsToReport.clear();
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
