package com.schematical.chaoscraft.entities;

import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.fitness.EntityFitnessRule;

/**
 * Created by user1a on 1/4/19.
 */
public class EntityFitnessScoreEvent {
    public CCWorldEvent worldEvent;
    public int score;
    public int life;
    public EntityFitnessRule fitnessRule;


    public EntityFitnessScoreEvent(CCWorldEvent event, int _score, EntityFitnessRule _fitnessRule) {
        worldEvent = event;
        score = _score;
        fitnessRule = _fitnessRule;
    }
    public String toString(){
        return worldEvent.toString() + " - " + score;
    }
}
