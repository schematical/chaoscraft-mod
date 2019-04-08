package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.containers.CCOrgInventoryContainer;
import com.schematical.chaoscraft.entities.EntityOrganism;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgInventoryView extends GuiContainer {

  private static final ResourceLocation texture = new ResourceLocation(ChaosCraft.MODID,
      "container.png");
  EntityOrganism entityOrganism;

  public CCOrgInventoryView(EntityOrganism entity) {
    super(new CCOrgInventoryContainer(entity));
    xSize = 176;
    ySize = 166;
    entityOrganism = entity;
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    fontRenderer.drawString(entityOrganism.getName() + " Inventory", 5, 5, Color.darkGray.getRGB());
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
  }
}
