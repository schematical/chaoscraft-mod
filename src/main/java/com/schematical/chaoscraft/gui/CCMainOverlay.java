package com.schematical.chaoscraft.gui;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by user1a on 2/21/19.
 */
@SideOnly(Side.CLIENT)
public class CCMainOverlay extends Gui {
    GuiButton button1;
    final int BUTTON1 = 0;
    //private final ResourceLocation bar = new ResourceLocation(ChaosCraft.MODID, "textures/gui/hpbar.png");
    private final int tex_width = 102, tex_height = 8, bar_width = 100, bar_height = 6;
    public CCMainOverlay(){
        super();
        button1 = new GuiButton(BUTTON1, 100, 40, 100, 20, "CC");

    }

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            Minecraft mc = Minecraft.getMinecraft();
            /* mc.renderEngine.bindTexture(bar);
            float oneUnit = (float)bar_width / mc.player.getMaxHealth();
            int currentWidth = (int)(oneUnit * mc.player.getHealth());
            System.out.println((int)mc.player.getHealth());
            drawTexturedModalRect(0, 0, 0, 0, tex_width, tex_height);
            drawTexturedModalRect(1, 0, 1, tex_height, currentWidth, tex_height);*/

            //button1.drawButton(mc, 0, 0, event.getPartialTicks());
        }
    }

}