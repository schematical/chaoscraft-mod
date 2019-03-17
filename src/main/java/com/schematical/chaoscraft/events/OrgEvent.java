package com.schematical.chaoscraft.events;

import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * Created by user1a on 3/7/19.
 */
public class OrgEvent {
    public static final int DEFAULT_TTL = 20;
    protected int ttl;//The amount of ticks an orgEvent has to live in its memory
    protected EntityFitnessScoreEvent scoreEvent;
    protected CCWorldEvent worldEvent;

    public OrgEvent(CCWorldEvent worldEvent, int _ttl) {
        this.worldEvent = worldEvent;
        ttl = _ttl;
    }
    public OrgEvent(EntityFitnessScoreEvent scoreEvent, int _ttl) {
        this.scoreEvent = scoreEvent;
        this.worldEvent = scoreEvent.worldEvent;
        ttl = _ttl;
    }

    public OrgEvent(EntityFitnessScoreEvent scoreEvent) {
        new OrgEvent(scoreEvent, DEFAULT_TTL);
    }

    public int tick() {
        ttl -= 1;
        return ttl;
    }
}
