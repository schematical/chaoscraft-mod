package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

/**
 * Created by user1a on 12/8/18.
 */
public class DigOutput extends OutputNeuron {
    @Override
    public void execute() {
        if(this.getCurrentValue() <= .5f){
            return;
        }
        nNet.entity.swingArm(Hand.MAIN_HAND);
        RayTraceResult rayTraceResult = nNet.entity.rayTraceBlocks(nNet.entity.REACH_DISTANCE);
        if(rayTraceResult == null){
            return;
        }
        nNet.entity.dig(new BlockPos(rayTraceResult.getHitVec()));
    }

}
