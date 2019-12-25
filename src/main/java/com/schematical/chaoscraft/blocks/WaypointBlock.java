package com.schematical.chaoscraft.blocks;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaypointBlock extends Block {
    public WaypointBlock(Properties properties) {
        super(properties);
    }
    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        ChaosCraft.LOGGER.debug("Clicked! " + player.getName());

    }
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        ChaosCraft.LOGGER.debug("Walked! " + entityIn.getName());
        //If it is a OrgEntity give it points
    }
    

}
