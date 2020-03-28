package com.schematical.chaoscraft.blocks;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import com.schematical.chaoscraft.tileentity.WaypointBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.FireworkRocketEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class WaypointBlock extends Block implements ITileEntityProvider {
    public WaypointBlock(Properties properties) {
        super(properties);
    }
    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        ChaosCraft.LOGGER.debug("Clicked! " + player.getName());

    }
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
       /* ChaosCraft.LOGGER.debug("Walked! " + entityIn.getName());
        //If it is a OrgEntity give it points
        if(worldIn.isRemote){
            return;
        }
        if(! (entityIn instanceof OrgEntity)){
            return;
        }
        OrgEntity orgEntity = (OrgEntity) entityIn;
        orgEntity.setHealth(-1);


        ServerWorld serverWorld = ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD);
        FireworkRocketEntity fireworkRocketEntity = EntityType.FIREWORK_ROCKET.create(serverWorld);
        fireworkRocketEntity.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0);

        serverWorld.summonEntity(fireworkRocketEntity);*/
    }
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        ChaosCraft.LOGGER.info("NeighborStateChange: " + facingState.getBlock().getRegistryName().toString());
        if(worldIn.isRemote()){
            return stateIn;
        }
        if(facingState.isAir(worldIn, facingPos)){
            ChaosCraft.LOGGER.info("Is Air");
            return stateIn;
        }
        ServerOrgManager serverOrgManager = ChaosCraft.getServer().findOrgThatAlteredBlock(facingPos);
        if(serverOrgManager == null){
            ChaosCraft.LOGGER.error("Cannot find the owner");

        }else {
            CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.BLOCK_PLACED_TOUCHING_WAYPOINT);
            worldEvent.block = facingState.getBlock();
            serverOrgManager.test(worldEvent);
        }

        worldIn.setBlockState(facingPos, Blocks.AIR.getDefaultState(), 1);
        return stateIn;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new WaypointBlockTileEntity();
    }


}
