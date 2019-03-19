package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgBiologyDetailView extends CCGuiBase {

    protected final EntityOrganism entityOrganism;
    protected BiologyBase biologyBase;
    public CCOrgBiologyDetailView(EntityOrganism entityOrganism, BiologyBase biologyBase) {
        super(entityOrganism.getName(), new ResourceLocation(ChaosCraft.MODID, "textures/gui/book.png"), 175, 228);
        this.entityOrganism = entityOrganism;
        this.biologyBase = biologyBase;
    }

    /*@Override
    int initializeButtons() {
        buttonList.clear();

        int ID = super.initializeButtons();
        int buttonWidth = 100;
        int buttonHeight = 20;
        Iterator<BiologyBase> biologysIterator = this.entityOrganism.getNNet().biology.values().iterator();
        while(biologysIterator.hasNext()) {
            BiologyBase biologyBase = biologysIterator.next();
            buttonList.add(
                new CCBiologyDetailButton(
                    ID++,
                    this.guiLeft + (this.guiWidth / 2) - buttonWidth / 2,
                    this.guiTop + (buttonHeight + 10) * ID,
                    buttonWidth,
                    buttonHeight,
                    biologyBase.toString(), //I18n.format(ChaosCraft.MODID + ".gui.open.score_events"),
                    ButtonAction.VIEW_BIOLOGY_DETAIL_ACTION,
                    entityOrganism,
                    biologyBase
                )
            );
        }



        return ID;
    }*/


    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}