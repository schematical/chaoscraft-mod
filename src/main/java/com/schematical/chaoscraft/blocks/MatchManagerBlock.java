package com.schematical.chaoscraft.blocks;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.tileentity.MatchManagerBlockTileEntity;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class MatchManagerBlock extends Block implements ITileEntityProvider {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public MatchManagerBlock(Properties properties) {
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


    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new MatchManagerBlockTileEntity();
    }
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (worldIn.isRemote) {


            ChaosCraft.getClient().showMatchManagerBlockGui((MatchManagerBlockTileEntity) tileentity);
            return ActionResultType.SUCCESS;
        } else {
            ChaosCraft.LOGGER.debug("Server Clicked! " + player.getName());
            //activate(state, worldIn, pos);
            return ActionResultType.PASS;
        }
    }

}
