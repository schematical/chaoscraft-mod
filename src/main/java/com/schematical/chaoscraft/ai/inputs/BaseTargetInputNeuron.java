package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
//import com.schematical.chaoscraft.util.TargetHelper;
import com.schematical.chaoscraft.ai.biology.iTargetable;
import com.schematical.chaoscraft.util.iHasAttributeIdValue;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

public class BaseTargetInputNeuron extends InputNeuron implements iHasAttributeIdValue {
    public String attributeId;
    public String attributeValue;
    public TargetSlot targetSlot;

   // protected TargetHelper targetHelper;

    //private int maxDistance = 20;
    public BaseTargetInputNeuron(){
        super();
        //targetHelper = new TargetHelper();
    }
    /*protected Vec3d getTargetPosition() {
        switch(attributeId){
            case(CCAttributeId.TARGET_SLOT):
                TargetSlot targetSlot = (TargetSlot) nNet.getBiology(attributeValue);
                return targetSlot.getTargetPosition();
           // break;
            *//*case(CCAttributeId.ENTITY_ID):

                AxisAlignedBB grownBox = getEntity().getBoundingBox().grow(maxDistance, maxDistance, maxDistance);
                List<Entity> entities =  getEntity().world.getEntitiesWithinAABB(LivingEntity.class,  grownBox);
                entities.addAll(getEntity().world.getEntitiesWithinAABB(ItemEntity.class,  grownBox));
                Entity closestEntity = null;
                double closestEntityDist = 10000;
                int entityCount = 0;
                for (Entity entity : entities) {
                    String entityId =  entity.getType().getRegistryName().getNamespace() + ":" + entity.getType().getRegistryName().getPath();
                    if(entityId.equals(attributeValue)){
                        entityCount += 1;
                        double dist = getEntity().getPositionVec().distanceTo(entity.getPositionVec());
                        if(
                                closestEntity == null ||
                                        closestEntityDist > dist
                        ){
                            closestEntity = entity;
                            closestEntityDist = dist;
                        }
                    }
                }
                if(closestEntity != null){
                    return closestEntity.getPositionVec();
                }
            break;*//*
            default:
                Entity entity =  targetHelper.getTarget(this);
                if(entity != null) {
                    return entity.getPositionVec();
                }
                //throw new ChaosNetException("Invalid `attributeId`: " + attributeId);
        }

        return null;
    }*/
    public iTargetable getTarget(){
        return targetSlot;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        if(jsonObject.get("targetSlotId") != null){
            targetSlot = (TargetSlot)nNet.getBiology(jsonObject.get("targetSlotId").toString());
        }else {
            attributeId = jsonObject.get("attributeId").toString();
            attributeValue = jsonObject.get("attributeValue").toString();
        }

    }
    public String toString(){
        String response =super.toString();
        if(targetSlot != null){
            response += targetSlot.toString();
        }
        response += " " + getAttributeId() + "=" + getAttributeValue();

        response += getPrettyCurrValue();

        return response;
    }
    public String toLongString(){
        String response = super.toString();
        response += " " + getAttributeId() + "=" + getAttributeValue();
        return response;
    }
    @Override
    public String getAttributeId() {
        return attributeId;
    }

    @Override
    public String getAttributeValue() {
        return attributeValue;
    }
}
