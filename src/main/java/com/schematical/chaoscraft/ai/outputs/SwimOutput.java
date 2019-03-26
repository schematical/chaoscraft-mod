package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.RayTraceResult;

import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public class SwimOutput extends OutputNeuron {


    @Override
    public float evaluate(){
        if(!(this.nNet.entity.isInWater() || this.nNet.entity.isInLava())){
            return _lastValue;
        }
        return super.evaluate();
    }
    @Override
    public void execute() {
        if(this._lastValue <= .5){
            return;
        }
        if(!(this.nNet.entity.isInWater() || this.nNet.entity.isInLava())){
            return;
        }
        if (this.nNet.entity.getRNG().nextFloat() < 0.8F)
        {
            this.nNet.entity.getJumpHelper().setJumping();
        }

    }
}
