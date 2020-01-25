package com.schematical.chaoscraft.entities;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class AlteredBlockInfo {

    public BlockPos blockPos;
    public BlockState state;
    public AlteredBlockInfo(BlockPos _blockPos, BlockState _state){
        blockPos = _blockPos;
        state = _state;
    }


}
