package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.NeuronBase;

import com.schematical.chaoscraft.ai.NeuronDep;
import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;

import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Quaternion;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgNNetView extends GuiScreen {
    final String VIEW_NEURON_DETAIL_ACTION = "VIEW_NEURON_DETAIL_ACTION";
    final ResourceLocation texture = new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png");
    int guiWidth = 175;
    int guiHeight = 228;
    List<CCGUINeuronDisplayButton> buttons = new ArrayList<CCGUINeuronDisplayButton>();

    GuiButton button1;
    //GuiButtonTutorial arrow;
    GuiTextField textBox;

    final int BUTTON1 = 0, ARROW = 1;
    String title = "NNet";

    int centerX;
    int centerY;

    public EntityOrganism entityOrganism;

    /*public void setEntityOrganism(EntityOrganism _entityOrganism){
        this.entityOrganism = _entityOrganism;
    }*/
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        drawDefaultBackground();
        title = entityOrganism.getName() + " NNet " + entityOrganism.getNNet().neurons.size();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        centerX = (width / 2) - guiWidth / 2;
        centerY = (height / 2) - guiHeight / 2;

        //drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
        //drawString(fontRendererObj, "Tutorial", centerX, centerY, 0x6028ff);
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.color(1, 1, 1, 1);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
        }
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate((width / 2) - fontRenderer.getStringWidth(title), centerY + 10, 0);

            fontRenderer.drawString(title, 0, 0, 0x000000);
        }
        GlStateManager.popMatrix();

        button1.drawButton(mc, mouseX, mouseY, partialTicks);
        for(CCGUINeuronDisplayButton button: buttons){
            button.drawButton(mc, mouseX, mouseY, partialTicks);
        }
        //arrow.drawButton(mc, mouseX, mouseY);
        /*ItemStack icon = new ItemStack(Blocks.OBSIDIAN);
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(centerX, centerY, 0);
            GlStateManager.scale(2, 2, 2);
            mc.getRenderItem().renderItemAndEffectIntoGUI(icon, 0, 0);
        }
        GlStateManager.popMatrix();*/
        //textBox.drawTextBox();




        updateButtons();
        for(CCGUINeuronDisplayButton button: buttons) {
            List<String> text = new ArrayList<String>();
            text.add(button.neuron.toString());
            drawTooltip(text, mouseX, mouseY, button.x, button.y, button.width, button.height);
        }

    }

    public void drawTooltip(List<String> lines, int mouseX, int mouseY, int posX, int posY, int width, int height) {
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
            drawHoveringText(lines, mouseX, mouseY);
        }
    }

    @Override
    public void initGui() {
        buttonList.clear();
        buttonList.add(button1 = new GuiButton(BUTTON1, (width / 2) - 100 / 2, height - 40, 100, 20, "Close"));
        //buttonList.add(arrow = new GuiButtonTutorial(ARROW, 100, 100));

        textBox = new GuiTextField(0, fontRenderer, 0, 0, 100, 20);

        super.initGui();
    }

    public void updateButtons() {
        int btnCount = 1;
        buttons.clear();
        int outputY = centerY + 10;
        int inputY = centerY + 10;
        int middleY = centerY + 10;
        NeuralNet net;
        int r, g, b, a;
        for(NeuronBase neuron: entityOrganism.getNNet().neurons.values()){
            CCGUINeuronDisplayButton button;
            int x = 0;
            int y = 0;
            int buttonWidth = 5;
            int buttonHeight = 10;

            switch(neuron._base_type()){
                case("INPUT"):
                    x = centerX + 1;
                    y = inputY;
                    buttonWidth = 5;
                    buttonHeight = 10;
                    inputY += buttonHeight;



                    break;
                case("OUTPUT"):
                    x = centerX + guiWidth - 5;
                    y = outputY;
                    buttonWidth = 5;
                    buttonHeight = 10;
                    outputY += buttonHeight;

                    break;
                case("MIDDLE"):
                    x = width/2;
                    y = middleY;
                    buttonWidth = 5;
                    buttonHeight = 10;
                    middleY += buttonHeight;

                    break;
                default:
                    throw new ChaosNetException("Invalid Neuron: " + neuron._base_type());

            }


            for(int i = 0; i < neuron.dependencies.size(); i++){
                    if(neuron.dependencies.get(i).weight > 0) {
                        r = 255;
                        g = 0;
                        b = 0;
                        a = 255;
                    } else {
                        r = 0;
                        g = 255;
                        b = 0;
                        a = 255;
                    }


                drawLine(x + buttonWidth/2, y + buttonHeight/2, neuron.dependencies.get(i).depNeuron.x + buttonWidth/2, neuron.dependencies.get(i).depNeuron.y+buttonHeight/2, r, g, b, a);
            }
            neuron.setcoords(x, y);

            buttonList.add(button = new CCGUINeuronDisplayButton(
                            btnCount,
                            x,
                            y,
                    buttonWidth,
                    buttonHeight,
                    ""

                    )
            );
            button.neuron = neuron;
            button.entity = entityOrganism;
            button.action = CCGuiBase.ButtonAction.VIEW_NEURON_DETAIL_ACTION;
            btnCount += 1;
            buttons.add(button);


        }


    }

    public void updateTextBoxes() {
        if (!textBox.getText().isEmpty()) {
            if (!textBox.isFocused()) {
                title = textBox.getText();
            }
        }
        updateButtons();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button instanceof  CCGuiButton){
            CCGuiButton ccButton = (CCGuiButton) button;
            switch (ccButton.action){
                case VIEW_NEURON_DETAIL_ACTION:
                    CCOrgDetailView view = new CCOrgDetailView(ccButton.entity);
                    mc.displayGuiScreen(view);
                    return;
            }
        }
        switch (button.id) {
            case BUTTON1:
                mc.displayGuiScreen(null);
                break;
        }
        //initializeButtons();
        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        textBox.textboxKeyTyped(typedChar, keyCode);
        updateTextBoxes();
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        textBox.mouseClicked(mouseX, mouseY, mouseButton);
        updateTextBoxes();
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawLine(int startX, int startY, int endX, int endY, int r, int g, int b, int a) {
        GlStateManager.color(r, g, b, a);
        glLineWidth(5);
        GlStateManager.glBegin(GL_LINE_STRIP);
        glVertex2f(startX, startY);
        glVertex2f(endX, endY);
        GlStateManager.glEnd();
    }


    public class CCGUINeuronDisplayButton extends CCGuiButton{
        public NeuronBase neuron;
        public CCGUINeuronDisplayButton(int buttonId, int x, int y, int width, int height, String buttonText) {
            super(buttonId, x, y, width, height, buttonText);
        }
        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {

            //ChaosCraft.logger.info("CCGUINeuronDisplayButton.drawButton " + this.displayString);
            super.drawButton(mc, mouseX,mouseY,partialTicks);

        }

    }

}