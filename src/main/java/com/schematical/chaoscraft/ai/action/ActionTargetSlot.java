package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;

public class ActionTargetSlot extends TargetSlot {
    public Class actionBase;
    /*
    Class cls = Class.forName(fullClassName);
    BiologyBase biologyBase = (BiologyBase) cls.newInstance();

     */
    public static void init(NeuralNet neuralNet){
        //TODO: Add one of each Action type to the biology
        ActionTargetSlot actionTargetSlot = new ActionTargetSlot();
        actionTargetSlot.actionBase = MeleeAttackAction.class;


    }



}
