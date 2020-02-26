package com.schematical.chaoscraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import com.schematical.chaoscraft.util.BuildArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DistNormTracker extends AbstractGui {
    private FontRenderer fontrenderer;
    public DistNormTracker(){
        this.fontrenderer = Minecraft.getInstance().fontRenderer;
    }

    public void render(){
        RenderSystem.pushMatrix();
        if(ChaosBlocks.markerBlocks.size() > 0){
            /*
            for(int i = 0; i < 12; i++){
                for(int j = 0; j < 12; j++){
                    fontrenderer.drawString(""+ChaosCraft.buildAreas.get(0).areaMatrices[0].getData()[i][j],15 * j, i * 15, 14737632);
                }
            }
            */


            for(int i = 0; i < ChaosBlocks.markerBlocks.size(); i++)
            {
                fontrenderer.drawString("Score :" + ChaosCraft.buildAreas.get(i).getScore(),  25 + (i * i * 45), 25, 14737632);
                fontrenderer.drawString("Diff " + (i + 1) + " " + ChaosCraft.buildAreas.get(i).getDiffNorm(), 15, (45 * i * i) + 15, 14737632);
                fontrenderer.drawString("Norm of Area " + (i + 1) + " " + ChaosCraft.buildAreas.get(i).getAreamatrixNorm(), 15, (35 * i * i) + 35, 14737632);
            }
        }
        RenderSystem.popMatrix();
    }

}
