package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgListView extends CCGuiBase {

    public CCOrgListView() {
        super("Organisms", new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png"), 175, 228);
    }

    int initializeButtons() {
        int ID = super.initializeButtons();

        int buttonWidth = 150;
        int buttonHeight = 10;
        for (EntityOrganism entityOrganism : ChaosCraft.client.myOrganisims) {
            buttonList.add(new CCGuiButton(
                            ID++,
                            this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                            this.guiTop + (buttonHeight) * (ID - 1),
                            buttonWidth,
                            buttonHeight,
                            entityOrganism.getName() + " - " + entityOrganism.entityFitnessManager.totalScore(),
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