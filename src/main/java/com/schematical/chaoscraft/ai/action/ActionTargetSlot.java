package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ActionTargetSlot extends TargetSlot {
    private Class actionBaseClass;
    /*
    Class cls = Class.forName(fullClassName);
    BiologyBase biologyBase = (BiologyBase) cls.newInstance();

     */
    public static void init(NeuralNet neuralNet){
        ArrayList<Class> classes = new ArrayList<Class>();

        classes.add(MeleeAttackAction.class);
        classes.add(DigBlockAction.class);
        classes.add(PlaceBlockAction.class);

        for (Class c : classes) {
            ActionTargetSlot actionTargetSlot = new ActionTargetSlot();
            actionTargetSlot.actionBaseClass = c;

            actionTargetSlot.id = "ActionTargetSlot_" + c.getSimpleName();
            neuralNet.biology.put(actionTargetSlot.id, actionTargetSlot);
        }
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

    public boolean isValid() {


        try {
           Method m = actionBaseClass.getMethod("validateTarget", OrgEntity.class, ChaosTarget.class);

            return (boolean)m.invoke(null, getEntity(), getTarget());
        } catch (NoSuchMethodException e) {
           throw new ChaosNetException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ChaosNetException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ChaosNetException(e.getMessage());
        }


    }
}
