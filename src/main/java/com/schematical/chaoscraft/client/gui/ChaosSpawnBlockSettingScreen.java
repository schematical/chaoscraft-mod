package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.tileentity.SpawnBlockTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
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
    public ChaosSpawnBlockSettingScreen(SpawnBlockTileEntity tileEntity) {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));
        this.tileEntity = tileEntity;

    }
    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.spawnPointIdText = new TextFieldWidget(this.font, this.width / 2 - 100, 100, 200, 20, tileEntity.getSpawnPointId());
        this.spawnPointIdText.setResponder((text) -> {
            spawnPointId = text;
        });
        this.children.add(this.spawnPointIdText);
        this.addButton(new Button(this.width / 2 - 100, 200, 200, 20, I18n.format("chaoscraft.gui.orgdetail.nnet"), (p_214266_1_) -> {
            tileEntity.setSpawnPointId(spawnPointId, true);
            Minecraft.getInstance().displayGuiScreen(null);
        }));


    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);


        this.spawnPointIdText.render(p_render_1_, p_render_2_, p_render_3_);
        //this.drawCenteredString(this.font, "Something else goes here", this.width / 2, i, 16777215);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }


}
