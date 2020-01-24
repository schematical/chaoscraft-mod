package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.client.ClientOrgManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosOrgDetailOverlayGui extends Screen {
    protected ClientOrgManager clientOrgManager;
    public ChaosOrgDetailOverlayGui(ClientOrgManager clientOrgManager) {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));
        this.clientOrgManager = clientOrgManager;

    }
    protected void init() {
        super.init();
        int y = 0;
        this.addButton(new Button(this.width / 2 - 100, y, 200, 20, I18n.format("chaoscraft.gui.orgdetail.nnet"), (p_214266_1_) -> {
            ChaosNNetViewOverlayGui screen = new ChaosNNetViewOverlayGui(this.clientOrgManager);
            Minecraft.getInstance().displayGuiScreen(screen);
        }));
        y += 20;
        this.addButton(new Button(this.width / 2 - 100, y, 200, 20, I18n.format("chaoscraft.gui.orgdetail.biology"), (p_214266_1_) -> {
            ChaosOrgBiologyOverlayGui screen = new ChaosOrgBiologyOverlayGui(clientOrgManager);
            Minecraft.getInstance().displayGuiScreen(screen);
        }));
        y += 20;
       /* this.addButton(new Button(this.width / 2 - 100, y, 200, 20, I18n.format("chaoscraft.gui.orgdetail.x"), (p_214266_1_) -> {
            ChaosNetworkInfoOverlayGui screen = new ChaosNetworkInfoOverlayGui();
            Minecraft.getInstance().displayGuiScreen(screen);
        }));*/
        y += 20;
    }
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);
        int i = 90;


        //this.drawCenteredString(this.font, "Something else goes here", this.width / 2, i, 16777215);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    public void minAll() {
        for (Widget button : buttons) {
            ((ChaosNeuronButton) button).min();
        }
    }
    public void drawLine(int startX, int startY, int endX, int endY, int r, int g, int b, int a) {

    }
}
