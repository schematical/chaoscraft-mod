package com.schematical.chaoscraft.entities;

import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.fitness.EntityFitnessRule;
import com.schematical.chaosnet.model.ChaosNetException;

/**
 * Created by user1a on 1/4/19.
 */
public class EntityFitnessScoreEvent {
    public CCWorldEvent worldEvent;
    public int score;
    public int life;
    public EntityFitnessRule fitnessRule;
    public float multiplier = 1;


    public EntityFitnessScoreEvent(CCWorldEvent event, int _score, EntityFitnessRule _fitnessRule) {
        worldEvent = event;
        score = _score;
        fitnessRule = _fitnessRule;
        if(fitnessRule == null){
            throw new ChaosNetException("Missing `fitnessRule`");
        }
    }
    public float getAdjustedScore(){
        return score * multiplier;
    }
    public String toString(){
        return worldEvent.toString() + " = " + getAdjustedScore() + "(" + score + "*" + multiplier + ")";
    }
}
