package com.schematical.chaoscraft.fitness;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.Enum;
import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaosnet.model.ChaosNetException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

  public void parseData(JSONObject jsonObject) {
    eventType = jsonObject.get("eventType").toString();
    Object jsonId = jsonObject.get("id");
    if (jsonId != null) {
      id = jsonId.toString();
    }
    Object jsonAttributeId = jsonObject.get("attributeId");
    if (jsonAttributeId != null) {
      attributeId = jsonAttributeId.toString();
    }
    Object jsonAttributeValue = jsonObject.get("attributeValue");
    if (jsonAttributeValue != null) {
      if (jsonAttributeValue instanceof JSONArray) {
        attributeValue = (ArrayList<String>) jsonAttributeValue;
      } else {
        attributeValue = new ArrayList<String>();
        attributeValue.add(jsonAttributeValue.toString());
      }
    }
    scoreEffect = Integer.parseInt(jsonObject.get("scoreEffect").toString());
    if (jsonObject.get("lifeEffect") != null) {
      lifeEffect = Integer.parseInt(jsonObject.get("lifeEffect").toString());
    }
    if (jsonObject.get("maxOccurrences") != null) {
      maxOccurrences = Integer.parseInt(jsonObject.get("maxOccurrences").toString());
    }
  }

  public EntityFitnessScoreEvent testWorldEvent(CCWorldEvent event) {
    if (!eventType.equals(event.eventType.toString())) {
      return null;
    }

    if (
        attributeId != null &&
            attributeValue != null
    ) {
      ResourceLocation resourceLocation;
      switch (attributeId) {
        case (Enum.BLOCK_ID):
          if (event.block == null) {
            throw new ChaosNetException("No block to check against! " + event.eventType);
          }

          resourceLocation = event.block.getRegistryName();
          String blockId = resourceLocation.getNamespace() + ":" + resourceLocation.getPath();
          //ChaosCraft.logger.info("Testing: " + blockId);
          if (!attributeValue.contains(blockId)) {
            return null;
          }
          break;
        case (Enum.ENTITY_ID):
          if (event.entity == null) {
            ChaosCraft.logger.error("No `entity` to check against!");
          }

          resourceLocation = EntityRegistry.getEntry(event.entity.getClass()).getRegistryName();
          String entityId = resourceLocation.getNamespace() + ":" + resourceLocation.getPath();
          if (!attributeValue.contains(entityId)) {
            return null;
          }
          break;
        case (Enum.ITEM_ID):
          if (event.item == null) {
            ChaosCraft.logger.error("No `item` to check against!");
          }
          resourceLocation = event.item.getRegistryName();
          String itemId = resourceLocation.getNamespace() + ":" + resourceLocation.getPath();

          if (!attributeValue.contains(itemId)) {
            return null;
          }
          break;
        default:
          ChaosCraft.logger.error("Invalid `attributeId`: " + attributeId);
          return null;
      }
    }

    int _scoreEffect = (int) Math.round(event.amount * scoreEffect);
    EntityFitnessScoreEvent scoreEvent = new EntityFitnessScoreEvent(event, _scoreEffect, this);
    scoreEvent.life = lifeEffect;

    return scoreEvent;
  }
}
