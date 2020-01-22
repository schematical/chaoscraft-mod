package com.schematical.chaoscraft.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.entities.OrgEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

@OnlyIn(Dist.CLIENT)
public class ChaosNNetViewOverlayGui extends Screen {

    private OrgEntity orgEntity;
    public ChaosNNetViewOverlayGui(OrgEntity orgEntity) {
        super(new TranslationTextComponent(orgEntity.getCCNamespace()));
        this.orgEntity = orgEntity;
    }
    protected void init() {
        super.init();
        ArrayList<NeuronBase> inputs = new ArrayList<NeuronBase>();
        ArrayList<NeuronBase> outputs = new ArrayList<NeuronBase>();
        ArrayList<NeuronBase> middles = new ArrayList<NeuronBase>();
        for (NeuronBase neuronBase : orgEntity.getNNet().neurons.values()) {
           switch(neuronBase._base_type()){
               case(com.schematical.chaoscraft.Enum.INPUT):
                   inputs.add(neuronBase);
                   break;
               case(com.schematical.chaoscraft.Enum.OUTPUT):
                   outputs.add(neuronBase);
                   break;
               case(com.schematical.chaoscraft.Enum.MIDDLE):
                   middles.add(neuronBase);
                   break;

           }
        }
        int baseHeight = (this.height - 20);
        int inputsY = baseHeight/(inputs.size() + 1);
        int outputsY = baseHeight/(outputs.size() + 1);


        int i = 0;
        for (NeuronBase neuronBase : inputs) {
            this.addButton(new ChaosNeuronButton( neuronBase, this, i, inputsY  * i + 10));
            i += 1;
        }

        i = 0;
        for (NeuronBase neuronBase : outputs) {
            this.addButton(new ChaosNeuronButton( neuronBase, this, i, outputsY  * i + 10));
            i += 1;
        }
        if(middles.size() > 0){
            int middlesX = baseHeight/middles.size();
        }
        /*this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, "I am a button", (p_212983_1_) -> {
            ChaosCraft.LOGGER.info("TEST CLICK");
        }));*/

       /* this.field_201553_i.clear();
        this.field_201553_i.addAll(this.font.listFormattedStringToWidth(this.field_201550_f.getFormattedText(), this.width - 50));*/
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
