package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgDetailView extends CCGuiBase {

    protected final EntityOrganism entityOrganism;

    public CCOrgDetailView(EntityOrganism entityOrganism) {
        super(entityOrganism.getName(), new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png"), 175, 228);
        this.entityOrganism = entityOrganism;
    }

    @Override
    int initializeButtons() {
        buttonList.clear();

        int ID = super.initializeButtons();
        int buttonWidth = 100;
        int buttonHeight = 20;
        int buttonsGap = 5;

        buttonList.add(
                new CCGuiButton(
                        ID++,
                        this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                        this.guiTop + (buttonHeight + buttonsGap) * ID,
                        buttonWidth,
                        buttonHeight,
                        I18n.format(ChaosCraft.MODID + ".gui.open.score_events"),
                        ButtonAction.SHOW_SCORE_EVENTS,
                        entityOrganism
                )
        );


        buttonList.add(
                new CCGuiButton(
                        ID++,
                        this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                        this.guiTop + (buttonHeight + buttonsGap) * ID,
                        buttonWidth,
                        buttonHeight,
                        I18n.format(ChaosCraft.MODID + ".gui.open.nnet"),
                        ButtonAction.SHOW_NNET,
                        entityOrganism
                )
        );


        buttonList.add(
                new CCGuiButton(
                        ID++,
                        this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                        this.guiTop + (buttonHeight + buttonsGap) * ID,
                        buttonWidth,
                        buttonHeight,
                        I18n.format(ChaosCraft.MODID + ".gui.open.inventory"),
                        ButtonAction.SHOW_INVENTORY,
                        entityOrganism
                )
        );

        buttonList.add(
            new CCGuiButton(
                ID++,
                this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                this.guiTop + (buttonHeight + buttonsGap) * ID,
                buttonWidth,
                buttonHeight,
                I18n.format(ChaosCraft.MODID + ".gui.open.biology"),
                ButtonAction.VIEW_BIOLOGY_LIST_ACTION,
                entityOrganism
            )
        );

        buttonList.add(
            new CCGuiButton(
                ID++,
                this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                this.guiTop + (buttonHeight + buttonsGap) * ID,
                buttonWidth,
                buttonHeight,
                I18n.format(ChaosCraft.MODID + ".gui.open.observe"),
                ButtonAction.OBSERVE_ORG,
                entityOrganism
            )
        );

        return ID;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}