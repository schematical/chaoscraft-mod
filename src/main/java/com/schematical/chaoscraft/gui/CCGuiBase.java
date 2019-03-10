package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public abstract class CCGuiBase extends GuiScreen {

    enum ButtonAction {
        CLOSE,
        SHOW_NNET,
        SHOW_INVENTORY,
        SHOW_SCORE_EVENTS,
        VIEW_ORG_DETAIL_ACTION,
        VIEW_NEURON_DETAIL_ACTION,
        VIEW_BIOLOGY_LIST_ACTION,
        VIEW_BIOLOGY_DETAIL_ACTION
    }

    final String title;
    final int guiWidth, guiHeight;
    final ResourceLocation texture;

    int guiLeft;
    int guiTop;

    public CCGuiBase(String title, ResourceLocation texture, int guiWidth, int guiHeight) {
        this.title = title;
        this.texture = texture;
        this.guiWidth = guiWidth;
        this.guiHeight = guiHeight;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1, 1, 1, 1);

        drawDefaultBackground();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        int centerX = (width / 2) - guiWidth / 2;
        int centerY = (height / 2) - guiHeight / 2;

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);

        fontRenderer.drawString(title, this.guiLeft + (this.guiWidth - this.fontRenderer.getStringWidth(title)) / 2, this.guiTop + 10, 0x000000);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.guiWidth) / 2;
        this.guiTop = (this.height - this.guiHeight) / 2;

        initializeButtons();

        super.initGui();
    }

    int initializeButtons() {
        buttonList.clear();
        int ID = 0;
        int buttonWidth = 100;
        int buttonHeight = 20;

        buttonList.add(
                new CCGuiButton(
                        ID++,
                        this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                        this.guiTop + this.guiHeight - (buttonHeight + 10),
                        buttonWidth,
                        buttonHeight,
                        I18n.format(ChaosCraft.MODID + ".gui.close"),
                        ButtonAction.CLOSE
                )
        );


        return ID;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        CCGuiButton ccButton = (CCGuiButton) button;
        GuiScreen view = null;
        switch (ccButton.action) {
            case CLOSE:
                mc.displayGuiScreen(null);

                break;
            case SHOW_SCORE_EVENTS:
                view = new CCOrgScoreEventsView(ccButton.entity);
                mc.displayGuiScreen(view);

                break;
            case SHOW_INVENTORY:
                view = new CCOrgInventoryView(ccButton.entity);
                mc.displayGuiScreen(view);

                break;
            case SHOW_NNET:
                view = new CCOrgNNetView(ccButton.entity);
                mc.displayGuiScreen(view);

                break;
            case VIEW_ORG_DETAIL_ACTION:
                view = new CCOrgDetailView(ccButton.entity);
                mc.displayGuiScreen(view);
                return;
            case VIEW_BIOLOGY_LIST_ACTION:
                view = new CCOrgBiologyListView(ccButton.entity);
                mc.displayGuiScreen(view);
                return;
            case VIEW_BIOLOGY_DETAIL_ACTION:
                view = new CCOrgBiologyDetailView(ccButton.entity, ((CCOrgBiologyListView.CCBiologyDetailButton) ccButton).biologyBase);
                mc.displayGuiScreen(view);
                return;
            default:
                ChaosCraft.logger.warn("Unknown button action! " + ccButton.action);
                super.actionPerformed(button);
        }
    }
}