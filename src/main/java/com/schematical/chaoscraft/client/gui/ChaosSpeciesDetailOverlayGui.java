package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientOrgDebugStateChangeRequestPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaosnet.model.GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest;
import com.schematical.chaosnet.model.GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult;
import com.schematical.chaosnet.model.HistoricalScoresElement;
import com.schematical.chaosnet.model.TaxonomicRank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosSpeciesDetailOverlayGui extends Screen {
    protected TaxonomicRank taxonomicRank;
    public ChaosSpeciesDetailOverlayGui( TaxonomicRank taxonomicRank) {
        super(new TranslationTextComponent(taxonomicRank.getNamespace()));
        this.taxonomicRank = taxonomicRank;

    }
    @Override
    protected void init() {
        super.init();



    }
    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        int y = 0;
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, y, 16777215);
        y += 10;
        this.drawCenteredString(this.font, "Age: " + this.taxonomicRank.getAge(), this.width / 2, y, 16777215);
        y += 10;
        this.drawCenteredString(this.font, "Curr Score: " + this.taxonomicRank.getCurrScore(), this.width / 2, y, 16777215);
        y += 10;
        this.drawCenteredString(this.font, "High Score: " + this.taxonomicRank.getHighScore(), this.width / 2, y, 16777215);
        y += 10;
        this.drawCenteredString(this.font, "Children Spawned This Gen: " + this.taxonomicRank.getChildrenSpawnedThisGen(), this.width / 2, y, 16777215);
        y += 10;
        this.drawCenteredString(this.font, "Children Reported This Gen: " + this.taxonomicRank.getChildrenReportedThisGen(), this.width / 2, y, 16777215);
        y += 20;
        for (HistoricalScoresElement historicalScore : this.taxonomicRank.getHistoricalScores()) {

            String message = "Avg: " + historicalScore.getAge() +
                    " Gen Avg: " + historicalScore.getGenAvg() +
                    " Top Avg: " + historicalScore.getTopAvg() +
                    " Top Max: " + historicalScore.getTopMax();
            this.drawCenteredString(this.font, message, this.width / 2, y, 16777215);
            y += 10;
        }

        //this.drawCenteredString(this.font, "Something else goes here", this.width / 2, i, 16777215);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    public void minAll() {
        for (Widget button : buttons) {
            ((ChaosNeuronButton) button).min();
        }
    }
    public void drawLine(int startX, int startY, int endX, int endY, int r, int g, int b, int a) {

    }
}
