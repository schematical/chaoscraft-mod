package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1a on 2/26/19.
 */
public class Eye  extends BiologyBase {
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
        Vec3d vec3d = getEntity().getEyePosition(1);
        Vec3d vec3d1 = getEntity().getLook(1);
        vec3d1 = vec3d1.rotatePitch(this.pitch);
        vec3d1 = vec3d1.rotateYaw(this.yaw);
        Vec3d vec3d2 = vec3d.add(
            new Vec3d(
                vec3d1.x * maxDistance,
                vec3d1.y * maxDistance,
                vec3d1.z * maxDistance
            )
        );


        RayTraceResult rayTraceResult = getEntity().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE/* RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY*/, getEntity()));

        if(rayTraceResult != null && !rayTraceResult.getType().equals(RayTraceResult.Type.MISS)) {
            BlockPos blockPos = new BlockPos(
                    rayTraceResult.getHitVec()
            );
            BlockState blockState = getEntity().world.getBlockState(
                    blockPos
            );
            Block block = blockState.getBlock();

            CCObserviableAttributeCollection attributeCollection = getEntity().observableAttributeManager.Observe(block);
            attributeCollection.position = new Vec3d(
                blockPos.getX(),
                blockPos.getY(),
                blockPos.getZ()
            );
            seenBlocks.add(attributeCollection);
        }
        _blocksCached = true;
        return seenBlocks;

    }
    public ArrayList<CCObserviableAttributeCollection> canSeenEntities(){
        if(_entitiesCached){
            return seenEntities;
        }
        AxisAlignedBB grownBox = getEntity().getBoundingBox().grow(maxDistance, maxDistance, maxDistance);
        List<Entity> entities =  getEntity().world.getEntitiesWithinAABB(LivingEntity.class,  grownBox);
        entities.addAll(getEntity().world.getEntitiesWithinAABB(ItemEntity.class,  grownBox));

        Vec3d vec3d = getEntity().getEyePosition(1);
        Vec3d vec3d1 = getEntity().getLook(1);
        vec3d1 = vec3d1.rotatePitch(this.pitch);
        vec3d1 = vec3d1.rotateYaw(this.yaw);
        Vec3d vec3d2 = vec3d.add(
            new Vec3d(
            vec3d1.x * maxDistance,
            vec3d1.y * maxDistance,
            vec3d1.z * maxDistance
            )
        );

        for (Entity target : entities) {

            if(!target.equals(getEntity())) {

                target.getBoundingBox().rayTrace(vec3d, vec3d2).ifPresent(position -> {
                    CCObserviableAttributeCollection attributeCollection = getEntity().observableAttributeManager.Observe(target);
                    if (attributeCollection != null) {
                        //ChaosCraft.logger.info(entity.getCCNamespace() + " can see " + attributeCollection.resourceId);
                        seenEntities.add(attributeCollection);
                    }
                });

            }
        }
        _entitiesCached = true;
        return seenEntities;
    }
    public CCObserviableAttributeCollection getClosestSeenObject(){
        double closestDist = 999999;
        CCObserviableAttributeCollection closest = null;
        ArrayList<CCObserviableAttributeCollection> seenColl = getAllSeenObjects();
        Vec3d entityPos = new Vec3d(getEntity().getPosition().getX(), getEntity().getPosition().getY(), getEntity().getPosition().getZ());
        for(CCObserviableAttributeCollection seen: seenColl){
            if(

                seen.position != null
            ){
                double dist = entityPos.distanceTo(seen.position);
                if(
                    closest == null ||
                    dist < closestDist
                ) {
                    closest = seen;
                    closestDist = dist;
                }
            }
        }
        return closest;
    }
    public ArrayList<CCObserviableAttributeCollection> getAllSeenObjects(){
        ArrayList<CCObserviableAttributeCollection> seenColl = canSeenBlocks();
        seenColl.addAll(canSeenEntities());
        return seenColl;
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
    public String toString(){

        String message = "Eye P:"+ Math.round(Math.toDegrees(pitch)) + " Y:" + Math.round(Math.toDegrees(yaw)) + " D:" + maxDistance + " -";
        for (CCObserviableAttributeCollection seenBlock : seenBlocks) {
            message += " " + seenBlock.resourceId;
        }
        for (CCObserviableAttributeCollection seenEntity : seenEntities) {
            message += " " + seenEntity.resourceId;
        }
        return message;
    }

}

