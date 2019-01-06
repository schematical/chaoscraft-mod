package com.schematical.chaoscraft.entities;

import com.schematical.chaoscraft.events.CCWorldEvent;

/**
 * Created by user1a on 1/4/19.
 */
public class EntityFitnessScoreEvent {
    public CCWorldEvent worldEvent;
    public int score;


    public EntityFitnessScoreEvent(CCWorldEvent event, int _score) {
        worldEvent = event;
        score = _score;
    }
}
