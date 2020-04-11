package com.schematical.chaoscraft.fitness.managers;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.fitness.EntityFitnessRule;
import com.schematical.chaoscraft.server.ServerOrgManager;

import java.util.HashMap;

public class EntityDiscoveryFitnessManager extends FitnessManagerBase {

    public HashMap<String, HashMap<String, EventHolder>> events = new HashMap<String, HashMap<String, EventHolder>>();

    public EntityDiscoveryFitnessManager(ServerOrgManager serverOrgManager) {
        super(serverOrgManager);
    }

    @Override
    public void test(CCWorldEvent event) {
        if(
                !(
                        event.eventType.equals(CCWorldEvent.Type.BLOCK_MINED) ||
                        event.eventType.equals(CCWorldEvent.Type.BLOCK_PLACED) ||
                        event.eventType.equals(CCWorldEvent.Type.ITEM_COLLECTED) ||
                        event.eventType.equals(CCWorldEvent.Type.ITEM_CRAFTED)||
                        event.eventType.equals(CCWorldEvent.Type.ITEM_USED) /*||
                        event.eventType.equals(CCWorldEvent.Type.TOUCHED_BLOCK)*/
                )
        ){
            return;
        }
        if(!events.containsKey(event.eventType.toString())){
            events.put(event.eventType.toString(), new HashMap<String, EventHolder>());
        }
        if(!events.get(event.eventType.toString()).containsKey(event.toSimpleId())){
            events.get(event.eventType.toString()).put(event.toSimpleId(), new EventHolder());
        }
        EventHolder eventHolder = events.get(event.eventType.toString()).get(event.toSimpleId());
        eventHolder.count += 1;
        int baseScore = 1;

        int score = (int)Math.round(baseScore/eventHolder.count);
        if(eventHolder.count == 1) {
            EntityFitnessRule entityFitnessRule = new EntityFitnessRule();
            entityFitnessRule.id = event.eventType.toString() + ":" + event.toSimpleId();
            EntityFitnessScoreEvent scoreEvent = new EntityFitnessScoreEvent(event, score, entityFitnessRule);
            scoreEvent.life = score * 5;
            addScoreEvent(scoreEvent);
        }
    }
    public class EventHolder{
        public int count = 0;
/*        public String attributeId;
        public String attributeType;*/
    }
}
