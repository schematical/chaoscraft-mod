package com.schematical.chaoscraft.fitness;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.Enum;
import com.schematical.chaoscraft.ai.NeuronDep;
import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.events.CCWorldEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 1/4/19.
 */
public class EntityFitnessRule {
    public String id;
    public String eventType;
    public String attributeId;
    public List<String> attributeValue;
    public int scoreEffect;
    public int lifeEffect = 0;
    public int maxOccurrences = -1;

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
            if(jsonAttributeId instanceof  JSONArray){
                attributeValue = (ArrayList<String>) jsonAttributeValue;
            }else {
                attributeValue = new ArrayList<String>();
                attributeValue.add(jsonAttributeValue.toString());
            }
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


        if(
            attributeId != null &&
            attributeValue != null
        ){
            ResourceLocation resourceLocation;
           switch (attributeId){
               case(Enum.BLOCK_ID):
                   if(event.block == null){
                       ChaosCraft.logger.error("No block to check against!");
                   }

                   resourceLocation = event.block.getRegistryName();
                   String blockId = resourceLocation.getResourceDomain() + ":" + resourceLocation.getResourcePath();
                   ChaosCraft.logger.info("Testing: " + blockId);
                   if(!attributeValue.contains(blockId)){
                       return null;
                   }
               break;
               case(Enum.ENTITY_ID):
                   if(event.entity == null){
                       ChaosCraft.logger.error("No `entity` to check against!");
                   }

                   resourceLocation = EntityRegistry.getEntry(event.entity.getClass()).getRegistryName();
                   String entityId = resourceLocation.getResourceDomain() + ":" + resourceLocation.getResourcePath();
                   if(!attributeValue.contains(entityId)){
                       return null;
                   }
                   break;
               case(Enum.ITEM_ID):
                   if(event.item == null){
                       ChaosCraft.logger.error("No `item` to check against!");
                   }
                   resourceLocation = event.item.getRegistryName();
                   String itemId = resourceLocation.getResourceDomain() + ":" + resourceLocation.getResourcePath();


                   if(attributeValue.contains(itemId)){
                       return null;
                   }
                   break;
               default:
                   ChaosCraft.logger.error("Invalid `attributeId`: " + attributeId);
                   return null;
           }
        }

        EntityFitnessScoreEvent scoreEvent  = new EntityFitnessScoreEvent(event, scoreEffect, this);
        scoreEvent.life = lifeEffect;

        return scoreEvent;
    }
}
