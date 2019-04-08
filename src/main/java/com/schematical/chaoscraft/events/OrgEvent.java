package com.schematical.chaoscraft.events;

import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;

/**
 * Created by user1a on 3/7/19.
 */
public class OrgEvent {

  public static final int DEFAULT_TTL = 20;
  protected int startTtl;
  protected int ttl;//The amount of ticks an orgEvent has to live in its memory
  protected EntityFitnessScoreEvent scoreEvent;
  protected CCWorldEvent worldEvent;

  public OrgEvent(int _ttl) {
    startTtl = _ttl;
    ttl = _ttl;
  }

  public OrgEvent(CCWorldEvent worldEvent, int _ttl) {
    this.worldEvent = worldEvent;
    startTtl = _ttl;
    ttl = _ttl;
  }

  public OrgEvent(EntityFitnessScoreEvent scoreEvent, int _ttl) {
    this.scoreEvent = scoreEvent;
    this.worldEvent = scoreEvent.worldEvent;
    startTtl = _ttl;
    ttl = _ttl;
  }

  public OrgEvent(EntityFitnessScoreEvent scoreEvent) {
    this.scoreEvent = scoreEvent;
    this.worldEvent = scoreEvent.worldEvent;
    startTtl = DEFAULT_TTL;
    ttl = DEFAULT_TTL;
  }

  public int tick() {
    ttl -= 1;
    return ttl;
  }

  public int getTtl() {
    return ttl;
  }

  public float getTTLWeight() {
    return (float) ttl / (float) startTtl;
  }

  public CCWorldEvent getWorldEvent() {
    return this.worldEvent;
  }

  public EntityFitnessScoreEvent getScoreEvent() {
    return this.scoreEvent;
  }
}
