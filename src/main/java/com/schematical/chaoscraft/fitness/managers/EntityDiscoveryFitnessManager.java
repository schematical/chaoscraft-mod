package com.schematical.chaoscraft.fitness.managers;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;

import java.util.HashMap;

public class EntityDiscoveryFitnessManager extends FitnessManagerBase {
    private int score = 0;
    public HashMap<String, HashMap<String, EventHolder>> events = new HashMap<String, HashMap<String, EventHolder>>();

    public EntityDiscoveryFitnessManager(OrgEntity orgEntity) {
        super(orgEntity);
    }

    @Override
    public void test(CCWorldEvent event) {
        if(!events.containsKey(event.eventType.toString())){
            events.put(event.eventType.toString(), new HashMap<String, EventHolder>());
        }
        if(!events.get(event.eventType.toString()).containsKey(event.toSimpleId())){
            events.get(event.eventType.toString()).put(event.toSimpleId(), new EventHolder());
        }
        EventHolder eventHolder = events.get(event.eventType.toString()).get(event.toSimpleId());
        eventHolder.count += 1;
        int score = (int)Math.round(3/eventHolder.count);
        EntityFitnessScoreEvent scoreEvent  = new EntityFitnessScoreEvent(event, score, null);
        scoreEvent.life = score * 5;
        addScoreEvent(scoreEvent);
    }
    public class EventHolder{
        public int count = 0;
/*        public String attributeId;
        public String attributeType;*/
    }
}
