package com.schematical.chaoscraft.client.gui;

import com.google.common.base.Strings;
import com.mojang.blaze3d.systems.RenderSystem;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.ai.inputs.BaseTargetInputNeuron;
import com.schematical.chaoscraft.ai.inputs.TargetDistanceInput;
import com.schematical.chaoscraft.ai.inputs.TargetPitchInput;
import com.schematical.chaoscraft.ai.inputs.TargetYawInput;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.network.packets.CCServerObserverOrgChangeEventPacket;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;

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
        RenderSystem.popMatrix();
    }

    public void setObservedEntity(CCServerObserverOrgChangeEventPacket message, ClientOrgManager clientOrgManager) {
        this.message = message;
        this.clientOrgManager = clientOrgManager;
    }
}
