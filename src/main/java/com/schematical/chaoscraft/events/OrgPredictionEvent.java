package com.schematical.chaoscraft.events;

/**
 * Created by user1a on 3/28/19.
 */
public class OrgPredictionEvent extends OrgEvent {
    public float weight = 0;

    public OrgPredictionEvent(int _ttl) {
        super( _ttl);
    }
    public OrgPredictionEvent(float weight, int _ttl) {
        super( _ttl);
        this.weight = weight;
    }
}
