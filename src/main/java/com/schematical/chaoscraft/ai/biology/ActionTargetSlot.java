package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.action.*;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ActionTargetSlot extends TargetSlot {
    private Class actionBaseClass;
    private ChaosTargetItem chaosTargetItem;
    /*
    Class cls = Class.forName(fullClassName);
    BiologyBase biologyBase = (BiologyBase) cls.newInstance();

     */

    public static void init(NeuralNet neuralNet){
        ArrayList<Class> classes = new ArrayList<Class>();

        classes.add(MeleeAttackAction.class);
        classes.add(DigBlockAction.class);
        classes.add(PlaceBlockAction.class);
        classes.add(UseItemAction.class);

        for (Class c : classes) {
            ActionTargetSlot actionTargetSlot = new ActionTargetSlot();
            actionTargetSlot.actionBaseClass = c;

            actionTargetSlot.id = "ActionTargetSlot_" + c.getSimpleName();
            neuralNet.biology.put(actionTargetSlot.id, actionTargetSlot);
        }
    }
    public Class getActionBaseClass(){
        return actionBaseClass;
    }

    public ActionBase createAction() {
        ActionBase actionBase = null;
        try {
            actionBase = (ActionBase) actionBaseClass.newInstance();
            actionBase.setTarget(getTarget());
            actionBase.setTargetItem(getTargetItem());
        } catch (InstantiationException e) {
           throw new ChaosNetException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ChaosNetException(e.getMessage());
        }
        return actionBase;

    }

    private ChaosTargetItem getTargetItem() {
        return chaosTargetItem;
    }

    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        String fullClassName = "com.schematical.chaoscraft.ai.action." + jsonObject.get("action").toString();
        //ChaosCraft.logger.info("Full Class name: " + fullClassName);
        try {
            actionBaseClass = Class.forName(fullClassName);
        } catch (ClassNotFoundException e) {
            throw new ChaosNetException(e.getMessage());
        }

    }
    public boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {

        try {
            Method m = actionBaseClass.getMethod("validateTarget", OrgEntity.class, ChaosTarget.class);

            return (boolean)m.invoke(null, orgEntity, chaosTarget);
        } catch (NoSuchMethodException e) {
            throw new ChaosNetException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ChaosNetException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ChaosNetException(e.getMessage());
        }
    }

    public boolean validateTargetItem(OrgEntity orgEntity, ChaosTargetItem chaosTargetItem) {

        try {
            Method m = actionBaseClass.getMethod("validateTargetItem", OrgEntity.class, ChaosTargetItem.class);

            return (boolean)m.invoke(null, orgEntity, chaosTargetItem);
        } catch (NoSuchMethodException e) {
            throw new ChaosNetException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ChaosNetException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ChaosNetException(e.getMessage());
        }
    }
    public boolean validateTargetAndItem(OrgEntity orgEntity, ChaosTarget chaosTarget, ChaosTargetItem chaosTargetItem) {
        try {
            Method m = actionBaseClass.getMethod("validateTargetAndItem", OrgEntity.class, ChaosTarget.class, ChaosTargetItem.class);

            return (boolean)m.invoke(null, orgEntity, chaosTarget, chaosTargetItem);
        } catch (NoSuchMethodException e) {
            throw new ChaosNetException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ChaosNetException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ChaosNetException(actionBaseClass.getClass().getSimpleName() + "   " + e.getMessage());
        }

    }
    public boolean isValid() {
        return validateTargetAndItem(getEntity(), getTarget(), getTargetItem());
    }

    public String getSimpleActionStatsKey() {
        return getClass().getSimpleName() + "-" + getTarget().getActionStatString(getEntity().world);
    }

    public void setTargetItem(ChaosTargetItem chaosTargetItem) {
        this.chaosTargetItem = chaosTargetItem;
    }
}
