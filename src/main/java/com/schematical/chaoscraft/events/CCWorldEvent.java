package com.schematical.chaoscraft.events;

import com.schematical.chaoscraft.fitness.EntityFitnessRule;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3i;

/**
 * Created by user1a on 1/4/19.
 */
public class CCWorldEvent {
    public enum Type {
        CRAFT,
        EQUIP,
        BLOCK_MINED,
        BLOCK_PLACED,
        HEALTH_CHANGE,
        ITEM_COLLECTED,
        ENTITY_ATTACKED,
        TOSSED_EQUIPPED_STACK,
        HAS_TRAVELED,
        TRAVEL_ALONG_AXIS,
        TOUCHED_BLOCK,
        HAS_NOT_TRAVELED,
        ATTACK_SUCCESS,
        TARGET_CLOSE_DIST,
        TARGET_CLOSE_YAW,
        TARGET_CLOSE_PITCH,
        TARGET_SELECTED,
        IS_FACING
    }
    public enum Axis{
        X,
        Y,
        Z
    }
    public enum BlockTouchedState{
       HAS_TOUCHED,
       HAS_NOT_TOUCHED
    }
    public Type eventType;
    public Block block;
    public Item item;
    public Entity entity;
    public float amount = 1;
    public Vec3i position;
    public BlockTouchedState blockTouchedState;
    public Axis axis;
    public float extraMultiplier = 1;


    public CCWorldEvent(Type _eventType){
        eventType = _eventType;
    }
    public String toString(){
        String response = eventType.toString();
        if(block != null){
            response += " " + block.getRegistryName();
        }
        if(item != null){
            response += " " + item.getRegistryName();
        }
        if(entity != null){
            response += " " + entity.getName();
        }
        return response;
    }

    /**
     * Created by user1a on 1/4/19.
     */
    public static class EntityFitnessScoreEvent {
        public CCWorldEvent worldEvent;
        public int score;
        public int life;
        public EntityFitnessRule fitnessRule;
        public float multiplier = 1;


        public EntityFitnessScoreEvent(CCWorldEvent event, int _score, EntityFitnessRule _fitnessRule) {
            worldEvent = event;
            score = _score;
            fitnessRule = _fitnessRule;
            if(fitnessRule == null){
                throw new ChaosNetException("Missing `fitnessRule`");
            }
        }
        public float getAdjustedScore(){
            return Math.round(score * multiplier);
        }
        public String toString(){
            String _multiplier = ((Float)multiplier).toString();
            if(_multiplier.length() > 3){
                _multiplier = _multiplier.substring(0, 3);
            }
            return worldEvent.toString() + " = " + getAdjustedScore() + "(" + score + "*" + _multiplier + ")";
        }
    }
}
