package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
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
public class FloatMemory extends BiologyBase{
    public int index;
    public float _lastValue = -1;

    public void setValue(float _lastValue){
         this._lastValue = _lastValue;
    }
    public float getValue(){
        return this._lastValue;
    }

    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        index = Integer.parseInt(jsonObject.get("index").toString());

    }
    /*@Override
    public void reset() {

    }*/

}

