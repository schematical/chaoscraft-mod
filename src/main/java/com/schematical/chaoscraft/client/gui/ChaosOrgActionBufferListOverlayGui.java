package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class ChaosOrgActionBufferListOverlayGui extends Screen {

    private ClientOrgManager clientOrgManager;
    public ChaosOrgActionBufferListOverlayGui(ClientOrgManager clientOrgManager) {
        super(new TranslationTextComponent(clientOrgManager.getCCNamespace()));
        this.clientOrgManager = clientOrgManager;
    }
    @Override
    protected void init() {
        super.init();



    }
    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        int y = 0;
        float secondsToLive = (clientOrgManager.getExpectedLifeEndTime() - clientOrgManager.getEntity().world.getGameTime()) /20;
        this.drawCenteredString(
                this.font,
                "Base: " + clientOrgManager.getServerScoreEventTotal() + " Life: " + secondsToLive,
                this.width / 2,
                y,
                16777215
        );
        y += 20;
        ActionBase currAction = clientOrgManager.getActionBuffer().getCurrAction();
        String message = "No Action";
        if(currAction != null) {
            message = "Curr: " + currAction.toString();
        }
        this.drawCenteredString(
                this.font,
                message,
                this.width / 2,
                y,
                16777215
        );

        y += 20;
        ArrayList<ActionBase> actionBases = (ArrayList<ActionBase>)clientOrgManager.getActionBuffer().getRecentActions().clone();
        Collections.reverse(actionBases);
        for (ActionBase actionBase : actionBases) {
             message = actionBase.toString();
            this.drawCenteredString(
                    this.font,
                    message,
                    this.width / 2,
                    y,
                    16777215
            );
            y += 10;

        }
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }


}
