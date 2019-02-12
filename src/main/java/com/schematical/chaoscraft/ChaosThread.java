package com.schematical.chaoscraft;

import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by user1a on 1/30/19.
 */
public class ChaosThread implements Runnable {

    public void run(){

   /* }
    public void getNextOrgs(List<EntityOrganism> organismList){*/
        String namespaces = "";

        PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest request = new PostUsernameTrainingroomsTrainingroomSessionsSessionNextRequest();
        request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
        request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
        request.setSession(ChaosCraft.config.sessionNamespace);

        TrainingRoomSessionNextRequest trainingRoomSessionNextRequest = new TrainingRoomSessionNextRequest();

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
                }
                namespaces += namespace + "    ";
            }

        }
        ChaosCraft.logger.info("GETTING getNextOrgs: " + ((ChaosCraft.orgsToReport != null) ? ChaosCraft.orgsToReport.size() : "") + " - " + namespaces + " - Ticks: " + ChaosCraft.ticksSinceLastSpawn);

        trainingRoomSessionNextRequest.setReport(report);
        trainingRoomSessionNextRequest.setNNetRaw(true);
        ChaosCraft.lastResponse = null;

        try {
            request.setTrainingRoomSessionNextRequest(trainingRoomSessionNextRequest);
            PostUsernameTrainingroomsTrainingroomSessionsSessionNextResult result = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsSessionNext(request);


            ChaosCraft.lastResponse = result.getTrainingRoomSessionNextResponse();
            ChaosCraft.orgsToSpawn = ChaosCraft.lastResponse.getOrganisms();
            String namespacesToSpawn = "";
            for(Organism org : ChaosCraft.orgsToSpawn){
                namespacesToSpawn += org.getNamespace() + ", ";
            }
            ChaosCraft.logger.info("SUCCESS getNextOrgs: " + ChaosCraft.orgsToSpawn.size() + " - " + namespacesToSpawn);

            for (EntityOrganism organism : ChaosCraft.orgsToReport) {
                organism.hasFinishedReport = true;
            }
            ChaosCraft.orgsToReport.clear();
            ChaosCraft.consecutiveErrorCount = 0;
            ChaosCraft.thread = null;
        }catch(Exception exeception){
            //logger.error(exeception.getMessage());
            ChaosCraft.consecutiveErrorCount += 1;
            ChaosCraft.chat("ChaosThread `/next` Error: " + exeception.getMessage());
            ChaosCraft.thread = null;
            



        }


    }

}
