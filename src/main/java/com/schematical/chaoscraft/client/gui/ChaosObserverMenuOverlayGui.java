package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientObserveStateChangePacket;
import com.schematical.chaoscraft.server.ChaosCraftServerPlayerInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosObserverMenuOverlayGui extends Screen {

    private CheckboxButton displayScoreCheckbox;
    private CheckboxButton drawTargetLines;
    private CheckboxButton displayTargetSlots;
    private CheckboxButton displayInventory;

    public ChaosObserverMenuOverlayGui() {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));

    }

    @Override
    protected void init() {
        super.init();
        int y = 0;
        this.displayScoreCheckbox = new CheckboxButton(this.width / 2 - 155 + 80, 76 + y, 150, 20,  I18n.format("chaoscraft.gui.observer-menu.display-scores"), false);

        this.addButton(this.displayScoreCheckbox);
        y += 20;
        this.displayTargetSlots = new CheckboxButton(this.width / 2 - 155 + 80, 76 + y, 150, 20,  I18n.format("chaoscraft.gui.observer-menu.display-target-slots"), false);

        this.addButton(this.displayTargetSlots);
        y += 20;
        this.displayInventory = new CheckboxButton(this.width / 2 - 155 + 80, 76 + y, 150, 20,  I18n.format("chaoscraft.gui.observer-menu.display-inventory"), false);

        this.addButton(this.displayInventory);



        y += 20;
        this.drawTargetLines = new CheckboxButton(this.width / 2 - 155 + 80, 76 + y, 150, 20,  I18n.format("chaoscraft.gui.observer-menu.draw-target-lines"), false);

        this.addButton(this.drawTargetLines);



        y += 20;
        this.addButton(new Button(this.width / 2 - 155 + 80, 124 + y, 150, 20, I18n.format("chaoscraft.gui.save"), (p_212991_1_) -> {
            //Save settings
            ChaosCraft.getClient().getObserveOverlayScreen().setDisplaySettings(
                    this.displayScoreCheckbox.isChecked(),
                    this.displayTargetSlots.isChecked(),
                    this.displayInventory.isChecked(),
                    this.drawTargetLines.isChecked()
            );
            Minecraft.getInstance().displayGuiScreen(null);
        }));

        y += 20;
    }

    @Override
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


}
