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
    final ResourceLocation texture = new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png");

    int guiWidth = 175;
    int guiHeight = 228;
    private int guiLeft;
    private int guiTop;

    public EntityOrganism entityOrganism;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1, 1, 1, 1);

        drawDefaultBackground();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        int centerX = (width / 2) - guiWidth / 2;
        int centerY = (height / 2) - guiHeight / 2;

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);

        String title = entityOrganism.getName() + " Score Events";
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

    public void initializeButtons() {
        buttonList.clear();
        int ID = 0;
        buttonList.add(
                new CCGuiButton(
                        ID++,
                        this.guiLeft + (this.guiWidth / 2) - 100 / 2,
                        this.guiTop + this.guiHeight - (20 + 10),
                        100,
                        20,
                        I18n.format(ChaosCraft.MODID+".gui.close"),
                        "CLOSE"
                )
        );

        int buttonWidth = 200;
        int buttonHeight = 15;
        for (EntityFitnessScoreEvent scoreEvent : entityOrganism.entityFitnessManager.scoreEvents) {
            buttonList.add(new CCGuiButton(
                            ID++,
                            this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                            this.guiTop + (buttonHeight) * (ID-1),
                            buttonWidth,
                            buttonHeight,
                            scoreEvent.toString(),
                            VIEW_ORG_DETAIL_ACTION,
                            entityOrganism
                    )
            );
        }

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button instanceof CCGuiButton) {
            CCGuiButton ccButton = (CCGuiButton) button;
            switch (ccButton.action) {
                case (VIEW_ORG_DETAIL_ACTION):
                    CCOrgDetailView view = new CCOrgDetailView();
                    view.setEntityOrganism(ccButton.getEntity());
                    mc.displayGuiScreen(view);
                    return;
                case "CLOSE":
                    mc.displayGuiScreen(null);
                    break;
            }
        }

        super.actionPerformed(button);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}