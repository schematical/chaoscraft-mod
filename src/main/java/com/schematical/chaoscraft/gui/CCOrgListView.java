package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgListView extends CCGuiBase {

    public CCOrgListView() {
        super("Organisms", new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png"), 175, 228);
    }

    int initializeButtons() {
        int ID = super.initializeButtons();
        //Collections.sort(ChaosCraft.organisims, (h1, h2) -> (int) (h2.entityFitnessManager.totalScore() - h1.entityFitnessManager.totalScore()));
        int buttonWidth = 100;
        int buttonHeight = 10;
        int x = guiLeft - buttonWidth;
        int y = guiTop;
        for (EntityOrganism entityOrganism : ChaosCraft.organisims) {

            buttonList.add(new CCGuiButton(
                            ID++,
                            x,
                            y,
                            buttonWidth,
                            buttonHeight,
                            entityOrganism.getOrganism().getName().substring(0, Math.min(4, entityOrganism.getOrganism().getName().length())) + " - " + entityOrganism.getOrganism().getGeneration() + " - " + entityOrganism.entityFitnessManager.totalScore(),
                            ButtonAction.VIEW_ORG_DETAIL_ACTION,
                            entityOrganism
                    )
            );
            if(y < guiTop + guiHeight) {
                y += buttonHeight;
            } else {
                x += buttonWidth;
                y = guiTop;
            }
        }

        return ID;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}