package com.schematical.chaoscraft.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by user1a on 12/7/18.
 */
public class AIEvaluateNeuron extends EntityAIBase
{
    protected OutputNeuron outputNeuron;
    protected Entity entity;
    public AIEvaluateNeuron(Entity entity)
    {
        super();
        this.entity = entity;
    }
    public boolean shouldExecute(){
        float val = outputNeuron.evaluate();
        if(val > 0){
            return true;
        }
        return false;
    }
    public boolean shouldContinueExecuting() {

        return false;//super.shouldContinueExecuting();
    }
    public void startExecuting()
    {
        outputNeuron.execute();
    }
}

