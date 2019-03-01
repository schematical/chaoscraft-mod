package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgDetailView extends GuiScreen {

    //Button actions
    private static final String SHOW_NNET = "SHOW_NNET";
    private static final String SHOW_INVENTORY = "SHOW_INVENTORY";
    private static final String CLOSE = "CLOSE";
    private static final String SHOW_SCORE_EVENTS = "SHOW_SCORE_EVENTS";

    private final ResourceLocation texture = new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png");
    int guiWidth = 175;
    int guiHeight = 228;
    private int guiLeft;
    private int guiTop;

    protected EntityOrganism entityOrganism;

    public void setEntityOrganism(EntityOrganism _entityOrganism) {
        this.entityOrganism = _entityOrganism;
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

        String title = this.entityOrganism.getName();
        fontRenderer.drawString(title, this.guiLeft + (this.guiWidth - this.fontRenderer.getStringWidth(title))/2, this.guiTop + 10, 0x000000);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.guiWidth) / 2;
        this.guiTop = (this.height - this.guiHeight) / 2;
        initializeButtons();
        super.initGui();
    }

    private void initializeButtons() {
        buttonList.clear();

        int buttonWidth = 100;
        int buttonHeight = 20;

        int ID = 0;

        buttonList.add(
                new CCGuiButton(
                        ID++,
                        this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                        this.guiTop + this.guiHeight - (buttonHeight + 10),
                        buttonWidth,
                        buttonHeight,
                        I18n.format(ChaosCraft.MODID+".gui.close"),
                        CLOSE
                )
        );

        buttonList.add(
                new CCGuiButton(
                        ID++,
                        this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                        this.guiTop + (buttonHeight + 10) * ID,
                        buttonWidth,
                        buttonHeight,
                        I18n.format(ChaosCraft.MODID+".gui.open.score_events"),
                        SHOW_SCORE_EVENTS,
                        entityOrganism
                )
        );


        buttonList.add(
                new CCGuiButton(
                        ID++,
                        this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                        this.guiTop + (buttonHeight + 10) * ID,
                        buttonWidth,
                        buttonHeight,
                        I18n.format(ChaosCraft.MODID+".gui.open.nnet"),
                        SHOW_NNET,
                        entityOrganism
                )
        );


        buttonList.add(
                new CCGuiButton(
                        ID++,
                        this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                        this.guiTop + (buttonHeight + 10) * ID,
                        buttonWidth,
                        buttonHeight,
                        I18n.format(ChaosCraft.MODID+".gui.open.inventory"),
                        SHOW_INVENTORY,
                        entityOrganism
                )
        );
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        CCGuiButton ccButton = (CCGuiButton) button;
        switch (ccButton.action) {
            case CLOSE:
                mc.displayGuiScreen(null);
                break;
            case SHOW_SCORE_EVENTS:
                CCOrgScoreEventsView view = new CCOrgScoreEventsView();
                view.entityOrganism = entityOrganism;
                mc.displayGuiScreen(view);

                break;
            case SHOW_INVENTORY:
                CCOrgInventoryView view2 = new CCOrgInventoryView(entityOrganism);
                mc.displayGuiScreen(view2);

                break;
            case SHOW_NNET:
                CCOrgNNetView view3 = new CCOrgNNetView();
                view3.entityOrganism = entityOrganism;
                mc.displayGuiScreen(view3);

                break;

        }

        super.actionPerformed(button);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}