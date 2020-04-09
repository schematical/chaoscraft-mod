package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.tickables.BaseChaosEventListener;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class ChaosHighScoreTracker extends BaseChaosEventListener {
    private static ClientOrgManager highScoreOrgManager = null;
    private static int highScore = -99999;
    private static final int minThreshold = 50;

    public void onClientReport(ClientOrgManager clientOrgManager) {
        Double score = clientOrgManager.getServerScoreEventTotal();
        if(score > highScore){
            highScoreOrgManager = clientOrgManager;
            highScore = (int) Math.round(score);
            if(highScore > minThreshold){
                try {
                    String filePath = ChaosCraft.config.getDirPath() + "logs/highscore.txt";// + highScore + "  " + clientOrgManager.getCCNamespace()  + ".txt";
                    File file = new File(filePath);
                    String dirPath = file.getParent();
                    File dir = new File(dirPath);
                    if(!dir.exists()){
                        dir.mkdirs();
                    }
                    FileWriter fileWriter = new FileWriter(filePath);

                    String message = clientOrgManager.getCCNamespace()  + " Score: " + highScore + "\n";
                    ArrayList<CCServerScoreEventPacket> scoreEvents = (ArrayList<CCServerScoreEventPacket>)clientOrgManager.getServerScoreEvents().clone();
                    Collections.reverse(scoreEvents);
                    for (CCServerScoreEventPacket serverScoreEventPacket : scoreEvents) {
                       message += serverScoreEventPacket.fitnessRuleId +
                                " S:" + serverScoreEventPacket.score +
                                " L:" + serverScoreEventPacket.life +
                                " M:" + serverScoreEventPacket.multiplier +
                                " T:" + serverScoreEventPacket.getAdjustedScore() +
                                "\n";
                    }
                    message += "\n\n-----------------------------\n\n";
                    fileWriter.write(message);

                    fileWriter.close();
                }catch (Exception e){
                    ChaosCraft.LOGGER.error("Error saving Config: " + e.getMessage());
                }
            }
        }
    }
}
