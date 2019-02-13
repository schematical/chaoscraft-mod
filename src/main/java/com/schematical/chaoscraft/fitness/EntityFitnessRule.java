package com.schematical.chaoscraft.fitness;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.Enum;
import com.schematical.chaoscraft.ai.NeuronDep;
import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.events.CCWorldEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

/**
 * Created by user1a on 1/4/19.
 */
public class EntityFitnessRule {
    public String id;
    public String eventType;
    public String attributeId;
    public String attributeValue;
    public int scoreEffect;
    public int lifeEffect = 0;
    public int maxOccurrences = -1;
    protected int occurrences = 0;
    public void parseData(JSONObject jsonObject){
        eventType = jsonObject.get("eventType").toString();
        Object jsonId = jsonObject.get("id");
        if(jsonId != null) {
            id = jsonId.toString();
        }
        Object jsonAttributeId = jsonObject.get("attributeId");
        if(jsonAttributeId != null) {
            attributeId = jsonAttributeId.toString();
        }
        Object jsonAttributeValue = jsonObject.get("attributeValue");
        if(jsonAttributeId != null) {
            attributeValue = jsonAttributeValue.toString();
        }
        scoreEffect = Integer.parseInt(jsonObject.get("scoreEffect").toString());
        if(jsonObject.get("lifeEffect") != null) {
            lifeEffect = Integer.parseInt(jsonObject.get("lifeEffect").toString());
        }
        if(jsonObject.get("maxOccurrences") != null) {
            maxOccurrences = Integer.parseInt(jsonObject.get("maxOccurrences").toString());
        }
    }

    public EntityFitnessScoreEvent testWorldEvent(CCWorldEvent event) {
        if(!eventType.equals(event.eventType)) {
            return null;
        }
        if(occurrences >= maxOccurrences){
            return null;
        }
        if(
            attributeId != null &&
            attributeValue != null
        ){
           switch (attributeId){
               case(Enum.BLOCK_ID):
                   if(event.block == null){
                       ChaosCraft.logger.error("No block to check against!");
                   }
                   int blockId = Block.getIdFromBlock(event.block);
                   if(blockId != Integer.parseInt(attributeValue)){
                       return null;
                   }
               break;
               case(Enum.ENTITY_ID):
                   if(event.entity == null){
                       ChaosCraft.logger.error("No `entity` to check against!");
                   }
                   int entityId = event.entity.getEntityId();
                   if(entityId != Integer.parseInt(attributeValue)){
                       return null;
                   }
                   break;
               case(Enum.ITEM_ID):
                   if(event.item == null){
                       ChaosCraft.logger.error("No `item` to check against!");
                   }
                   int itemId = Item.getIdFromItem(event.item);
                   if(itemId != Integer.parseInt(attributeValue)){
                       return null;
                   }
                   break;
               default:
                   ChaosCraft.logger.error("Invalid `attributeId`: " + attributeId);
                   return null;
           }
        }

        EntityFitnessScoreEvent scoreEvent  = new EntityFitnessScoreEvent(event, scoreEffect);
        scoreEvent.life = lifeEffect;
        occurrences += 1;
        return scoreEvent;
    }
}
