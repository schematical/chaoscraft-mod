package com.schematical.chaoscraft;

import com.schematical.chaoscraft.fitness.ChaosCraftFitnessManager;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.TrainingRoomRole;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TrainingRoomRoleHolder {
    public TrainingRoomRole trainingRoomRole;
    public ChaosCraftFitnessManager fitnessManager;

    public TrainingRoomRoleHolder(TrainingRoomRole role) {
        trainingRoomRole = role;
        fitnessManager = new ChaosCraftFitnessManager();
        JSONParser parser = new JSONParser();
        try{
            String fitnessRulesRaw = trainingRoomRole.getFitnessRulesRaw();
            JSONArray obj = (JSONArray) parser.parse(
                    fitnessRulesRaw
            );
            fitnessManager.parseData(obj);
        } catch (ParseException e) {
            throw new ChaosNetException(e.getMessage());
        }

    }
}
