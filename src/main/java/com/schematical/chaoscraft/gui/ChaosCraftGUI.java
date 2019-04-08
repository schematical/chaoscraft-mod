package com.schematical.chaoscraft.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * Created by user1a on 2/15/19.
 */
@SideOnly(Side.CLIENT)
public class ChaosCraftGUI {

  protected static ArrayList<CCLine> toRenderLines = new ArrayList<CCLine>();
  protected static boolean isRendering = false;
  private static ArrayList<CCBox> toRenderBoxs = new ArrayList<CCBox>();

  public static void render(RenderWorldLastEvent event) {
    EntityPlayer player = Minecraft.getMinecraft().player;

    double partialTicks = (double) event.getPartialTicks();
    double xo = Minecraft.getMinecraft()
        .getRenderManager().viewerPosX;///*player.lastTickPosX*/ + ((player.posX - player.lastTickPosX) * partialTicks);
    double yo = Minecraft.getMinecraft()
        .getRenderManager().viewerPosY;///*player.lastTickPosY*/ + ((player.posY - player.lastTickPosY) * partialTicks);
    double zo = Minecraft.getMinecraft()
        .getRenderManager().viewerPosZ;///*player.lastTickPosZ*/ + ((player.posZ - player.lastTickPosZ) * partialTicks);

    GlStateManager.pushMatrix();

    GlStateManager.translate(-xo, -yo, -zo);

    isRendering = true;
    Iterator<CCBox> iterator = toRenderBoxs.iterator();
    while (iterator.hasNext()) {
      CCBox toRenderBox = iterator.next();
      drawBoundingBox(toRenderBox.start, toRenderBox.end, toRenderBox.color);
    }
    toRenderBoxs.clear();

    for (CCLine line : toRenderLines) {
      renderDebugLine(line);
    }
    toRenderLines.clear();
    isRendering = false;

    GlStateManager.popMatrix();
    GlStateManager.translate(0, 0, 0);
  }

  public static void drawBoundingBox(Vec3d posA, Vec3d posB, Color c) {

    GlStateManager.pushMatrix();
    GlStateManager.color(0.0F, 1.0F, 0.0F, 1f);
    GlStateManager.disableLighting();
    GlStateManager.disableTexture2D();
    GlStateManager.glLineWidth(5.0F);

    GlStateManager.color(1, 1, 1, 1);

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferBuilder = tessellator.getBuffer();
    bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);

    double dx = posB.x - posA.x;
    double dy = posB.y - posA.y;
    double dz = posB.z - posA.z;

    //BOTTOM --> A to B to C to D and back to A
    bufferBuilder.pos(posA.x, posA.y, posA.z)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
    bufferBuilder.pos(posA.x, posA.y, posA.z + dz)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
    bufferBuilder.pos(posA.x + dx, posA.y, posA.z + dz)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
    bufferBuilder.pos(posA.x + dx, posA.y, posA.z)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
    bufferBuilder.pos(posA.x, posA.y, posA.z)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A

    //TOP --> E to F to G to H and bac kto E
    bufferBuilder.pos(posA.x, posA.y + dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), 0)
        .endVertex();       //E
    bufferBuilder.pos(posA.x, posA.y + dy, posA.z + dz)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
    bufferBuilder.pos(posA.x + dx, posA.y + dy, posA.z + dz)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
    bufferBuilder.pos(posA.x + dx, posA.y + dy, posA.z)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H
    bufferBuilder.pos(posA.x, posA.y + dy, posA.z)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E

    //Vertical lines E to A, B to F, C to G and D to H
    //No need to go to E, we are already there
    bufferBuilder.pos(posA.x, posA.y, posA.z)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A

    bufferBuilder.pos(posA.x, posA.y, posA.z + dz).color(c.getRed(), c.getGreen(), c.getBlue(), 0)
        .endVertex();       //B
    bufferBuilder.pos(posA.x, posA.y + dy, posA.z + dz)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F

    bufferBuilder.pos(posA.x + dx, posA.y, posA.z + dz)
        .color(c.getRed(), c.getGreen(), c.getBlue(), 0).endVertex();    //C
    bufferBuilder.pos(posA.x + dx, posA.y + dy, posA.z + dz)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G

    bufferBuilder.pos(posA.x + dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), 0)
        .endVertex();       //D
    bufferBuilder.pos(posA.x + dx, posA.y + dy, posA.z)
        .color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H

    tessellator.draw();

    GlStateManager.enableLighting();
    GlStateManager.enableTexture2D();
    GlStateManager.popMatrix();
  }

  protected static void renderDebugLine(CCLine toRenderLine) {
    Vec3d start = toRenderLine.start;
    Vec3d end = toRenderLine.end;
    Color color = toRenderLine.color;

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
    GlStateManager.pushMatrix();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1f);
    GlStateManager.disableLighting();
    GlStateManager.disableTexture2D();
    GlStateManager.glLineWidth(5.0F);

    bufferbuilder.pos((double) start.x,
        (double) start.y,
        (double) start.z)
        .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();

    bufferbuilder.pos((double) end.x,
        (double) end.y,
        (double) end.z)
        .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();

    tessellator.draw();
    GlStateManager.enableLighting();
    GlStateManager.enableTexture2D();
    GlStateManager.popMatrix();
  }

  public static void drawDebugLine(Entity startEntity, RayTraceResult endBlock, Color color) {

    drawDebugLine(startEntity.getPosition(), endBlock.getBlockPos(), color);
  }

  public static void drawDebugLine(Entity startEntity, Entity endEntity, Color color) {

    drawDebugLine(startEntity.getPosition(), startEntity.getPosition(), color);
  }

  public static void drawDebugLine(BlockPos startBlockPos, BlockPos endPos, Color color) {
    Vec3d start = new Vec3d(startBlockPos.getX(), startBlockPos.getY(), startBlockPos.getZ());
    Vec3d end = new Vec3d(endPos.getX(), endPos.getY(), endPos.getZ());
    drawDebugLine(start, end, color);
  }

  @SideOnly(Side.CLIENT)
  public static void drawDebugLine(Vec3d start, Vec3d end, Color color) {
    if (!isRendering) {
      toRenderLines.add(new CCLine(start, end, color));
    }
  }

  @SideOnly(Side.CLIENT)
  public static void drawDebugBox(Vec3d start, Vec3d end, Color color) {
    if (!isRendering) {
      toRenderBoxs.add(new CCBox(start, end, color));
    }
  }

  protected static class CCLine {

    public Vec3d start;
    public Vec3d end;
    public Color color;

    public CCLine(Vec3d start, Vec3d end, Color color) {
      this.start = start;
      this.end = end;
      this.color = color;
    }
  }

  protected static class CCBox {

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
