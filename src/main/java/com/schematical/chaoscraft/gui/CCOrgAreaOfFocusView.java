package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.biology.AreaOfFocus;
import com.schematical.chaoscraft.containers.CCOrgAreaOfFocusContainer;
import com.schematical.chaoscraft.containers.CCOrgInventoryContainer;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

/**
 * Created by user1a on 2/21/19.
 */
public class CCOrgAreaOfFocusView extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(ChaosCraft.MODID, "container.png");
    EntityOrganism entityOrganism;
    public CCOrgAreaOfFocusView(EntityOrganism entity) {
        super(new CCOrgAreaOfFocusContainer(entity));
        xSize = 176;
        ySize = 166;
        entityOrganism = entity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        NeuralNet neuralNet = entityOrganism.getNNet();
        if(neuralNet == null){
            return;
        }
        AreaOfFocus areaOfFocus = (AreaOfFocus)neuralNet.getBiology("AreaOfFocus_0");
        Vec3d focusPoint = areaOfFocus.getFocusPoint();
        fontRenderer.drawString(
           Math.round(focusPoint.x) + ", " +
                Math.round(focusPoint.y) + ", " +
                Math.round(focusPoint.z),
                5, 5, Color.darkGray.getRGB()

        );

        fontRenderer.drawString(
           Math.round(entityOrganism.getPosition().getX()) + ", " +
                Math.round(entityOrganism.getPosition().getY()) + ", " +
                Math.round(entityOrganism.getPosition().getZ()),
                5, 10, Color.darkGray.getRGB()
        );
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
