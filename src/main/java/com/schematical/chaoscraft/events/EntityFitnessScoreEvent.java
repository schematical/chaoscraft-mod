package com.schematical.chaoscraft.events;

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
        /*if(fitnessRule == null){
            throw new ChaosNetException("Missing `fitnessRule`");
        }*/
    }
    public float getAdjustedScore(){
        return Math.round(score * multiplier);
    }
    public String toString(){
        String _multiplier = ((Float)multiplier).toString();
        if(_multiplier.length() > 3){
            _multiplier = _multiplier.substring(0, 3);
        }
        return worldEvent.toString() + " = " + getAdjustedScore() + "(" + score + "*" + _multiplier + ")";
    }


}
