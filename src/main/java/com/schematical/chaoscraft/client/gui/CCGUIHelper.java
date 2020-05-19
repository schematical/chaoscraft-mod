package com.schematical.chaoscraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class CCGUIHelper {

    static EntityRendererManager renderManager = null;
    static Tessellator tessellator = null;
    static BufferBuilder buffer = null;
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


    static void glColor(Color color, float alpha) {
        float[] colorComponents = color.getColorComponents(null);
        RenderSystem.color4f(colorComponents[0], colorComponents[1], colorComponents[2], alpha);
    }

    static void startLines(Color color, float alpha, float lineWidth, boolean ignoreDepth) {
        RenderSystem.enableBlend();
        RenderSystem.disableLighting();
        RenderSystem.blendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
        glColor(color, alpha);
        RenderSystem.lineWidth(lineWidth);
        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);

        if (ignoreDepth) {
            RenderSystem.disableDepthTest();
        }
    }

    static void startLines(Color color, float lineWidth, boolean ignoreDepth) {
        startLines(color, .4f, lineWidth, ignoreDepth);
    }

    static void endLines(boolean ignoredDepth) {
        if (ignoredDepth) {
            RenderSystem.enableDepthTest();
        }

        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.enableLighting();
        RenderSystem.disableBlend();
    }
    static void drawAABB(MatrixStack stack, AxisAlignedBB aabb, Vec3d viewerPos, Color color, float alpha) {

            if(buffer ==null){
            Minecraft mc =  Minecraft.getInstance();
            renderManager = mc.getRenderManager();
            tessellator =Tessellator.getInstance();
            buffer =  tessellator.getBuffer();
        }

        AxisAlignedBB toDraw = aabb.offset(
                -viewerPos.getX(),//renderManager.renderPosX(),
                -viewerPos.getY(),//renderManager.renderPosY(),
                -viewerPos.getZ()//renderManager.renderPosZ()
        );

        MatrixStack.Entry last = stack.getLast();
        Matrix4f matrix4f = last.getPositionMatrix();//getMatrix();//.();
        buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        // bottom

        float[] c = color.getColorComponents(null);
        RenderSystem.lineWidth(2);
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.minY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.minY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.minY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.minY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.minY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.minY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.minY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.minY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        // top
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.maxY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.maxY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.maxY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.maxY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.maxY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.maxY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.maxY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.maxY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        // corners
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.minY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.maxY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.minY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.maxY, (float) toDraw.minZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.minY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.maxX, (float) toDraw.maxY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.minY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) toDraw.minX, (float) toDraw.maxY, (float) toDraw.maxZ).color(c[0],c[1],c[2], alpha).endVertex();
        tessellator.draw();
    }
    public static void drawLine2d(MatrixStack stack, Vec3d startPos, Vec3d endPos, Vec3d viewerPos, Color color, float alpha) {

        if(buffer ==null){
            Minecraft mc =  Minecraft.getInstance();
            renderManager = mc.getRenderManager();
            tessellator =Tessellator.getInstance();
            buffer =  tessellator.getBuffer();
        }

        startPos = startPos.add(
                -viewerPos.getX(),//renderManager.renderPosX(),
                -viewerPos.getY(),//renderManager.renderPosY(),
                -viewerPos.getZ()//renderManager.renderPosZ()
        );
        endPos = endPos.add(
                -viewerPos.getX(),//renderManager.renderPosX(),
                -viewerPos.getY(),//renderManager.renderPosY(),
                -viewerPos.getZ()//renderManager.renderPosZ()
        );

        MatrixStack.Entry last = stack.getLast();
        Matrix4f matrix4f = last.getPositionMatrix();//getMatrix();//.();
        buffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        // bottom

        float[] c = color.getColorComponents(null);
        RenderSystem.lineWidth(2);
        buffer.pos(matrix4f, (float) startPos.getX(), (float) startPos.getY(), (float) startPos.getZ()).color(c[0],c[1],c[2], alpha).endVertex();
        buffer.pos(matrix4f, (float) endPos.getX(), (float) endPos.getY(), (float) endPos.getZ()).color(c[0],c[1],c[2], alpha).endVertex();

        tessellator.draw();
    }

    public static void drawAABB(MatrixStack stack, AxisAlignedBB aabb, Vec3d viewerPos, double expand, Color color, float alpha) {
        drawAABB(stack, aabb.grow(expand, expand, expand),  viewerPos, color, alpha);
    }
}
