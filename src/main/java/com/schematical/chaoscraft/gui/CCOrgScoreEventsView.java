package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgScoreEventsView extends CCGuiBase {
    public final EntityOrganism entityOrganism;

    public CCOrgScoreEventsView(EntityOrganism entityOrganism) {
        super(entityOrganism.getName(), new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png"), 175, 228);
        this.entityOrganism = entityOrganism;
    }


    @Override
    int initializeButtons() {
        int ID = super.initializeButtons();

        int buttonWidth = 200;
        int buttonHeight = 15;
        for (EntityFitnessScoreEvent scoreEvent : entityOrganism.entityFitnessManager.scoreEvents) {
            buttonList.add(
                new CCGuiButton(
                    ID++,
                    this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                    this.guiTop + (buttonHeight) * (ID-1),
                    buttonWidth,
                    buttonHeight,
                    scoreEvent.toString(),
                    ButtonAction.VIEW_ORG_DETAIL_ACTION,
                    entityOrganism
                )
            );
        }

        return ID;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}