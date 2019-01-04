package com.schematical.chaoscraft.events;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Created by user1a on 1/4/19.
 */
public class CCWorldEvent {
    public String eventType;
    public Block block;
    public Item item;

    public CCWorldEvent(String _eventType){
        eventType = _eventType;
    }
}
