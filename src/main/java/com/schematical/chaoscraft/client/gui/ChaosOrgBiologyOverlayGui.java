package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.client.ClientOrgManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosOrgBiologyOverlayGui extends Screen {

    private ClientOrgManager clientOrgManager;
    public ChaosOrgBiologyOverlayGui(ClientOrgManager clientOrgManager) {
        super(new TranslationTextComponent(clientOrgManager.getCCNamespace()));
        this.clientOrgManager = clientOrgManager;
    }
    protected void init() {
        super.init();
        int y = 0;
        int x = 0;
        for (BiologyBase biologyBase : clientOrgManager.getEntity().getNNet().biology.values()) {
            this.addButton(new ChaosOrgBiologyButton(biologyBase, this, x, y));
            y += 10;
            if(y > this.height - 20){
                y = 0;
                x += 200;
            }
        }

    }
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        for (Widget button : this.buttons) {
            if(button instanceof ChaosOrgBiologyButton) {
                ((ChaosOrgBiologyButton)button).renderRefresh();
            }
        }
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }


    public void minAll() {
        for (Widget button : buttons) {
            ((ChaosOrgBiologyButton) button).min();
        }
    }
}
