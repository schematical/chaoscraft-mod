package com.schematical.chaoscraft.tileentity;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import com.schematical.chaoscraft.util.BuildArea;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.item.minecart.MinecartCommandBlockEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.logging.Logger;

import static net.minecraft.client.Minecraft.getInstance;

public class BuildAreaMarkerTileEntity extends TileEntity implements ITickableTileEntity {
    public BuildAreaMarkerTileEntity() {
        super(ChaosTileEntity.BUILD_AREA_TILE.get());
    }

    @Override
    public void tick() {
        if(!ChaosBlocks.markerBlocks.contains(this.getPos())) {
            ChaosBlocks.markerBlocks.add(this.getPos());
            BuildArea buildArea = new BuildArea();
            buildArea.buildTemplates();
            buildArea.createMatrices();
            ChaosCraft.buildAreas.add(buildArea);
            ChaosCraft.buildAreaMarkers.add(this);
            buildArea.assignBuildAreaTileEntity(this);
            //buildArea.getBlocks(this.pos);
        }
    }

    public static void resetBuildArea(BlockPos block, World world){
        BlockPos origValue = block;
        block = block.add(1, -2, -1);
        BlockPos currentBlock;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 12; j++){
                for(int k = 0; k < 12; k++){
                    if(world !=  null && !world.isRemote()){
                        currentBlock = block.add(k, i, -j);
                        world.setBlockState(currentBlock, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
        fixGround(origValue, world);
    }

    private static void fixGround(BlockPos block, World world){
        block = block.add(1, -3, -1);
        BlockPos currentBlock;
        for(int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                    if (world != null && !world.isRemote()) {
                        currentBlock = block.add(i, 0, -j);
                        world.setBlockState(currentBlock, Blocks.GRASS_BLOCK.getDefaultState());
                    }
                }
            }
    }
}
