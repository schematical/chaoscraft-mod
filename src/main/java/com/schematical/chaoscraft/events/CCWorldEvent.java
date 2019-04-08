package com.schematical.chaoscraft.events;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

/**
 * Created by user1a on 1/4/19.
 */
public class CCWorldEvent {

  public Type eventType;
  public Block block;
  public Item item;
  public Entity entity;
  public float amount = 1;
  public CCWorldEvent(Type _eventType) {
    eventType = _eventType;
  }

  public String toString() {
    String response = eventType.toString();
    if (block != null) {
      response += " " + block.getRegistryName();
    }
    if (item != null) {
      response += " " + item.getRegistryName();
    }
    if (entity != null) {
      response += " " + entity.getName();
    }
    return response;
  }

  public enum Type {
    CRAFT,
    EQUIP,
    BLOCK_MINED,
    BLOCK_PLACED,
    HEALTH_CHANGE,
    ITEM_COLLECTED,
    ENTITY_ATTACKED,
    TOSSED_EQUIPPED_STACK,
    HAS_TRAVELED
  }
}
