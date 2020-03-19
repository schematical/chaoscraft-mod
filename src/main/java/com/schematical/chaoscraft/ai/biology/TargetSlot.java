package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientActionPacket;
import com.schematical.chaoscraft.util.TargetHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by user1a on 2/26/19.
 */
public class TargetSlot extends BiologyBase implements iTargetable {
    private Entity targetEntity;
    private BlockPos blockPos;

    public void setTarget(Entity entity){
        this.blockPos = null;
        this.targetEntity = entity;
    }
    public void setTarget(BlockPos blockPos){
        this.targetEntity = null;
        this.blockPos = blockPos;
    }
    public Vec3d getTargetPosition(){
        if(targetEntity != null){
            return targetEntity.getPositionVector();
        }
        if(blockPos != null) {
            return new Vec3d(
                    this.blockPos.getX(),
                    this.blockPos.getY(),
                    this.blockPos.getZ()
            );
        }
        return null;
    }
    public Vec3d getTargetPositionCenter(){
        if(targetEntity != null){
            Vec3d vec3d = targetEntity.getPositionVector();
            AxisAlignedBB box = targetEntity.getBoundingBox();
            return vec3d.add(
                    box.getCenter()
            );
        }
        if(blockPos != null) {
            return new Vec3d(
                    this.blockPos.getX() + .5f,
                    this.blockPos.getY()+ .5f,
                    this.blockPos.getZ()+ .5f
            );
        }
        return null;
    }
    public CCObserviableAttributeCollection getObservedAttributes(OrgEntity orgEntity){
        if(this.targetEntity != null){
            return orgEntity.observableAttributeManager.Observe(this.targetEntity);
        }
        if(this.blockPos != null){
            return orgEntity.observableAttributeManager.Observe(this.blockPos, orgEntity.world);
        }
        return null;

    }

    @Override
    public Entity getTargetEntity() {
        return targetEntity;
    }

    @Override
    public BlockPos getTargetBlockPos() {
        return blockPos;
    }

    public Double getYawDelta() {
        Vec3d targetPosition = getTargetPositionCenter();
        if (targetPosition == null) {
            return null;
        }
        return TargetHelper.getYawDelta(
                targetPosition,
                getEntity().getEyePosition(1),
                getEntity() .rotationYaw
        );

    }

    public Double getPitchDelta() {
        Vec3d targetPosition = getTargetPositionCenter();
        if (targetPosition == null) {
            return null;
        }

        return TargetHelper.getPitchDelta(
                targetPosition,
                getEntity().getEyePosition(1),//getEntity().getPositionVec(),
                getEntity().rotationPitch //getEntity().getLookVec()
        );
    }

    public Double getDist() {
        Vec3d targetPosition = getTargetPositionCenter();
        if (targetPosition == null) {
            return null;
        }
        return TargetHelper.getDistDelta(
                getEntity().getPositionVector(),
                targetPosition
        );
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);


    }
    @Override
    public void reset() {

    }
    public String toString(){

        String message = id + ": ";
        if(targetEntity != null){
            message += targetEntity.getType().getRegistryName().toString();
        }else if(blockPos != null){

            BlockState blockState = Minecraft.getInstance().world.getBlockState(blockPos);
            message += blockState.getBlock().getRegistryName().toString();
        }else{
            message += " xnull";
        }

        return message;
    }

    public boolean hasTarget() {
        if(targetEntity != null){
            return true;
        }
        if(blockPos != null){
            return true;
        }
        return false;
    }

    public void populateDebug() {
        int maxDistance = 50;
        AxisAlignedBB grownBox = getEntity().getBoundingBox().grow(maxDistance, maxDistance, maxDistance);
        List<Entity> entities =  getEntity().world.getEntitiesWithinAABB(LivingEntity.class,  grownBox);
        entities.addAll(getEntity().world.getEntitiesWithinAABB(ItemEntity.class,  grownBox));
        for (Entity target : entities) {

            //if(!target.equals(getEntity())) {


            CCObserviableAttributeCollection attributeCollection = getEntity().observableAttributeManager.Observe(target);
            if (attributeCollection != null) {
                //ChaosCraft.logger.info(entity.getCCNamespace() + " can see " + attributeCollection.resourceId);
                if(attributeCollection.resourceId.equals("minecraft:bee")){
                    setTarget(target);
                    CCClientActionPacket clientActionPacket = new CCClientActionPacket(getEntity().getCCNamespace(), CCClientActionPacket.Action.SET_TARGET);
                    clientActionPacket.setBiology(this);

                    clientActionPacket.setEntity(target);
                    ChaosNetworkManager.sendToServer(clientActionPacket);
                    return;
                }
            }

            //}
        }

    }
    public String toShortString(){
        String message = toAbreviation() + ": ";
        if(targetEntity != null){
            message += targetEntity.getType().getRegistryName().toString();
        }else if(blockPos != null){

            BlockState blockState = Minecraft.getInstance().world.getBlockState(blockPos);
            message += blockState.getBlock().getRegistryName().toString();
        }else{
            message += " xnull";
        }
        return message;
    }
}

