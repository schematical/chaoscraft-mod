package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public class ChatOutput extends OutputNeuron {
    @Override
    public void execute() {
        if(this._lastValue <= .5){
            return;
        }
        this.nNet.entity.sendMessage(new TextComponentString("I am chatting"));


    }
}
