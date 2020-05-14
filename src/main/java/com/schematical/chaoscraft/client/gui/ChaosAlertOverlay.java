package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaosnet.model.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@OnlyIn(Dist.CLIENT)
public class ChaosAlertOverlay extends Screen {



    private TranslationTextComponent infoMessage;
    private Button button;
    private boolean nextClose = false;

    public ChaosAlertOverlay(TranslationTextComponent textComponent) {
        super(new TranslationTextComponent("chaoscraft.gui.alert.title"));
        infoMessage = textComponent;
    }
    public ChaosAlertOverlay(String text) {
        super(new TranslationTextComponent("chaoscraft.gui.alert.title"));
        infoMessage = new TranslationTextComponent(text);
    }

    @Override
    protected void init() {
        super.init();

        this.minecraft.keyboardListener.enableRepeatEvents(true);
        button = new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, I18n.format("chaoscraft.gui.close"), (p_214266_1_) -> {

                this.close();
                return;


        });
        this.addButton(button);


    }


    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);


        String[] parts = this.infoMessage.getFormattedText().split("\n");
        int y = 0;
        for (String part : parts) {
            this.drawCenteredString(this.font, part, this.width / 2, 100 + y, 16777215);
            y += 10;
        }
       super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    private void close() {
        this.minecraft.displayGuiScreen((Screen)null);
    }


}
