package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;


/**
 * Created by user1a on 2/15/19.
 */
@SideOnly(Side.CLIENT)
public class ChaosCraftGUI {
    protected static ArrayList<CCLine> toRenderLines = new ArrayList<CCLine>();
    protected static boolean isRendering = false;
    private static ArrayList<CCBox> toRenderBoxs = new ArrayList<CCBox>();

    public static void render(){
        isRendering = true;
        for(CCBox toRenderBox: toRenderBoxs){
            drawBoundingBox(null, toRenderBox.start, toRenderBox.end, true, 10f);
        }
        toRenderBoxs.clear();
        isRendering = false;
    }
    public static void drawBoundingBox(Vec3d player_pos, Vec3d posA, Vec3d posB, boolean smooth, float width) {

        GlStateManager.pushMatrix();
        GlStateManager.color(0.0F, 1.0F, 0.0F, 1f);
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(5.0F);

        GlStateManager.translate(
            (posA.x + posB.x)/2,
            (posA.y + posB.y)/2,
            (posA.z + posB.z)/2
        );

        Color c = new Color(255, 0, 0, 150);
        GlStateManager.color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());


        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);

        double dx = Math.abs(posA.x - posB.x);
        double dy = Math.abs(posA.y - posB.y);
        double dz = Math.abs(posA.z - posB.z);

        //AB
        bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
        bufferBuilder.pos(posA.x, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
        //BC
        bufferBuilder.pos(posA.x, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
        bufferBuilder.pos(posA.x+dx, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
        //CD
        bufferBuilder.pos(posA.x+dx, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
        bufferBuilder.pos(posA.x+dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
        //DA
        bufferBuilder.pos(posA.x+dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
        bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
        //EF
        bufferBuilder.pos(posA.x, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E
        bufferBuilder.pos(posA.x, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
        //FG
        bufferBuilder.pos(posA.x, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
        bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
        //GH
        bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
        bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H
        //HE
        bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H
        bufferBuilder.pos(posA.x, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E
        //AE
        bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
        bufferBuilder.pos(posA.x, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E
        //BF
        bufferBuilder.pos(posA.x, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
        bufferBuilder.pos(posA.x, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
        //CG
        bufferBuilder.pos(posA.x+dx, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
        bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
        //DH
        bufferBuilder.pos(posA.x+dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
        bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H

        tessellator.draw();

        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    protected static void renderDebugLine(CCLine toRenderLine){
        Vec3d start = toRenderLine.start;
        Vec3d end = toRenderLine.end;
        Color color = toRenderLine.color;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        GlStateManager.pushMatrix();
        GlStateManager.color(0.0F, 1.0F, 0.0F, 1f);
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(5.0F);

        bufferbuilder.pos((double) start.x,
                (double) start.y,
                (double) start.z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), 1f/*color.getAlpha()*/).endVertex();

        bufferbuilder.pos((double) end.x,
                (double) end.y,
                (double) end.z)
                .color(color.getRed(), color.getGreen(), color.getBlue(), 1f/*color.getAlpha()*/).endVertex();

        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static void drawDebugLine(Entity startEntity, RayTraceResult endBlock){

        drawDebugLine(startEntity.getPosition(), endBlock.getBlockPos());
    }
    public static void drawDebugLine(Entity startEntity, Entity endEntity){

        drawDebugLine(startEntity.getPosition(), startEntity.getPosition());
    }
    public static void drawDebugLine(BlockPos startBlockPos, BlockPos endPos){
        Vec3d start = new Vec3d(startBlockPos.getX(), startBlockPos.getY(), startBlockPos.getZ());
        Vec3d end = start.add(0, 10, 0);
        drawDebugLine(start, end, Color.RED);
    }
    @SideOnly(Side.CLIENT)
    public static void drawDebugLine(Vec3d start, Vec3d end, Color color){
        if(!isRendering) {
            toRenderLines.add(new CCLine(start, end, color));
        }
    }
    @SideOnly(Side.CLIENT)
    public static void drawDebugBox(Vec3d start, Vec3d end, Color color){
        if(!isRendering) {
            toRenderBoxs.add(new CCBox(start, end, color));
        }
    }
    protected static class CCLine{
        public Vec3d start;
        public Vec3d end;
        public Color color;

        public CCLine(Vec3d start, Vec3d end, Color color) {
            this.start = start;
            this.end = end;
            this.color = color;
        }
    }
    protected static class CCBox{
        public Vec3d start;
        public Vec3d end;
        public Color color;

        public CCBox(Vec3d start, Vec3d end, Color color) {
            this.start = start;
            this.end = end;
            this.color = color;
        }
    }


}
