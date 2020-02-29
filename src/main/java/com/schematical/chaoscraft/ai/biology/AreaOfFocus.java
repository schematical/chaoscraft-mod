package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user1a on 2/26/19.
 */
public class AreaOfFocus extends BiologyBase{
    public int index;

    public float currDistance;
    public float maxFocusDistance;
    public Vec3d currFocusVec;
    public ArrayList<Entity> seenEntities = new ArrayList<Entity>();
    public HashMap<Float, HashMap<Float, HashMap<Float, ArrayList<CCObserviableAttributeCollection>>>> entityCache = new HashMap<Float, HashMap<Float, HashMap<Float, ArrayList<CCObserviableAttributeCollection>>>>();
    public boolean _entitiesCached = false;
    public int viewRange;
    public Vec3d getFocusPoint(){
        if(this.currFocusVec != null){
            return this.currFocusVec;
        }
        Vec3d vec3d = getEntity().getEyePosition(1);
        Vec3d vec3d1 = getEntity().getLook(1);


        this.currFocusVec = vec3d.add(
            new Vec3d(
            vec3d1.x * currDistance,
            vec3d1.y * currDistance,
            vec3d1.z * currDistance
            )
        );
        return this.currFocusVec;

    }
    public CCObserviableAttributeCollection canSeenBlock(float x, float y, float z){


        Vec3d vec3d = this.getFocusPoint();
        BlockPos blockPos = new BlockPos(
            new Vec3d(
                vec3d.x + x,
                vec3d.y + y,
                vec3d.z + z
            )
        );
        BlockState blockState = getEntity().world.getBlockState(
           blockPos
        );

        Block block = blockState.getBlock();

        CCObserviableAttributeCollection attributeCollection = getEntity().observableAttributeManager.Observe(block);
        return attributeCollection;

    }
    public ArrayList<CCObserviableAttributeCollection>  canSeenEntities(float x, float y, float z){
        Vec3d vec3d = this.getFocusPoint();
        if(!_entitiesCached) {


            float dist = viewRange + 1;
            AxisAlignedBB grownBox = new AxisAlignedBB(
            vec3d.x + dist,
            vec3d.y + dist,
            vec3d.z + dist,
            vec3d.x - dist,
            vec3d.y - dist,
            vec3d.z - dist
            );
            seenEntities.addAll(getEntity().world.getEntitiesWithinAABB(LivingEntity.class, grownBox));
            seenEntities.addAll(getEntity().world.getEntitiesWithinAABB(ItemEntity.class, grownBox));
            _entitiesCached = true;
        }

        if(!entityCache.containsKey(x)){
            entityCache.put(x, new  HashMap<Float, HashMap<Float, ArrayList<CCObserviableAttributeCollection>>>());
        }
        if(!entityCache.get(x).containsKey(y)){
            entityCache.get(x).put(y, new  HashMap<Float, ArrayList<CCObserviableAttributeCollection>>());
        }
        ArrayList<CCObserviableAttributeCollection> observedEntities;
        if(!entityCache.get(x).get(y).containsKey(z)){
            observedEntities = new ArrayList<>();
            entityCache.get(x).get(y).put(z, observedEntities);
        }else{
            return entityCache.get(x).get(y).get(z);
        }

        Vec3d searchVecStart = new Vec3d(
        vec3d.x + x,
        vec3d.y + y,
        vec3d.z + z
        );
        BlockPos blockPos = new BlockPos(searchVecStart);
        BlockState blockState = getEntity().world.getBlockState(
            blockPos
        );
        VoxelShape voxelShape = blockState.getCollisionShape(getEntity().world, blockPos);
        if(voxelShape == null ||  voxelShape.isEmpty()){
            return observedEntities;
        }
        AxisAlignedBB boundingBox = voxelShape.getBoundingBox();
        AxisAlignedBB blockBox = boundingBox.offset(blockPos);

        for (Entity target : seenEntities) {

            if(!target.equals(getEntity())) {

                if (target.getBoundingBox().intersects(blockBox)) {

                    CCObserviableAttributeCollection attributeCollection = getEntity().observableAttributeManager.Observe(target);
                    if (attributeCollection != null) {
                        //ChaosCraft.logger.info(entity.getCCNamespace() + " can see " + attributeCollection.resourceId);
                        observedEntities.add(attributeCollection);
                    }
                }
            }
        }

        return observedEntities;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        index = Integer.parseInt(jsonObject.get("index").toString());
        maxFocusDistance = Integer.parseInt(jsonObject.get("maxFocusDistance").toString());
        viewRange = Integer.parseInt(jsonObject.get("viewRange").toString());
    }
    @Override
    public void reset() {
       super.reset();
       this.currFocusVec = null;
       this._entitiesCached = false;
       this.entityCache.clear();//Might need to do more GC
       this.seenEntities.clear();

    }

    public void setDistance(float v) {
        this.currDistance = v;
    }
}

