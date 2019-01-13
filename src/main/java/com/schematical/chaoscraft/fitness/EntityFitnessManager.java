package com.schematical.chaoscraft.fitness;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.events.CCWorldEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1a on 1/4/19.
 */
public class EntityFitnessManager {
    public EntityOrganism entityOrganism;
    public List<EntityFitnessScoreEvent> scoreEvents = new ArrayList<EntityFitnessScoreEvent>();

    public EntityFitnessManager(EntityOrganism entityOrganism) {
        this.entityOrganism = entityOrganism;
    }

    public void test(CCWorldEvent event){
        EntityFitnessScoreEvent scoreEvent = ChaosCraft.fitnessManager.testEntityFitnessEvent(this.entityOrganism, event);
        if(scoreEvent != null){
            scoreEvents.add(scoreEvent);
        }
    }

    public Double totalScore() {
        Double total = 0d;
        for (EntityFitnessScoreEvent scoreEvent: scoreEvents) {
            total += scoreEvent.score;
        }
        return total;
    }
}
