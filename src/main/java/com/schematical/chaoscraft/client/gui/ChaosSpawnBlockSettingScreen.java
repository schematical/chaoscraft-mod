package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientSpawnBlockStateChangePacket;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosSpawnBlockSettingScreen extends Screen {
    protected SpawnBlockTileEntity tileEntity;

    private TextFieldWidget spawnPointIdText;
    private String spawnPointId;

    private TextFieldWidget maxLivingEntitesText;
    private int maxLivingEntites;

    private TextFieldWidget matchIdText;
    private String matchId;
    public ChaosSpawnBlockSettingScreen(SpawnBlockTileEntity tileEntity) {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));
        this.tileEntity = tileEntity;

    }
    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.spawnPointId = tileEntity.getSpawnPointId();
        this.spawnPointIdText = new TextFieldWidget(this.font, this.width / 2 - 100, 40, 200, 20, tileEntity.getSpawnPointId());
        this.spawnPointIdText.setText(spawnPointId);
        this.spawnPointIdText.setResponder((text) -> {
            spawnPointId = text;
        });
        this.children.add(this.spawnPointIdText);

        this.maxLivingEntites = tileEntity.getMaxLivingEntities();
        this.maxLivingEntitesText = new TextFieldWidget(this.font, this.width / 2 - 100, 70, 200, 20, tileEntity.getSpawnPointId());
        this.maxLivingEntitesText.setText(Integer.toString(maxLivingEntites));
        this.maxLivingEntitesText.setResponder((text) -> {
            try {
                maxLivingEntites = Integer.parseInt(text);
            }catch(Exception e){

            }
        });
        this.children.add(this.maxLivingEntitesText);


        this.matchId = tileEntity.getMatchId();
        this.matchIdText = new TextFieldWidget(this.font, this.width / 2 - 100, 100, 200, 20, tileEntity.getMatchId());
        this.matchIdText.setText(matchId);
        this.matchIdText.setResponder((text) -> {
            matchId = text;
        });
        this.children.add(this.matchIdText);
        this.addButton(new Button(this.width / 2 - 100, 200, 200, 20, I18n.format("chaoscraft.gui.save"), (p_214266_1_) -> {

            CCClientSpawnBlockStateChangePacket pkt = new CCClientSpawnBlockStateChangePacket(spawnPointId, tileEntity.getPos(), this.maxLivingEntites, this.matchId);
            ChaosNetworkManager.sendToServer(pkt);
            Minecraft.getInstance().displayGuiScreen(null);
        }));


    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 20, 16777215);
        this.drawCenteredString(this.font, I18n.format("chaoscraft.gui.spawnblock.spawnpointid"), this.width / 2, 90, 16777215);
        this.drawCenteredString(this.font, I18n.format("chaoscraft.gui.spawnblock.maxlivingentities"), this.width / 2, 130, 16777215);


        this.spawnPointIdText.render(p_render_1_, p_render_2_, p_render_3_);
        this.maxLivingEntitesText.render(p_render_1_, p_render_2_, p_render_3_);
        this.matchIdText.render(p_render_1_, p_render_2_, p_render_3_);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }


}
