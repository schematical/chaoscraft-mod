package com.schematical.chaoscraft.gui;

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


/**
 * Created by user1a on 2/15/19.
 */
@SideOnly(Side.CLIENT)
public class ChaosCraftGUI {
    public static void drawDebugLine(Entity startEntity, RayTraceResult endBlock){

        drawDebugLine(startEntity.getPosition(), endBlock.getBlockPos());
    }
    public static void drawDebugLine(Entity startEntity, Entity endEntity){

        drawDebugLine(startEntity.getPosition(), startEntity.getPosition());
    }
    public static void drawDebugLine(BlockPos startBlockPos, BlockPos endPos){
        Vec3d start = new Vec3d(startBlockPos.getX(), startBlockPos.getY(), startBlockPos.getZ());
        Vec3d end = start.addVector(0, 10, 0);
        drawDebugLine(start, end);
    }
    public static void drawDebugLine(Vec3d start, Vec3d end){

        Vec3d posDiff = end.subtract(start);
        GlStateManager.pushMatrix();
        GlStateManager.glLineWidth(1F);
        GlStateManager.disableTexture2D();
        BufferBuilder bb = Tessellator.getInstance().getBuffer();
        bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        bb.pos(start.x, start.y, start.z).color(1, 1, 1, 1F).endVertex();
        bb.pos(posDiff.x, posDiff.y, posDiff.z).color(1, 1, 1, 1F).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

}
