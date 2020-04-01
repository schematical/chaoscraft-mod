package com.schematical.chaoscraft.client.gui;

import com.google.common.base.Strings;
import com.mojang.blaze3d.systems.RenderSystem;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.ai.action.ActionBuffer;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.network.packets.CCServerObserverOrgChangeEventPacket;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.ChaosTarget;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.items.ItemStackHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class ChaosObserveOverlayScreen extends AbstractGui {
    private final Minecraft mc;
    private final FontRenderer fontRenderer;
    private CCServerObserverOrgChangeEventPacket message;
    private ClientOrgManager clientOrgManager;
    private boolean displayScore = false;
    private boolean displayTarget = false;
    private boolean drawTargetLines = true;
    private boolean displayInventory = false;

    public ChaosObserveOverlayScreen(Minecraft mc) {
        this.mc = mc;
        this.fontRenderer = mc.fontRenderer;
        ClientPlayerEntity playerEntity = mc.player;


    }
    public void render(){
        if(message == null || clientOrgManager == null){
            return;
        }
        RenderSystem.pushMatrix();
        ArrayList<String> list = new ArrayList<String>();

        if(clientOrgManager == null){
            float secondsToLive = (message.expectedLifeEndTime - clientOrgManager.getEntity().world.getGameTime()) /20;
            list.add("Observing: " + message.orgNamespace);
            list.add("Org is remote");
            list.add("Last Score: " + message.score);
            list.add("Last Expected Life End: " + secondsToLive);
        }else{
            list.add("Observing: " + clientOrgManager.getCCNamespace());
            NeuralNet nNet = clientOrgManager.getEntity().getNNet();

            list.add("Score: " + clientOrgManager.getLatestScore());
            float secondsToLive = (clientOrgManager.getExpectedLifeEndTime() - clientOrgManager.getEntity().world.getGameTime()) /20;
            list.add("Expected Life End: " + secondsToLive);
            ArrayList<CCServerScoreEventPacket> scoreEvents = (ArrayList<CCServerScoreEventPacket>)clientOrgManager.getServerScoreEvents().clone();
            Collections.reverse(scoreEvents);
            if(displayScore) {
                for (CCServerScoreEventPacket serverScoreEventPacket : scoreEvents) {
                    String message = serverScoreEventPacket.fitnessRuleId +
                            " S:" + serverScoreEventPacket.score +
                            " L:" + serverScoreEventPacket.life +
                            " M:" + Math.round(serverScoreEventPacket.multiplier * 100) / 100f +
                            " T: " + serverScoreEventPacket.getAdjustedScore();
                    list.add(message);
                }
            }

        }


        for(int i = 0; i < list.size(); ++i) {
            String s = list.get(i);
            if (!Strings.isNullOrEmpty(s)) {
                int j = 9;
                int k = this.fontRenderer.getStringWidth(s);
                int x = 10;//this.mc.func_228018_at_().getScaledWidth() - 2 - k;
                int y = 2 + j * i;
                fill(x  - 1, y- 1, x + k + 1, y + j - 1, -1873784752);
                this.fontRenderer.drawString(s, (float)x, (float)y, 14737632);
            }
        }



        list.clear();
        BlockRayTraceResult rayTraceResult = clientOrgManager.getEntity().rayTraceBlocks(clientOrgManager.getEntity().REACH_DISTANCE);
        String s = "RayTrace: ";
        if(rayTraceResult == null){
            s += "null";
        }else{
            s += rayTraceResult.getType() + " ";
            BlockPos blockPos = rayTraceResult.getPos();
            s += clientOrgManager.getEntity().world.getBlockState(blockPos).getBlock().getRegistryName().toString();
            list.add(s);
            Vec3d eyePos = clientOrgManager.getEntity().getEyePosition(1f);
            list.add(rayTraceResult.getPos().getX() + ","  + rayTraceResult.getPos().getY() + "," + rayTraceResult.getPos().getZ() + "   " + Math.round(eyePos.getX()) + ", "+ Math.round(eyePos.getY()) + ", " + Math.round(eyePos.getZ()));
        }
        s = "";
        ActionBase actionBase = clientOrgManager.getActionBuffer().getCurrAction();
        if(actionBase == null){
            s += "NO ACTION";//TODO Add scanning progress
        }else {

            ActionBuffer.SimpleActionStats simpleActionStats = clientOrgManager.getActionBuffer().getSimpleActionStats(actionBase.getSimpleActionStatsKey());

            s += actionBase.toString() + " " + simpleActionStats.toString(clientOrgManager.getEntity().world);

        }
        list.add(s);
        ScanManager scanManager = clientOrgManager.getScanManager();
        s = scanManager.getState().toString() + " R:" + scanManager.getRange() + " - " + scanManager.getIndex() + "/" + scanManager.getMaxRangeIndex();
        list.add(s);
        if(displayTarget) {
            for (BiologyBase biologyBase : this.clientOrgManager.getNNet().biology.values()) {
                if (biologyBase instanceof TargetSlot) {


                    TargetSlot targetSlot = (TargetSlot) biologyBase;
                    if (targetSlot != null) {
                        s = targetSlot.toShortString();

                        s += " EP:" + Math.round(clientOrgManager.getEntity().rotationPitch);
                        if (targetSlot.hasTarget()) {
                            s += " YD:" + Math.round(targetSlot.getYawDelta());
                            s += " PD:" + Math.round(targetSlot.getPitchDelta());
                            s += " DD:" + Math.round(targetSlot.getDist());


                        }
                        list.add(s);
                    }


                }
            }
        }
        if(displayInventory) {
            s = "Held Item: " + clientOrgManager.getEntity().getHeldItem(Hand.MAIN_HAND).toString();
            list.add(s);
            ItemStackHandler itemStackHandler = clientOrgManager.getEntity().getItemHandler();
            for (int i = 0; i < itemStackHandler.getSlots(); i++) {
                ItemStack itemStack = itemStackHandler.getStackInSlot(i);
                if (!itemStack.isEmpty()) {
                    s = "Inv:" + i + " " + itemStack.getItem().getRegistryName().toString() + " x " + itemStack.getCount();
                    list.add(s);
                }
            }
        }
        int i = 1;
        for (String _s : list) {
            int j = 9;
            int k = this.fontRenderer.getStringWidth(_s);
            int x = this.mc.getMainWindow().getScaledWidth() - k;
            int y = 2 + j * i;
            fill(x - 1, y - 1, x + k + 1, y + j - 1, -1873784752);
            this.fontRenderer.drawString(_s, (float) x, (float) y, 14737632);
            i += 1;
        }

        RenderSystem.popMatrix();
    }

    public void setObservedEntity(CCServerObserverOrgChangeEventPacket message, ClientOrgManager clientOrgManager) {
        this.message = message;
        this.clientOrgManager = clientOrgManager;
    }

    public ClientOrgManager getObservedEntity() {
        return  this.clientOrgManager;
    }

    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if(this.clientOrgManager == null){
            return;
        }
        if(!displayTarget){
            return;
        }
        AxisAlignedBB toDraw2 = clientOrgManager.getEntity().getBoundingBox();
        BlockPos blockPos2 = clientOrgManager.getEntity().getPosition();
        if(
            toDraw2 != null &&
            blockPos2 != null
        ) {

            //toDraw2 = toDraw2.offset(blockPos2);
            CCGUIHelper.drawAABB(
                    event.getMatrixStack(),
                    toDraw2,
                    Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                    .2D,
                    Color.RED,
                    1f
            );
        }
        for (BiologyBase biologyBase : this.clientOrgManager.getNNet().biology.values()) {
            if(biologyBase instanceof  TargetSlot) {


                TargetSlot targetSlot = (TargetSlot) biologyBase;
                if (targetSlot != null) {

                    if (targetSlot.hasTarget()) {
                        ChaosTarget chaosTarget = targetSlot.getTarget();


                        AxisAlignedBB toDraw = chaosTarget.getTargetBoundingBox(getObservedEntity().getEntity().world);
                        BlockPos blockPos = chaosTarget.getTargetBlockPos();
                        if(
                            toDraw != null &&
                            blockPos != null
                        ) {

                            toDraw = toDraw.offset(blockPos);
                            CCGUIHelper.drawAABB(
                                    event.getMatrixStack(),
                                    toDraw,
                                     Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                                    .002D,
                                    Color.WHITE,
                                    1f
                            );
                        }

                    }

                }

            }
        }

        ActionBase actionBase = this.clientOrgManager.getActionBuffer().getCurrAction();
        if(actionBase != null){
            ChaosTarget chaosTarget = actionBase.getTarget();
            if(chaosTarget == null){
                AxisAlignedBB toDraw = chaosTarget.getTargetBoundingBox(getObservedEntity().getEntity().world);
                if(toDraw != null) {
                    toDraw = toDraw.offset(chaosTarget.getTargetBlockPos());

                    CCGUIHelper.drawAABB(
                            event.getMatrixStack(),
                            toDraw,
                            Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                            .002D,
                            Color.GREEN,
                            1f
                    );
                }

            }
        }
    }

    public void setDisplaySettings(boolean displayScore, boolean displayTarget, boolean displayInventory, boolean drawTargetLines) {
        this.displayScore = displayScore;
        this.displayTarget = displayTarget;
        this.drawTargetLines = drawTargetLines;
        this.displayInventory = displayInventory;
    }
}
