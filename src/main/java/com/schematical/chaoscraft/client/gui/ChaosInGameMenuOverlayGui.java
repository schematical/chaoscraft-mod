package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.entities.OrgEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class ChaosInGameMenuOverlayGui extends Screen {

    public ChaosInGameMenuOverlayGui() {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));

    }
    protected void init() {
        super.init();
        int y = 100;
        this.addButton(new Button(this.width / 2 - 100, y, 200, 20, I18n.format("chaoscraft.gui.mainmenu.auth"), (p_214266_1_) -> {
            ChaosAuthOverlayGui screen = new ChaosAuthOverlayGui();
            Minecraft.getInstance().displayGuiScreen(screen);
        }));
        y += 30;
        this.addButton(new Button(this.width / 2 - 100, y, 200, 20, I18n.format("chaoscraft.gui.mainmenu.orglist"), (p_214266_1_) -> {
            ChaosAuthOverlayGui screen = new ChaosAuthOverlayGui();
            Minecraft.getInstance().displayGuiScreen(screen);
        }));
        y += 30;
        this.addButton(new Button(this.width / 2 - 100, y, 200, 20, I18n.format("chaoscraft.gui.mainmenu.networkinfo"), (p_214266_1_) -> {
            ChaosNetworkInfoOverlayGui screen = new ChaosNetworkInfoOverlayGui();
            Minecraft.getInstance().displayGuiScreen(screen);
        }));
        y += 30;
    }
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);
        int i = 90;

       /* for(String s : this.field_201553_i) {
            this.drawCenteredString(this.font, s, this.width / 2, i, 16777215);
            i += 9;
        }*/
        this.drawCenteredString(this.font, "Something else goes here", this.width / 2, i, 16777215);
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
