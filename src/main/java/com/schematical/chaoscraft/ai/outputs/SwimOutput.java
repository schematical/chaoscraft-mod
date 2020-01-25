package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;

import static net.minecraft.entity.LivingEntity.SWIM_SPEED;

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
            //this.nNet.entity.jump();//getJumpHelper().setJumping();
            this.nNet.entity.setMotion(this.nNet.entity.getMotion().add(0.0D, (double)0.04F * this.nNet.entity.getAttribute(SWIM_SPEED).getValue(), 0.0D));
        }

    }
}
