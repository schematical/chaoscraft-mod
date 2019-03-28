package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.ai.NeuronDep;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest;
import com.schematical.chaosnet.model.GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult;
import com.schematical.chaosnet.model.TaxonomicRank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

/**
 * Created by user1a on 2/21/19.
 */
public class CCSpeciesListView extends CCGuiBase {

    protected  List<TaxonomicRank> species;
    protected List<GuiButtonToggle> toggleButtons = new ArrayList<GuiButtonToggle>();
    static class CCSpeciesListViewButton extends CCGuiButton{
        public TaxonomicRank tRank;
        public boolean showStats = true;
        public CCSpeciesListViewButton(int buttonId, int x, int y, int widthIn, int heightIn, TaxonomicRank tRank, ButtonAction action) {
            super(buttonId, x, y, widthIn, heightIn, tRank.getName(), action);
            this.tRank = tRank;
        }
        public CCSpeciesListViewButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
            super(buttonId, x, y, widthIn, heightIn, buttonText);
        }
        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            this.displayString = tRank.getName() + " " + tRank.getAge() + " " + (this.showStats ? "ENABLED" : "DISABLED");

            super.drawButton(mc, mouseX, mouseY, partialTicks);
        }
    }
    public CCSpeciesListView() {
        super("Species", new ResourceLocation(ChaosCraft.MODID, "textures/gui/nn_background.png"), 256, 256);


        GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest request = new GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest();
        request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
        request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
        request.setSession(ChaosCraft.config.sessionNamespace);
        GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult result = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomSessionsSessionSpecies(request);
        species  = result.getTaxonomicRankCollection();


    }
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        CCGuiButton ccButton = (CCGuiButton) button;
        GuiScreen view = null;
        switch (ccButton.action) {
            case TOGGLE_SPECIES_VISIBILITY_ACTION:

                CCSpeciesListViewButton speciesButton = (CCSpeciesListViewButton) ccButton;
                speciesButton.showStats = !speciesButton.showStats;
                break;
           default:
                super.actionPerformed(button);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1, 1, 1, 1);

        drawDefaultBackground();

        GlStateManager.pushMatrix();
        float x = (float) width / guiWidth;
        float y = (float) height / guiHeight;
        GlStateManager.scale(x, y, 1.0);
        this.mc.renderEngine.bindTexture(texture);
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, guiWidth, guiHeight, guiWidth, guiHeight);
        GlStateManager.popMatrix();

        fontRenderer.drawString(title, (width - this.fontRenderer.getStringWidth(title)) / 2, this.guiTop, 0x000000);

        for (GuiButtonToggle button : toggleButtons) {

        }


        for (GuiButton button : buttonList) {
            button.drawButton(mc, mouseX, mouseY, partialTicks);
        }
        /*for (GuiButton button : buttonList) {
            if (button instanceof CCGUINeuronDisplayButton) {
                List<String> text = new ArrayList<String>();
                text.add(((CCGUINeuronDisplayButton) button).neuron.toLongString());
                drawTooltip(text, mouseX, mouseY, button.x, button.y, button.width, button.height);
            }
        }*/

    }

    public void drawTooltip(List<String> lines, int mouseX, int mouseY, int posX, int posY, int width, int height) {
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
            drawHoveringText(lines, mouseX, mouseY);
        }
    }

    @Override
    public void initGui() {
        this.guiLeft = 50;
        this.guiTop = 10;

        initializeButtons();
    }

    int initializeButtons() {
        buttonList.clear();
        int ID = 0;

        for(TaxonomicRank tRank: species) {

            buttonList.add(
                new CCSpeciesListViewButton(
                    ID++,
                   this.guiLeft,
                   this.guiTop + (ID * 15),
                    100,
                    15,
                    tRank,
                    ButtonAction.TOGGLE_SPECIES_VISIBILITY_ACTION
                )
            );
        }


        /*buttonList.add(
                new CCGuiButton(
                        ID++,
                        ((this.width) / 2) - buttonWidth / 2,
                        this.guiTop + (this.height - guiTop) - (buttonHeight + 15),
                        buttonWidth,
                        buttonHeight,
                        I18n.format(ChaosCraft.MODID + ".gui.close"),
                        ButtonAction.CLOSE
                )
        );
*/

        return ID;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawLine(int startX, int startY, int endX, int endY, int r, int g, int b, int a) {
        GlStateManager.disableTexture2D();
        GlStateManager.color(r / 255f, g / 255f, b / 255f, a / 255f);

        GlStateManager.glLineWidth(5);
        GlStateManager.glBegin(GL_LINE_STRIP);

        GlStateManager.glVertex3f(startX, startY, 0);
        GlStateManager.glVertex3f(endX, endY, 0);
        GlStateManager.glEnd();
        GlStateManager.enableTexture2D();
    }




}