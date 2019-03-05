package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.util.PositionRange;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1a on 2/26/19.
 */
public class Eye  extends BiologyBase{
    public int index;
    public float pitch;
    public float yaw;
    public float maxDistance;
    public ArrayList<CCObserviableAttributeCollection> seenEntities = new ArrayList<CCObserviableAttributeCollection>();
    public ArrayList<CCObserviableAttributeCollection> seenBlocks = new ArrayList<CCObserviableAttributeCollection>();
    public boolean _cached = false;


    public ArrayList<CCObserviableAttributeCollection> canSeenBlocks(){
        if(_cached){
            return seenBlocks;
        }
        Vec3d vec3d = entity.getPositionEyes(1);
        Vec3d vec3d1 = entity.getLook(1);
        vec3d1 = vec3d1.rotatePitch(this.pitch);
        vec3d1 = vec3d1.rotateYaw(this.yaw);
        Vec3d vec3d2 = vec3d.add(
            new Vec3d(
                vec3d1.x * maxDistance,
                vec3d1.y * maxDistance,
                vec3d1.z * maxDistance
            )
        );


        RayTraceResult rayTraceResult = entity.world.rayTraceBlocks(vec3d, vec3d2, false, false, false);

        if(rayTraceResult != null) {
            IBlockState blockState = entity.world.getBlockState(
                rayTraceResult.getBlockPos()
            );
            Block block = blockState.getBlock();

            CCObserviableAttributeCollection attributeCollection = entity.observableAttributeManager.Observe(block);
            seenBlocks.add(attributeCollection);
        }
        _cached = true;
        return seenBlocks;

    }
    public ArrayList<CCObserviableAttributeCollection> canSeenEntities(){
        if(_cached){
            return seenEntities;
        }
        List<EntityLiving> entities =  entity.world.getEntitiesWithinAABB(EntityLiving.class,  entity.getEntityBoundingBox().grow(maxDistance, maxDistance, maxDistance));

        for (EntityLiving target : entities) {


            Vec3d vec3d = entity.getPositionEyes(1);
            vec3d = vec3d.rotatePitch(this.pitch);
            vec3d = vec3d.rotateYaw(this.yaw);
            Vec3d vec3d1 = entity.getLook(1);
            Vec3d vec3d2 = vec3d.add(
                new Vec3d(
                vec3d1.x * maxDistance,
                vec3d1.y * maxDistance,
                vec3d1.z * maxDistance
                )
            );

            RayTraceResult rayTraceResult = target.getEntityBoundingBox().calculateIntercept(vec3d, vec3d2);
            if (rayTraceResult != null) {
                CCObserviableAttributeCollection attributeCollection = entity.observableAttributeManager.Observe(target);
                if(attributeCollection != null) {
                    seenEntities.add(attributeCollection);
                }
            }
        }
        _cached = true;
        return seenEntities;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        index = Integer.parseInt(jsonObject.get("index").toString());

        pitch = (float)Math.toRadians(Float.parseFloat(jsonObject.get("pitch").toString()));
        yaw = (float)Math.toRadians(Float.parseFloat(jsonObject.get("yaw").toString()));
    }
    @Override
    public void reset() {
        seenEntities.clear();
        seenBlocks.clear();
        _cached = false;
    }

}

