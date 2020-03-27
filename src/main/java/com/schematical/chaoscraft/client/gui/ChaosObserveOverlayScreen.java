package com.schematical.chaoscraft.client.gui;

import com.google.common.base.Strings;
import com.mojang.blaze3d.systems.RenderSystem;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.network.packets.CCServerObserverOrgChangeEventPacket;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Collections;

public class ChaosObserveOverlayScreen extends AbstractGui {
    private final Minecraft mc;
    private final FontRenderer fontRenderer;
    private CCServerObserverOrgChangeEventPacket message;
    private ClientOrgManager clientOrgManager;
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
            for (CCServerScoreEventPacket serverScoreEventPacket : scoreEvents) {
                String message = serverScoreEventPacket.fitnessRuleId +
                        " S:" + serverScoreEventPacket.score +
                        " L:" + serverScoreEventPacket.life +
                        " M:" + Math.round(serverScoreEventPacket.multiplier * 100) / 100f +
                        " T: " + serverScoreEventPacket.getAdjustedScore();
                list.add(message);
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
            s += actionBase.toString();
        }
        list.add(s);

        for (BiologyBase biologyBase : this.clientOrgManager.getNNet().biology.values()) {
            if(biologyBase instanceof  TargetSlot) {

               /* LookAtTargetOutput lookAtTargetOutput = null;
                for (NeuronBase neuronBase : this.clientOrgManager.getNNet().neurons.values()) {
                    if(neuronBase instanceof  LookAtTargetOutput){
                        lookAtTargetOutput = (LookAtTargetOutput) neuronBase;
                    }
                }
                if(lookAtTargetOutput != null) {


                    TargetSlot targetSlot = (TargetSlot) biologyBase;
                    if (targetSlot != null) {
                        s = targetSlot.toShortString();
                       *//* s += " LATO:" + lookAtTargetOutput.getPrettyCurrValue();
                        s += " EP:" + Math.round(clientOrgManager.getEntity().rotationPitch);
                        if (targetSlot.hasTarget()) {
                            s += " YD:" + Math.round(targetSlot.getYawDelta());
                            s += " PD:" + Math.round(targetSlot.getPitchDelta());
                            s += " DD:" + Math.round(targetSlot.getDist());
                        }*//*
                       list.add(s);
                    }
                }*/
            }
        }
        s = "Held Item: " + clientOrgManager.getEntity().getHeldItem(Hand.MAIN_HAND).toString();
        list.add(s);
        ItemStackHandler itemStackHandler = clientOrgManager.getEntity().getItemHandler();
        for(int i = 0; i < itemStackHandler.getSlots(); i++){
            ItemStack itemStack = itemStackHandler.getStackInSlot(i);
            if(!itemStack.isEmpty()){
                s = "Inv:" + i + " " + itemStack.getItem().getRegistryName().toString() + " x " + itemStack.getCount();
                list.add(s);
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
}
