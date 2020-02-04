package com.schematical.chaoscraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CCGUIHelper {
    public static void drawLine(int startX, int startY, int endX, int endY, Color c, float width) {
        RenderSystem.pushMatrix();

        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.enableDepthTest();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        RenderSystem.lineWidth(width);

        bufferbuilder.pos(startX, startY, 0)
                .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha())
                .endVertex();
        bufferbuilder.pos(endX, endY, 0)
                .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha())
                .endVertex();

        tessellator.draw();

        RenderSystem.popMatrix();
    }
}
