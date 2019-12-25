package com.schematical.chaoscraft.ai.biology;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 2/26/19.
 */
public class TargetSlot extends BiologyBase {
    public Entity entity;
    public Vec3d position;
    public float weight;

    public void setTarget(Entity entity){
        this.entity = entity;
    }
    public void setTarget(Vec3d vec3d){
        this.position = vec3d;
    }
    public Vec3d getTargetPosition(){
        if(entity != null){
            return entity.getPositionVector();
        }
        return this.position;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);


    }
    @Override
    public void reset() {

    }

}

