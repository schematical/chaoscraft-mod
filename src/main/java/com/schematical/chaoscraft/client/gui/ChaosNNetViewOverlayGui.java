package com.schematical.chaoscraft.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.ai.NeuronDep;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

@OnlyIn(Dist.CLIENT)
public class ChaosNNetViewOverlayGui extends Screen {

    private ClientOrgManager clientOrgManager;
    private int neuronsLength;

    public ChaosNNetViewOverlayGui(ClientOrgManager clientOrgManager) {
        super(new TranslationTextComponent(clientOrgManager.getCCNamespace()));
        this.clientOrgManager = clientOrgManager;
    }
    public ClientOrgManager getClientOrgManager(){
        return clientOrgManager;
    }
    @Override
    protected void init() {
        super.init();
        updateNeurons();
    }
    public void attachDependants(ChaosNeuronButton neuronButton){
        for (NeuronDep dependency : neuronButton.getNeuronBase().dependencies) {
            boolean btnFound = false;
            for (Widget button : this.buttons) {
                if(button instanceof ChaosNeuronButton){
                    ChaosNeuronButton chaosNeuronButton = (ChaosNeuronButton) button;
                    if(chaosNeuronButton.getNeuronBase().id.equals(dependency.depNeuronId)){
                        neuronButton.dependancies.put(dependency.depNeuronId, chaosNeuronButton);
                        btnFound = true;
                    }
                }
            }
            if(!btnFound){
                ChaosCraft.LOGGER.error("Error Client: Could not find dependant for: " + dependency.depNeuronId);
            }
        }
    }
    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        if(this.clientOrgManager.getNNet().neurons.values().size() != neuronsLength){
            updateNeurons();
        }

        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);

        for (Widget button : this.buttons) {
            if(button instanceof ChaosNeuronButton) {
                ChaosNeuronButton chaosNeuronButton = ((ChaosNeuronButton)button);
                chaosNeuronButton.renderRefresh();
                for (NeuronDep dependency : chaosNeuronButton.getNeuronBase().dependencies) {
                    ChaosNeuronButton depBtn = chaosNeuronButton.dependancies.get(dependency.depNeuronId);
                    if(depBtn == null){
                        throw new ChaosNetException("Missing btn for: " + dependency.depNeuronId);
                    }
                    Color c = Color.GREEN;
                    if(dependency.weight < 0){
                        c = Color.RED;
                    }
                    //NeuronBase neuron = clientOrgManager.getEntity().getNNet().neurons.get(dependency.depNeuronId);

                    float a = Math.abs(dependency.getCurrentValue());//dependency.depNeuron.getCurrentValue());//dependency._lastValue);

                    if(a < .2f){
                        a = .2f;
                    }else if (a > 1){
                        a = 1;
                    }
                    c = new Color(
                        c.getRed()/255f,
                        c.getGreen()/255f,
                        c.getBlue()/255f,
                            a
                    );
                    float width = Math.abs(dependency.weight) + .2f;
                    CCGUIHelper.drawLine(
                            chaosNeuronButton.x + chaosNeuronButton.getWidth() / 2,
                            chaosNeuronButton.y  + chaosNeuronButton.getHeight() / 2,
                            depBtn.x + depBtn.getWidth() /2,
                            depBtn.y  + depBtn.getHeight() /2,
                            c,
                            width
                    );
                }
            }
        }


        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    private void updateNeurons() {
        Iterator<IGuiEventListener> iterator = this.children.iterator();
        while(iterator.hasNext()){
            IGuiEventListener btn = iterator.next();
            if(this.buttons.contains(btn)){
                iterator.remove();
                this.buttons.remove(btn);
            }
        }
        ArrayList<NeuronBase> inputs = new ArrayList<NeuronBase>();
        ArrayList<NeuronBase> outputs = new ArrayList<NeuronBase>();
        ArrayList<NeuronBase> middles = new ArrayList<NeuronBase>();
        for (NeuronBase neuronBase : clientOrgManager.getEntity().getNNet().neurons.values()) {
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
        int baseHeight = (this.height - 10);
        int inputsY = baseHeight/(inputs.size() + 1);
        int outputsY = baseHeight/(outputs.size() + 1);


        int i = 0;
        for (NeuronBase neuronBase : inputs) {
            this.addButton(new ChaosNeuronButton( neuronBase, this, i, inputsY  * i + 10));
            i += 1;
        }


        if(middles.size() > 0){
            int middlesY = baseHeight/middles.size();
            i = 0;
            for (NeuronBase neuronBase : middles) {
                ChaosNeuronButton neuronButton = new ChaosNeuronButton( neuronBase, this, i, middlesY  * i + 10);
                this.addButton(neuronButton);



                i += 1;
            }
        }

        i = 0;
        for (NeuronBase neuronBase : outputs) {
            this.addButton(new ChaosNeuronButton( neuronBase, this, i, outputsY  * i + 10));
            i += 1;
        }
        /*this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, "I am a button", (p_212983_1_) -> {
            ChaosCraft.LOGGER.info("TEST CLICK");
        }));*/

       /* this.field_201553_i.clear();
        this.field_201553_i.addAll(this.font.listFormattedStringToWidth(this.field_201550_f.getFormattedText(), this.width - 50));*/

        for (Widget button : this.buttons) {
            if(button instanceof ChaosNeuronButton){
                attachDependants((ChaosNeuronButton) button);
            }
        }
        neuronsLength = this.clientOrgManager.getNNet().neurons.values().size();
    }

    public void minAll() {
        for (Widget button : buttons) {
            ((ChaosNeuronButton) button).min();
        }
    }

}
