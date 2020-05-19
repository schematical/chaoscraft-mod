package com.schematical.chaoscraft;

import net.minecraft.util.Direction;

import java.util.ArrayList;

/**
 * Created by user1a on 12/30/18.
 */
public class Enum {
    public static final String BLOCK_ID = "BLOCK_ID";
    public static final String ENTITY_ID = "ENTITY_ID";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String OWNER_ENTITY = "OWNER_ENTITY";
    public static final String AXIS = "AXIS";

    public static final String ALIGH = "ALIGH";
    public static final String OPPONENT = "OPPONENT";


    public final static String INPUT = "INPUT";
    public final static String OUTPUT = "OUTPUT";
    public final static String MIDDLE = "MIDDLE";
    public final static int SPEED = 4;
    public final static String BLOCK_TOUCH_STATE = "BLOCK_TOUCH_STATE";
    public final static String _base_type = "bt";//"_base_type";
    public final static String $TYPE = "t";//"_base_type";
    public final static String dependencies = "d";//"dependencies";
    public final static String $EVAL_GROUP = "eg";//"$EVAL_GROUP";
    public final static String weight = "w";//"weight";
    public final static String activator = "a";//"activator";
    public final static String neuronId = "n";//"activator";
    public static ArrayList<Direction> getDirections() {
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.DOWN);
        directions.add(Direction.UP);
        directions.add(Direction.NORTH);
        directions.add(Direction.SOUTH);
        directions.add(Direction.WEST);
        directions.add(Direction.EAST);
        return directions;
    }
}

