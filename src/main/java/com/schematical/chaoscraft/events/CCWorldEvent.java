package com.schematical.chaoscraft.events;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

/**
 * Created by user1a on 1/4/19.
 */
public class CCWorldEvent {
    public String eventType;
    public Block block;
    public Item item;
    public Entity entity;

    public CCWorldEvent(String _eventType){
        eventType = _eventType;
    }
    public String toString(){
        String response = eventType;
        if(block != null){
            response += " " + block.getRegistryName();
        }
        if(item != null){
            response += " " + item.getRegistryName();
        }
        if(entity != null){
            response += " " + entity.getDisplayName();
        }
        return response;
    }
}
