package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.client.gui.GuiButton;

/**
 * Created by user1a on 2/21/19.
 */
public class CCGuiButton extends GuiButton {

  protected EntityOrganism entity;
  public CCGuiBase.ButtonAction action;

  @Deprecated
  public CCGuiButton(int buttonId, int x, int y, int width, int height, String buttonText) {
    super(buttonId, x, y, width, height, buttonText);
  }

  public CCGuiButton(int buttonId, int x, int y, int width, int height, String buttonText,
      CCGuiBase.ButtonAction action) {
    this(buttonId, x, y, width, height, buttonText);
    this.action = action;
  }

  public CCGuiButton(int buttonId, int x, int y, int width, int height, String buttonText,
      CCGuiBase.ButtonAction action, EntityOrganism entity) {
    this(buttonId, x, y, width, height, buttonText, action);
    this.entity = entity;
  }

  public EntityOrganism getEntity() {
    return this.entity;
  }
}
