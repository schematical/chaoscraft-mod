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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
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
    public boolean _blocksCached = false;
    public boolean _entitiesCached = false;

    public ArrayList<CCObserviableAttributeCollection> canSeenBlocks(){
        if(_blocksCached){
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
        _blocksCached = true;
        return seenBlocks;

    }
    public ArrayList<CCObserviableAttributeCollection> canSeenEntities(){
        if(_entitiesCached){
            return seenEntities;
        }
        AxisAlignedBB grownBox = entity.getEntityBoundingBox().grow(maxDistance, maxDistance, maxDistance);
        List<Entity> entities =  entity.world.getEntitiesWithinAABB(EntityLiving.class,  grownBox);
        entities.addAll(entity.world.getEntitiesWithinAABB(EntityItem.class,  grownBox));

        for (Entity target : entities) {

            if(!target.equals(entity)) {
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


                RayTraceResult rayTraceResult = target.getEntityBoundingBox().calculateIntercept(vec3d, vec3d2);
                if (rayTraceResult != null) {

                    CCObserviableAttributeCollection attributeCollection = entity.observableAttributeManager.Observe(target);
                    if (attributeCollection != null) {
                        //ChaosCraft.logger.info(entity.getCCNamespace() + " can see " + attributeCollection.resourceId);
                        seenEntities.add(attributeCollection);
                    }
                }
            }
        }
        _entitiesCached = true;
        return seenEntities;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        index = Integer.parseInt(jsonObject.get("index").toString());

        pitch = (float)Math.toRadians(Float.parseFloat(jsonObject.get("pitch").toString()));
        yaw = (float)Math.toRadians(Float.parseFloat(jsonObject.get("yaw").toString()));
        maxDistance = Float.parseFloat(jsonObject.get("maxDistance").toString());
    }
    @Override
    public void reset() {
        seenEntities.clear();
        seenBlocks.clear();
        _blocksCached = false;
        _entitiesCached = false;
    }

}

