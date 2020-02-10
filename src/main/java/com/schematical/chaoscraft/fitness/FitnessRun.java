package com.schematical.chaoscraft.fitness;

import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;

import java.util.ArrayList;
import java.util.List;

public class FitnessRun {
    public List<EntityFitnessScoreEvent> scoreEvents = new ArrayList<EntityFitnessScoreEvent>();
    public Double totalScore() {
        Double total = 0d;
        for (EntityFitnessScoreEvent scoreEvent: scoreEvents) {
            total += scoreEvent.getAdjustedScore();
        }
        return total;
    }
}
