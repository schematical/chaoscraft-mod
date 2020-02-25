package com.schematical.chaoscraft.server.spawnproviders;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import com.schematical.chaoscraft.blocks.SpawnBlock;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;

public class SpawnBlockPosProvider implements iServerSpawnProvider {

    @Override
    public BlockPos getSpawnPos(ServerOrgManager serverOrgManager){
        if(ChaosBlocks.spawnBlocks.size() == 0){
            ChaosCraft.LOGGER.error("Cannot find any spawnBlocks");
            return null;
        }
        ArrayList<BlockPos> spawnBlocks = new ArrayList<>();
        for (BlockPos pos : ChaosBlocks.spawnBlocks) {
            SpawnBlockTileEntity spawnBlockTileEntity = (SpawnBlockTileEntity)ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD).getTileEntity(pos);
            if(
                    spawnBlockTileEntity != null &&
                    spawnBlockTileEntity.getSpawnPointId().equals("hill")
            ){
                spawnBlocks.add(pos);
            }
        }
        if(spawnBlocks.size() == 0){
            return null;
        }
        int i = (int)Math.floor(Math.random() * spawnBlocks.size());
        return spawnBlocks.get(i).add(new Vec3i(0,2,0));
    }
}
