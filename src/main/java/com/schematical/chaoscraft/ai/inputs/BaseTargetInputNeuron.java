package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.Eye;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

import java.util.List;

public class BaseTargetInputNeuron extends InputNeuron {
    public String attributeId;
    public String attributeValue;

    private int maxDistance = 20;
    protected Vec3d getTargetPosition() {
        switch(attributeId){
            case(CCAttributeId.TARGET_SLOT):
                TargetSlot targetSlot = (TargetSlot) nNet.getBiology(attributeValue);
                return targetSlot.getTargetPosition();
           // break;
            case(CCAttributeId.ENTITY_ID):

                AxisAlignedBB grownBox = nNet.entity.getBoundingBox().grow(maxDistance, maxDistance, maxDistance);
                List<Entity> entities =  nNet.entity.world.getEntitiesWithinAABB(LivingEntity.class,  grownBox);
                entities.addAll(nNet.entity.world.getEntitiesWithinAABB(ItemEntity.class,  grownBox));
                Entity closestEntity = null;
                double closestEntityDist = 10000;
                int entityCount = 0;
                for (Entity entity : entities) {
                    String entityId =  entity.getType().getRegistryName().getNamespace() + ":" + entity.getType().getRegistryName().getPath();
                    if(entityId.equals(attributeValue)){
                        entityCount += 1;
                        double dist =  nNet.entity.getPositionVec().distanceTo(entity.getPositionVec());
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
            break;
            default:
                throw new ChaosNetException("Invalid `attributeId`: " + attributeId);
        }

        return null;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();

    }
}
