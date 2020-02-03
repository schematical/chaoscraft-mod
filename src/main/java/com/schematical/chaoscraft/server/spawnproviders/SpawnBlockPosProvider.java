package com.schematical.chaoscraft.server.spawnproviders;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

public class SpawnBlockPosProvider implements iServerSpawnProvider {

    @Override
    public BlockPos getSpawnPos(ServerOrgManager serverOrgManager){
        int i = (int)Math.floor(Math.random() * ChaosBlocks.spawnBlocks.size());
        return ChaosBlocks.spawnBlocks.get(i).add(new Vec3i(0,2,0));
    }
}
