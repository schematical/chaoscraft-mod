package com.schematical.chaoscraft.ai;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityEvilRabbit;
import com.schematical.chaoscraft.entities.EntityRick;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        outputNeuron.exicute();
    }
}

