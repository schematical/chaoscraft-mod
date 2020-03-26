package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaosnet.model.ChaosNetException;

public class ActionTargetSlot extends TargetSlot {
    private Class actionBaseClass;
    /*
    Class cls = Class.forName(fullClassName);
    BiologyBase biologyBase = (BiologyBase) cls.newInstance();

     */
    public static void init(NeuralNet neuralNet){
        //TODO: Add one of each Action type to the biology
        ActionTargetSlot actionTargetSlot = new ActionTargetSlot();
        actionTargetSlot.actionBaseClass = MeleeAttackAction.class;


    }


    public ActionBase createAction() {
        ActionBase actionBase = null;
        try {
            actionBase = (ActionBase) actionBaseClass.newInstance();
            actionBase.setTarget(getTarget());
        } catch (InstantiationException e) {
           throw new ChaosNetException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ChaosNetException(e.getMessage());
        }
        return actionBase;

    }
}
