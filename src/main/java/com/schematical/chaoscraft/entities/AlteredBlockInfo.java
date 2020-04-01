package com.schematical.chaoscraft.entities;

import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class AlteredBlockInfo {

    public BlockPos blockPos;
    public BlockState state;
    public ServerOrgManager serverOrgManager;
    public AlteredBlockInfo(BlockPos _blockPos, BlockState _state,  ServerOrgManager serverOrgManager){
        blockPos = _blockPos;
        state = _state;
        this.serverOrgManager = serverOrgManager;
    }


}
