package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientActionPacket;
import com.schematical.chaoscraft.util.ChaosTarget;
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

    private ChaosTarget chaosTarget = new ChaosTarget();
   /* public void setTarget(Entity entity){
        this.chaosTarget.setTarget(entity);
    }
    public void setTarget(BlockPos blockPos){
        this.chaosTarget.setTarget(blockPos);
    }*/
    public ChaosTarget getTarget(){
        return chaosTarget;
    }
    public void setTarget(ChaosTarget chaosTarget){
        this.chaosTarget = chaosTarget;
    }
    public Vec3d getTargetPosition(){

        return this.chaosTarget.getTargetPosition();
    }
    public Vec3d getTargetPositionCenter(){

        return this.chaosTarget.getTargetPositionCenter();
    }
    public CCObserviableAttributeCollection getObservedAttributes(OrgEntity orgEntity){

        return this.chaosTarget.getObservedAttributes(orgEntity);

    }

    @Override
    public Entity getTargetEntity() {
        return this.chaosTarget.getTargetEntity();
    }

    @Override
    public BlockPos getTargetBlockPos() {
        return this.chaosTarget.getTargetBlockPos();
    }

    public Double getYawDelta() {
       return this.chaosTarget.getYawDelta(getEntity());
    }

    public Double getPitchDelta() {
        return this.chaosTarget.getPitchDelta(getEntity());
    }

    public Double getDist() {
        return this.chaosTarget.getDist(getEntity());
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
        if(chaosTarget != null) {
            message += chaosTarget.toString();
        }else{
            message += " xnull";
        }

        return message;
    }

    public boolean hasTarget() {
       return chaosTarget.hasTarget();
    }


    public String toShortString(){
        String message = toAbreviation() + ": ";
        if(chaosTarget != null) {
            message += chaosTarget.toString();
        }else{
            message += " xnull";
        }
        return message;
    }
}

