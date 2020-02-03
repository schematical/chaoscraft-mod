package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest;
import com.schematical.chaosnet.model.GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult;
import com.schematical.chaosnet.model.TaxonomicRank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@OnlyIn(Dist.CLIENT)
public class ChaosSpeciesListOverlayGui extends Screen {

    public ChaosSpeciesListOverlayGui() {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));


    }
    @Override
    protected void init() {
        super.init();
        try{
            GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest request = new GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesRequest();
            request.setUsername(ChaosCraft.getClient().getTrainingRoomUsernameNamespace());
            request.setTrainingroom(ChaosCraft.getClient().getTrainingRoomNamespace());
            request.setSession(ChaosCraft.getClient().getSessionNamespace());
            GetUsernameTrainingroomsTrainingroomSessionsSessionSpeciesResult response = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomSessionsSessionSpecies(request);
            int y = 0;
            for (TaxonomicRank taxonomicRank : response.getTaxonomicRankCollection()) {

                this.addButton(new Button(this.width / 2 - 100, y, 200, 10, taxonomicRank.getNamespace() + " - " + taxonomicRank.getCurrScore() + ":"  + taxonomicRank.getHighScore(), (p_214266_1_) -> {
                    ChaosSpeciesDetailOverlayGui screen = new ChaosSpeciesDetailOverlayGui(taxonomicRank);
                    Minecraft.getInstance().displayGuiScreen(screen);
                }));
                y += 10;
            }
        }catch(ChaosNetException exception){
            //logger.error(exeception.getMessage());
            ChaosCraft.getClient().consecutiveErrorCount += 1;

            int statusCode = exception.sdkHttpMetadata().httpStatusCode();
            switch(statusCode){
                case(400):

                    //ChaosCraft.getClient().repair();
                    break;
                case(401):
                    ChaosCraft.auth();
                    break;
                case(409):
                    //ChaosCraft.auth();
                    break;
            }
            ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
            String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();//new String(byteBuffer.as().array(), StandardCharsets.UTF_8 );
            ChaosCraft.LOGGER.error("ChaosSpeciesListOverlayGui Error: " + message + " - statusCode: " + statusCode);
            ChaosCraft.getClient().thread = null;

        }catch(Exception exception){

            ChaosCraft.LOGGER.error("ChaosSpeciesListOverlayGui Error: " + exception.getMessage() + " - exception type: " + exception.getClass().getName());


        }


    }
    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);
        int i = 90;


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
