package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgDetailView extends GuiScreen {
    private static final String SHOW_NNET = "SHOW_NNET";
    private static final String SHOW_INVENTORY = "SHOW_INVENTORY";
    final String CLOSE = "CLOSE";
    final String SHOW_SCORE_EVENTS = "SHOW_SCORE_EVENTS";

    final ResourceLocation texture = new ResourceLocation(ChaosCraft.MODID, "book.png");
    int guiWidth = 175;
    int guiHeight = 228;

    List<GuiButton> buttons = new ArrayList<GuiButton>();
    CCGuiButton btnClose;
    //GuiButtonTutorial arrow;
    GuiTextField textBox;

    final int BUTTON1 = 0;
    String title = "Tutorial";

    protected EntityOrganism entityOrganism;

    public void setEntityOrganism(EntityOrganism _entityOrganism){
        this.entityOrganism = _entityOrganism;
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        title = this.entityOrganism.getName();
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
            //GlStateManager.scale(2, 2, 2);
            fontRenderer.drawString(title, 0, 0, 0x000000);
        }
        GlStateManager.popMatrix();

        btnClose.drawButton(mc, mouseX, mouseY, partialTicks);
        for(GuiButton button: buttons){
            button.drawButton(mc, mouseX, mouseY, partialTicks);
        }
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
        buttonList.add(btnClose = new CCGuiButton(BUTTON1, (width / 2) - 100 / 2, height - 40, 100, 20, "Close"));
        btnClose.action = CLOSE;
        textBox = new GuiTextField(0, fontRenderer, 0, 0, 100, 20);
        updateButtons();
        super.initGui();
    }

    public void updateButtons() {

        buttons.clear();
        CCGuiButton button;
        buttonList.add(
            button = new CCGuiButton(
                    1,
                    (width / 2) - 100,
                     30,
                    100,
                    20,
                    "Score Events"
            )
        );
        button.entity = entityOrganism;
        button.action = SHOW_SCORE_EVENTS;

        buttons.add(button);


        buttonList.add(
                button = new CCGuiButton(
                        2,
                        (width / 2) - 100,
                        60,
                        100,
                        20,
                        "NNet"
                )
        );
        button.entity = entityOrganism;
        button.action = SHOW_NNET;

        buttons.add(button);


        buttonList.add(
                button = new CCGuiButton(
                        2,
                        (width / 2) - 100,
                        90,
                        100,
                        20,
                        "Inventory"
                )
        );
        button.entity = entityOrganism;
        button.action = SHOW_INVENTORY;

        buttons.add(button);

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
        CCGuiButton ccButton = (CCGuiButton) button;
        switch (ccButton.action) {
            case CLOSE:
                mc.displayGuiScreen(null);
                break;
            case(SHOW_SCORE_EVENTS):
                CCOrgScoreEventsView view = new CCOrgScoreEventsView();
                view.entityOrganism = entityOrganism;
                mc.displayGuiScreen(view);

                break;
            case(SHOW_INVENTORY):
                CCOrgInventoryView view2 = new CCOrgInventoryView(entityOrganism);
                mc.displayGuiScreen(view2);

                break;
            case(SHOW_NNET):
                CCOrgNNetView view3 = new CCOrgNNetView();
                view3.entityOrganism = entityOrganism;
                mc.displayGuiScreen(view3);

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