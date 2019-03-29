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
        int toggleB = 80;

        buttonList.add(new CCGuiButton(
                0,
                this.guiLeft + (this.guiWidth / 2) - toggleB / 2,
                0,
                toggleB,
                buttonHeight,
                "Toggle Mining",
                ButtonAction.TOGGLE_MINING_CHAT

        ));
        buttonList.add(new CCGuiButton(
                0,
                toggleB + this.guiLeft + (this.guiWidth / 2) - toggleB / 2,
                0,
                toggleB,
                buttonHeight,
                "Toggle Placing",
                ButtonAction.TOGGLE_PLACING_CHAT

        ));
        buttonList.add(new CCGuiButton(
                0,
                (this.guiLeft + (this.guiWidth / 2) - toggleB / 2) - toggleB,
                0,
                toggleB,
                buttonHeight,
                "Toggle Crafting",
                ButtonAction.TOGGLE_PLACING_CHAT

        ));

        for (EntityOrganism entityOrganism : ChaosCraft.organisims) {
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