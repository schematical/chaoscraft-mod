package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientMatchManagerBlockStateChangePacket;
import com.schematical.chaoscraft.network.packets.CCClientSpawnBlockStateChangePacket;
import com.schematical.chaoscraft.tileentity.MatchManagerBlockTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosMatchManagerBlockSettingScreen extends Screen {
    protected MatchManagerBlockTileEntity tileEntity;

    private TextFieldWidget matchIdText;
    private String matchId;

    private TextFieldWidget matchDurationText;
    private int matchDurationSeconds;
    public ChaosMatchManagerBlockSettingScreen(MatchManagerBlockTileEntity tileEntity) {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));
        this.tileEntity = tileEntity;

    }
    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.matchId = tileEntity.getMatchId();
        this.matchIdText = new TextFieldWidget(this.font, this.width / 2 - 100, 100, 200, 20, tileEntity.getMatchId());
        this.matchIdText.setText(matchId);
        this.matchIdText.setResponder((text) -> {
            matchId = text;
        });
        this.children.add(this.matchIdText);

        this.matchDurationSeconds = tileEntity.getMatchDurationTicks();
        this.matchDurationText = new TextFieldWidget(this.font, this.width / 2 - 100, 140, 200, 20, String.valueOf(tileEntity.getMatchDurationTicks()));
        this.matchDurationText.setText(Integer.toString(matchDurationSeconds));
        this.matchDurationText.setResponder((text) -> {
            try {
                matchDurationSeconds = Integer.parseInt(text);
            }catch(Exception e){

            }
        });
        this.children.add(this.matchDurationText);


        this.addButton(new Button(this.width / 2 - 100, 200, 200, 20, I18n.format("chaoscraft.gui.save"), (p_214266_1_) -> {

            CCClientMatchManagerBlockStateChangePacket pkt = new CCClientMatchManagerBlockStateChangePacket(matchId, tileEntity.getPos(), this.matchDurationSeconds * 20);
            ChaosNetworkManager.sendToServer(pkt);
            Minecraft.getInstance().displayGuiScreen(null);
        }));


    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);
        this.drawCenteredString(this.font, I18n.format("chaoscraft.gui.matchmanagerblock.matchId"), this.width / 2, 90, 16777215);
        this.drawCenteredString(this.font, I18n.format("chaoscraft.gui.matchmanagerblock.matchDurationSeconds"), this.width / 2, 130, 16777215);


        this.matchIdText.render(p_render_1_, p_render_2_, p_render_3_);
        this.matchDurationText.render(p_render_1_, p_render_2_, p_render_3_);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }


}
