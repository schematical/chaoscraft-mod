package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosOrgListOverlayGui extends Screen {

    public ChaosOrgListOverlayGui() {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));

    }
    @Override
    protected void init() {
        super.init();
        int y = 0;
        int x = 0;
        for (ClientOrgManager clientOrgManager : ChaosCraft.getClient().myOrganisms.values()) {
            this.addButton(new Button(x, y, 200, 10, clientOrgManager.getCCNamespace() + "- " + clientOrgManager.getState(), (p_214266_1_) -> {
                ChaosOrgDetailOverlayGui screen = new ChaosOrgDetailOverlayGui(clientOrgManager);
                Minecraft.getInstance().displayGuiScreen(screen);
            }));
            y += 10;
            if(y > this.height - 20){
                y = 0;
                x += 200;
            }
        }

    }
    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }


}
