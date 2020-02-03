package com.schematical.chaoscraft.blocks;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class SpawnBlock extends Block implements ITileEntityProvider {
    public boolean tickHasBeenSet = false;
    public SpawnBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        //if (!stateIn.isValidPosition(worldIn, currentPos)) {

        //}

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
    @Override
    public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
        //ChaosCraft.LOGGER.debug("SpawnBlock Ticked!  1" );
    }
    @Override
    public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_){
       // ChaosCraft.LOGGER.debug("SpawnBlock Ticked! 2" );
    }
    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if(!tickHasBeenSet){
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, 1);
        }
        ChaosCraft.LOGGER.debug("Clicked! " + player.getName());

    }
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if(!tickHasBeenSet){
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, 1);
        }
        //ChaosCraft.LOGGER.debug("Walked! " + entityIn.getName());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new SpawnBlockTileEntity();
    }

    //Load some stuff



}
