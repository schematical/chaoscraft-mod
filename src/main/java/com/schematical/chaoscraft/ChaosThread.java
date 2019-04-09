package com.schematical.chaoscraft;

import com.google.common.primitives.Bytes;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.*;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user1a on 1/30/19.
 */
public class ChaosThread implements Runnable {

    public void run(){


        String namespaces = "";

        PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest request = new PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest();
        request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
        request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
        request.setSession(ChaosCraft.config.sessionNamespace);

        TrainingRoomSessionNextRequest trainingRoomSessionNextRequest = new TrainingRoomSessionNextRequest();
        Collection<ObservedAttributesElement> newAttributes = new ArrayList<ObservedAttributesElement>();

        Collection<TrainingRoomSessionNextReport> report = new ArrayList<TrainingRoomSessionNextReport>();
        if(ChaosCraft.orgsToReport != null) {
            for (EntityOrganism organism : ChaosCraft.orgsToReport) {
                String namespace = organism.getCCNamespace();
                if(namespace != null) {
                    TrainingRoomSessionNextReport reportEntry = new TrainingRoomSessionNextReport();
                    reportEntry.setScore(organism.entityFitnessManager.totalScore());
                    reportEntry.setNamespace(organism.getCCNamespace());
                    report.add(reportEntry);
                    if(organism.hasAttemptedReport){
                        ChaosCraft.logger.info(organism.getCCNamespace() + " has already attempted a report");
                    }
                    organism.hasAttemptedReport = true;
                    newAttributes.addAll(organism.observableAttributeManager.newAttributes);
                }
                namespaces += namespace + "    ";
            }

        }

        trainingRoomSessionNextRequest.setReport(report);
        trainingRoomSessionNextRequest.setNNetRaw(true);
        trainingRoomSessionNextRequest.setObservedAttributes(newAttributes);


        try {
            request.setTrainingRoomSessionNextRequest(trainingRoomSessionNextRequest);
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsSessionNext(request);


            ChaosCraft.lastResponse = result.getTrainingRoomSessionNextResponse();
            ChaosCraft.orgsToSpawn = ChaosCraft.lastResponse.getOrganisms();


            for (EntityOrganism organism : ChaosCraft.orgsToReport) {
                organism.hasFinishedReport = true;
            }
            ChaosCraft.orgsToReport.clear();
            ChaosCraft.consecutiveErrorCount = 0;
            ChaosCraft.thread = null;
        }catch(ChaosNetException exception){
            //logger.error(exeception.getMessage());
            ChaosCraft.consecutiveErrorCount += 1;

            int statusCode = exception.sdkHttpMetadata().httpStatusCode();
            switch(statusCode){
                case(400):
                    ChaosCraft.orgsToReport.clear();
                    ChaosCraft.repair();
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
            ChaosCraft.chat("ChaosThread `/next` Error: " + message + " - statusCode: " + statusCode);
            ChaosCraft.thread = null;

        }catch(Exception exception){
            ChaosCraft.consecutiveErrorCount += 1;

            ChaosCraft.chat("ChaosThread `/next` Error: " + exception.getMessage() + " - exception type: " + exception.getClass().getName());
            ChaosCraft.thread = null;

        }


    }

}
