package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;

/**
 * Created by user1a on 2/21/19.
 */
public class CCGuiButton extends GuiButton {
    protected  EntityOrganism entity;
    public String action;
    public CCGuiButton(int buttonId, int x, int y, int width, int height, String buttonText) {
        super(buttonId, x, y, width, height, buttonText);
    }
    public void setEntity(EntityOrganism _entity){
        this.entity = _entity;
    }
    public EntityOrganism getEntity(){
        return this.entity;
    }
}
