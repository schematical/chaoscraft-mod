package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.client.ClientOrgManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosOrgNeuronDetailOverlayGui extends Screen {
    protected ClientOrgManager clientOrgManager;
    protected NeuronBase neuronBase;
    private TextFieldWidget valueInput;
    private float overrideVal;


    public ChaosOrgNeuronDetailOverlayGui(ClientOrgManager clientOrgManager,NeuronBase neuronBase) {
        super(new TranslationTextComponent(neuronBase.id));
        this.clientOrgManager = clientOrgManager;
        this.neuronBase = neuronBase;
    }
    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        int y = 10;
        this.valueInput = new TextFieldWidget(this.font, this.width / 2 - 100, y, 200, 20, new Float(this.neuronBase._lastValue).toString()) {
            protected String getNarrationMessage() {
                return"IDK what this is";
            }
        };
        this.valueInput.setResponder((strVal) -> {
            try {
                this.overrideVal = Float.parseFloat(strVal);
            }catch(Exception e){
                ChaosCraft.LOGGER.error(e.getMessage() + " -> "  + strVal);
            }

        });
        this.children.add(this.valueInput);
        y += 30;
        this.addButton(new Button(this.width / 2 - 100, y, 200, 20, I18n.format("chaoscraft.gui.orgneurondetail.overridevalue"), (p_214266_1_) -> {
            ChaosCraft.LOGGER.debug("Setting : " + this.overrideVal + " on " + this.neuronBase.id);
        }));
    }
    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 0, 16777215);
        this.valueInput.render(p_render_1_, p_render_2_, p_render_3_);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }


}
