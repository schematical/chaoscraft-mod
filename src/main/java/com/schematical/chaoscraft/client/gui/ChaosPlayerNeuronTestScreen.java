package com.schematical.chaoscraft.client.gui;

import com.google.common.base.Strings;
import com.mojang.blaze3d.systems.RenderSystem;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.ai.inputs.BaseTargetInputNeuron;
import com.schematical.chaoscraft.ai.inputs.TargetDistanceInput;
import com.schematical.chaoscraft.ai.inputs.TargetPitchInput;
import com.schematical.chaoscraft.ai.inputs.TargetYawInput;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class ChaosPlayerNeuronTestScreen extends AbstractGui {
    private final Minecraft mc;
    private final FontRenderer fontRenderer;
    private ClientPlayerEntity playerEntity;
    private ArrayList<NeuronBase> debugNeurons = new ArrayList<NeuronBase>();
    public ChaosPlayerNeuronTestScreen(Minecraft mc) {
        this.mc = mc;
        this.fontRenderer = mc.fontRenderer;
        playerEntity = mc.player;
        BaseTargetInputNeuron neuronBase = new TargetDistanceInput();
        neuronBase.setDebugEntity(playerEntity);
        neuronBase.attributeId = CCAttributeId.ENTITY_ID;
        neuronBase.attributeValue = "minecraft:chicken";
        debugNeurons.add(neuronBase);

        neuronBase = new TargetPitchInput();
        neuronBase.setDebugEntity(playerEntity);
        neuronBase.attributeId = CCAttributeId.ENTITY_ID;
        neuronBase.attributeValue = "minecraft:chicken";
        debugNeurons.add(neuronBase);

        neuronBase = new TargetYawInput();
        neuronBase.setDebugEntity(playerEntity);
        neuronBase.attributeId = CCAttributeId.ENTITY_ID;
        neuronBase.attributeValue = "minecraft:chicken";
        debugNeurons.add(neuronBase);

    }
    public void render(){
        RenderSystem.pushMatrix();
        ArrayList<String> list = new ArrayList<String>();
        for (NeuronBase debugNeuron : debugNeurons) {
            debugNeuron.reset();
            debugNeuron.evaluate();
            list.add(debugNeuron.toLongString());
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
}
