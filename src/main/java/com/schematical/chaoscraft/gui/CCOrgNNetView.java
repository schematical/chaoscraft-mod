package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgNNetView extends CCGuiBase {
    final String VIEW_NEURON_DETAIL_ACTION = "VIEW_NEURON_DETAIL_ACTION";

    final EntityOrganism entityOrganism;

    public CCOrgNNetView(EntityOrganism entityOrganism) {
        super(entityOrganism.getName() + " NNet " + entityOrganism.getNNet().neurons.size(), new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png"), 175, 228);

        this.entityOrganism = entityOrganism;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        initializeButtons();

        for (GuiButton button : buttonList) {
            if (button instanceof CCGUINeuronDisplayButton) {
                List<String> text = new ArrayList<String>();
                text.add(((CCGUINeuronDisplayButton) button).neuron.toString());
                drawTooltip(text, mouseX, mouseY, button.x, button.y, button.width, button.height);
            }
        }

    }

    public void drawTooltip(List<String> lines, int mouseX, int mouseY, int posX, int posY, int width, int height) {
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
            drawHoveringText(lines, mouseX, mouseY);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    int initializeButtons() {
        int ID = super.initializeButtons();

        int outputY = guiTop;
        int inputY = guiTop;
        int middleY = guiTop;

        int r, g, b, a;
        for (NeuronBase neuron : entityOrganism.getNNet().neurons.values()) {
            int x = 0;
            int y = 0;
            int buttonWidth = 30;
            int buttonHeight = 10;

            switch (neuron._base_type()) {
                case ("INPUT"):
                    x = guiLeft + guiWidth;
                    y = inputY;
                    inputY += buttonHeight;


                    break;
                case ("OUTPUT"):
                    x = guiLeft;
                    y = outputY;
                    outputY += buttonHeight;

                    break;
                case ("MIDDLE"):
                    x = guiLeft + guiWidth/2;
                    y = middleY;
                    middleY += buttonHeight;

                    break;
                default:
                    throw new ChaosNetException("Invalid Neuron: " + neuron._base_type());

            }


            for (int i = 0; i < neuron.dependencies.size(); i++) {
                if (neuron.dependencies.get(i).weight > 0) {
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


                drawLine(x, y, neuron.dependencies.get(i).depNeuron.x, neuron.dependencies.get(i).depNeuron.y, r, g, b, a);
            }
            neuron.setcoords(x, y);

            buttonList.add(new CCGUINeuronDisplayButton(
                            ID++,
                            x - buttonWidth/2,
                            y - buttonHeight/2,
                            buttonWidth,
                            buttonHeight,
                            "test",
                            entityOrganism,
                            neuron

                    )
            );
        }

        return ID;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawLine(int startX, int startY, int endX, int endY, int r, int g, int b, int a) {
        GlStateManager.disableTexture2D();
        GlStateManager.color(r, g, b, a);

        GlStateManager.glLineWidth(5);
        GlStateManager.glBegin(GL_LINE_STRIP);

        GlStateManager.glVertex3f(startX, startY, 20);
        GlStateManager.glVertex3f(endX, endY, 20);
        GlStateManager.glEnd();
    }


    public class CCGUINeuronDisplayButton extends CCGuiButton {
        public final NeuronBase neuron;

        public CCGUINeuronDisplayButton(int buttonId, int x, int y, int width, int height, String buttonText, EntityOrganism entityOrganism, NeuronBase neuron) {
            super(buttonId, x, y, width, height, buttonText, ButtonAction.VIEW_NEURON_DETAIL_ACTION, entityOrganism);
            this.neuron = neuron;
        }

    }

}