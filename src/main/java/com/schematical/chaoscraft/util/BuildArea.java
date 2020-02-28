package com.schematical.chaoscraft.util;

import com.google.common.primitives.Doubles;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.biology.BlockPositionSensor;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.tileentity.BuildAreaMarkerTileEntity;
import com.schematical.chaoscraft.tileentity.ChaosTileEntity;
import jdk.nashorn.internal.runtime.logging.Loggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StructureBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.math3.*;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.transform.TransformUtils;
import org.apache.commons.math3.util.MathUtils;

import javax.annotation.Nullable;


import java.util.ArrayList;
import java.util.Random;

import static com.schematical.chaoscraft.ChaosCraft.*;
import static net.minecraft.client.Minecraft.*;


public class BuildArea{
    public Array2DRowRealMatrix[] templates = new Array2DRowRealMatrix[4];
    public Array2DRowRealMatrix[] areaMatrices = new Array2DRowRealMatrix[4];
    private BuildAreaMarkerTileEntity buildaAreaEntity;
    private ClientOrgManager currentClientOrgManager;
    private double score;
    private int blockPlacedCount = 0;

    public void getBlocks(BlockPos pos){
        pos = pos.add(1, -2, -1);
        BlockPos currentBlock;
        Block block;

        for(int k = 0; k < templates.length; k++){
            for(int i = 0; i < 12; i++) {
                for (int j = 0; j < 12; j++) {
                    if (buildaAreaEntity.getWorld() != null) {
                        currentBlock = pos.add(i, k, -j);
                        block = buildaAreaEntity.getWorld().getBlockState(currentBlock).getBlock();
                        updateMatrix(i, j, block.toString(), k);
                    }
                }
            }
        }
    }

    private int getNumberOfTemplates(){
        return templates.length;
    }
    public void assignBuildAreaTileEntity(BuildAreaMarkerTileEntity tileEntity){
        this.buildaAreaEntity = tileEntity;
    }

    public void assignCurrentOrgManager(ClientOrgManager orgManager){
        this.currentClientOrgManager = orgManager;
    }

    public ClientOrgManager getCurrentClientOrgManager(){
        return this.currentClientOrgManager;
    }
    public BuildAreaMarkerTileEntity getBuildaAreaEntity(){
        return this.buildaAreaEntity;
    }
    /*
    public void updateBlocks(BlockPos markerPos, BlockPos blockPos){
        int row = Math.abs(blockPos.getX() - markerPos.getX() - 1);
        int column = Math.abs(blockPos.getZ() - markerPos.getZ() + 1);
        if(row <= 11 && column <= 11) {
            if(getInstance().world != null){
                updateMatrix(row, column, getInstance().world.getBlockState(blockPos).getBlock().toString());
            }
        }
    }
    */

    public void createMatrices(){
        double[][] intialValues = new double[12][12];
            for(int i = 0; i < 12; i++){
                for(int j = 0; j < 12; j++){
                    intialValues[i][j] = 2.0;
                }
            }
            for(int i = 0; i < 4; i++){
                areaMatrices[i] = (Array2DRowRealMatrix)MatrixUtils.createRealMatrix(intialValues);
            }
        LOGGER.info("Area Matrix created");
    }

    public void updateMatrix(int row, int col, String block, int areaMatrixIndex){
        int maxBlockCount = 56;
        switch(block){
                case "Block{minecraft:oak_planks}":
                    areaMatrices[areaMatrixIndex].getDataRef()[row][col] = 8.0;
                    blockPlacedCount += 1;
                    if(blockPlacedCount < maxBlockCount){
                        score += 1;
                    }
                    break;
                case "Block{minecraft:air}":
                    areaMatrices[areaMatrixIndex].getDataRef()[row][col] = 2.0;
                    break;
                case "Block{minecraft:oak_door}":
                    areaMatrices[areaMatrixIndex].getDataRef()[row][col] = 10.0;
                    blockPlacedCount += 1;
                    if(blockPlacedCount < maxBlockCount){
                        score += 1;
                    }
                    break;
        }
    }

    public void buildTemplates(){
        double[][] pattern = new double[12][12];
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                pattern[i][j] = 2.0;
            }
        }

        pattern[4][4] = 8.0;
        pattern[4][5] = 8.0;
        pattern[4][6] = 8.0;
        pattern[4][7] = 8.0;

        pattern[5][4] = 8.0;
        pattern[5][5] = 2.0;
        pattern[5][6] = 2.0;
        pattern[5][7] = 8.0;

        pattern[6][4] = 8.0;
        pattern[6][5] = 2.0;
        pattern[6][6] = 2.0;
        pattern[6][7] = 8.0;

        pattern[7][4] = 8.0;
        pattern[7][5] = 10.0;
        pattern[7][6] = 8.0;
        pattern[7][7] = 8.0;

        for(int i = 0; i < 4; i++){
            templates[i] = (Array2DRowRealMatrix) MatrixUtils.createRealMatrix(pattern);
        }

        templates[3].getDataRef()[5][4] = 8.0;
        templates[3].getDataRef()[5][5] = 8.0;
        templates[3].getDataRef()[5][6] = 8.0;
        templates[3].getDataRef()[5][7] = 8.0;

        templates[3].getDataRef()[6][4] = 8.0;
        templates[3].getDataRef()[6][5] = 8.0;
        templates[3].getDataRef()[6][6] = 8.0;
        templates[3].getDataRef()[6][7] = 8.0;

        templates[3].getDataRef()[7][4] = 8.0;
        templates[3].getDataRef()[7][5] = 8.0;
        templates[3].getDataRef()[7][6] = 8.0;
        templates[3].getDataRef()[7][7] = 8.0;

        templates[2].getDataRef()[7][6] = 8.0;
    }

    private double calculateDifference(Array2DRowRealMatrix template, Array2DRowRealMatrix area){
        double diffNorm = 0.0;
        diffNorm = Math.abs(template.subtract(area).getNorm());
        return diffNorm;
    }

    public double getScore(){
        for(int i = 0; i < templates.length; i++){
            double diff = calculateDifference(templates[i], areaMatrices[i]);
            if(diff == 0){
                score += 100;
            }
            else if (diff <= 8 && diff > 0)
            {
                score += 75;
            }
        }
        return score;
    }

    public void resetScore(){
        score = 0.0;
    }

    public double getDiffNorm(){
        return templates[0].subtract(areaMatrices[0]).getNorm();
    }

    public double getAreamatrixNorm(){
        return areaMatrices[0].getNorm();
    }
}
