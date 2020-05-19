package com.schematical.chaoscraft.ai.memory;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import sun.security.x509.UniqueIdentity;

public class BlockStateMemoryBufferSlot {
    public BlockPos blockPos;
    public BlockState blockState;
    public int ownerEntityId;
    public BlockPos debugBlockPos;

    public BlockStateMemoryBufferSlot(BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
