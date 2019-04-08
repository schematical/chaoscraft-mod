package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 2/26/19.
 */
public class AreaOfFocus extends BiologyBase {

  public int index;

  public float currDistance;
  public float maxFocusDistance;
  public Vec3d currFocusVec;
  public ArrayList<Entity> seenEntities = new ArrayList<Entity>();
  public HashMap<Float, HashMap<Float, HashMap<Float, ArrayList<CCObserviableAttributeCollection>>>> entityCache = new HashMap<Float, HashMap<Float, HashMap<Float, ArrayList<CCObserviableAttributeCollection>>>>();
  public boolean _entitiesCached = false;
  public int viewRange;

  public Vec3d getFocusPoint() {
    if (this.currFocusVec != null) {
      return this.currFocusVec;
    }
    Vec3d vec3d = entity.getPositionEyes(1);
    Vec3d vec3d1 = entity.getLook(1);

    this.currFocusVec = vec3d.add(
        new Vec3d(
            vec3d1.x * currDistance,
            vec3d1.y * currDistance,
            vec3d1.z * currDistance
        )
    );
    return this.currFocusVec;

  }

  public CCObserviableAttributeCollection canSeenBlock(float x, float y, float z) {

    Vec3d vec3d = this.getFocusPoint();
    BlockPos blockPos = new BlockPos(
        new Vec3d(
            vec3d.x + x,
            vec3d.y + y,
            vec3d.z + z
        )
    );
    IBlockState blockState = entity.world.getBlockState(
        blockPos
    );

    Block block = blockState.getBlock();

    CCObserviableAttributeCollection attributeCollection = entity.observableAttributeManager
        .Observe(block);
    return attributeCollection;

  }

  public ArrayList<CCObserviableAttributeCollection> canSeenEntities(float x, float y, float z) {
    Vec3d vec3d = this.getFocusPoint();
    if (!_entitiesCached) {

      float dist = viewRange + 1;
      AxisAlignedBB grownBox = new AxisAlignedBB(
          vec3d.x + dist,
          vec3d.y + dist,
          vec3d.z + dist,
          vec3d.x - dist,
          vec3d.y - dist,
          vec3d.z - dist
      );
      seenEntities.addAll(entity.world.getEntitiesWithinAABB(EntityLiving.class, grownBox));
      seenEntities.addAll(entity.world.getEntitiesWithinAABB(EntityItem.class, grownBox));
      _entitiesCached = true;
    }

    if (!entityCache.containsKey(x)) {
      entityCache.put(x,
          new HashMap<Float, HashMap<Float, ArrayList<CCObserviableAttributeCollection>>>());
    }
    if (!entityCache.get(x).containsKey(y)) {
      entityCache.get(x).put(y, new HashMap<Float, ArrayList<CCObserviableAttributeCollection>>());
    }
    ArrayList<CCObserviableAttributeCollection> observedEntities;
    if (!entityCache.get(x).get(y).containsKey(z)) {
      observedEntities = new ArrayList<>();
      entityCache.get(x).get(y).put(z, observedEntities);
    } else {
      return entityCache.get(x).get(y).get(z);
    }

    Vec3d searchVecStart = new Vec3d(
        vec3d.x + x,
        vec3d.y + y,
        vec3d.z + z
    );
    BlockPos blockPos = new BlockPos(searchVecStart);
    IBlockState blockState = entity.world.getBlockState(
        blockPos
    );
    AxisAlignedBB blockBox = blockState.getBoundingBox(entity.world, blockPos).offset(blockPos);

    for (Entity target : seenEntities) {

      if (!target.equals(entity)) {

        if (target.getEntityBoundingBox().intersects(blockBox)) {

          CCObserviableAttributeCollection attributeCollection = entity.observableAttributeManager
              .Observe(target);
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
  public void parseData(JSONObject jsonObject) {
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

