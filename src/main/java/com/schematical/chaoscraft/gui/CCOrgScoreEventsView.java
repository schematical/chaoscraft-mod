package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgScoreEventsView extends GuiScreen {
    final String VIEW_ORG_DETAIL_ACTION = "VIEW_ORG_DETAIL_ACTION";
    final ResourceLocation texture = new ResourceLocation(ChaosCraft.MODID, "book.png");
    int guiWidth = 175;
    int guiHeight = 228;
    List<GuiButton> buttons = new ArrayList<GuiButton>();

    GuiButton button1;
    //GuiButtonTutorial arrow;
    GuiTextField textBox;

    final int BUTTON1 = 0, ARROW = 1;
    String title = "Score Events";

    public EntityOrganism entityOrganism;

    /*public void setEntityOrganism(EntityOrganism _entityOrganism){
        this.entityOrganism = _entityOrganism;
    }*/
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
title = entityOrganism.getName() + " Score Events";

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        int centerX = (width / 2) - guiWidth / 2;
        int centerY = (height / 2) - guiHeight / 2;
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
        for(GuiButton button: buttons){
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
        textBox.drawTextBox();
        List<String> text = new ArrayList<String>();
        text.add(I18n.format("gui.tooltip"));
        text.add(I18n.format("gui.tooltip2", mc.world.provider.getDimension()));
        //text.add(icon.getDisplayName());
        drawTooltip(text, mouseX, mouseY, centerX, centerY, 16 * 2, 16 * 2);
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
        updateButtons();
        super.initGui();
    }

    public void updateButtons() {
        int btnCount = 1;
        buttons.clear();
        for(EntityFitnessScoreEvent scoreEvent: entityOrganism.entityFitnessManager.scoreEvents){
            CCGuiButton button;
            buttonList.add(button = new CCGuiButton(
                    btnCount,
                    (width / 2) - 100,
                    btnCount * 15 + 30,
                    200,
                    15,
                    scoreEvent.toString()
                )
            );
            button.entity = entityOrganism;
            button.action = VIEW_ORG_DETAIL_ACTION;
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
                case(VIEW_ORG_DETAIL_ACTION):
                    CCOrgDetailView view = new CCOrgDetailView();
                    view.setEntityOrganism(ccButton.getEntity());
                    mc.displayGuiScreen(view);
                    return;
            }
        }
        switch (button.id) {
            case BUTTON1:
                mc.displayGuiScreen(null);
                break;
        }
        //updateButtons();
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

}