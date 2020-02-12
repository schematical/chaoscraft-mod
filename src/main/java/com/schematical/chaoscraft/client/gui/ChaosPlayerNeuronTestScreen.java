package com.schematical.chaoscraft.client.gui;

import com.google.common.base.Strings;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

public class ChaosPlayerNeuronTestScreen extends AbstractGui {
    private final Minecraft mc;
    private final FontRenderer fontRenderer;
    public ChaosPlayerNeuronTestScreen(Minecraft mc) {
        this.mc = mc;
        this.fontRenderer = mc.fontRenderer;
    }
    public void render(){
        RenderSystem.pushMatrix();
        ArrayList<String> list = new ArrayList<String>();
list.add("It worked Jimmmmmmmmmmy");
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
