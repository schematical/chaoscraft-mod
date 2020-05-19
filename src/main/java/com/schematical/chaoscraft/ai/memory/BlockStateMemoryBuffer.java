package com.schematical.chaoscraft.ai.memory;

import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.HashMap;

public class BlockStateMemoryBuffer {
    protected HashMap<BlockPos, BlockStateMemoryBufferSlot> blocks = new HashMap<>();
    public void put(BlockPos blockPos, BlockStateMemoryBufferSlot blockStateMemoryBufferSlot){
        blocks.put(blockPos, blockStateMemoryBufferSlot);
    }
    public BlockStateMemoryBufferSlot get(BlockPos blockPos){
        if(!blocks.containsKey(blockPos)){
            return null;
        }
        return blocks.get(blockPos);
    }

    public Collection<BlockStateMemoryBufferSlot> values() {
       return blocks.values();
    }
}
