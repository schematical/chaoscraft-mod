package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientFactoryBlockStateChangePacket;
import com.schematical.chaoscraft.network.packets.CCClientSpawnBlockStateChangePacket;
import com.schematical.chaoscraft.tileentity.FactoryTileEntity;
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
public class ChaosFactoryBlockSettingScreen extends Screen {
    protected FactoryTileEntity tileEntity;

    private TextFieldWidget entityTypeText;
    private String entityType;

    private TextFieldWidget entityCountText;
    private int entityCount;

    private TextFieldWidget entityRangeText;
    private int entityRange;
    public ChaosFactoryBlockSettingScreen(FactoryTileEntity tileEntity) {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));
        this.tileEntity = tileEntity;

    }
    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.entityType = tileEntity.getEntityType().getRegistryName().toString();
        int y = 100;
        this.entityTypeText = new TextFieldWidget(this.font, this.width / 2 - 100, y, 200, 20, "Entity Type");
        this.entityTypeText.setText(entityType);
        this.entityTypeText.setResponder((text) -> {
            entityType = text;
        });
        this.children.add(this.entityTypeText);

        y += 30;
        this.entityCount = tileEntity.getEntityCount();
        this.entityCountText = new TextFieldWidget(this.font, this.width / 2 - 100, y, 200, 20, "Entity Count");
        this.entityCountText.setText(Integer.toString(entityCount));
        this.entityCountText.setResponder((text) -> {
            try {
                entityCount = Integer.parseInt(text);
            }catch (Exception e){

            }
        });
        this.children.add(this.entityCountText);

        y += 30;
        this.entityRange = tileEntity.getEntityRange();
        this.entityRangeText = new TextFieldWidget(this.font, this.width / 2 - 100, y, 200, 20, "Entity Range");
        this.entityRangeText.setText(Integer.toString(entityRange));
        this.entityRangeText.setResponder((text) -> {

            try {
                entityRange = Integer.parseInt(text);
            }catch (Exception e){

            }
        });
        this.children.add(this.entityRangeText);


        this.addButton(new Button(this.width / 2 - 100, 200, 200, 20, I18n.format("chaoscraft.gui.orgdetail.nnet"), (p_214266_1_) -> {

            CCClientFactoryBlockStateChangePacket pkt = new CCClientFactoryBlockStateChangePacket(entityType, tileEntity.getPos(), entityCount, entityRange);
            ChaosNetworkManager.sendToServer(pkt);
            Minecraft.getInstance().displayGuiScreen(null);
        }));


    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);


        this.entityTypeText.render(p_render_1_, p_render_2_, p_render_3_);
        this.entityCountText.render(p_render_1_, p_render_2_, p_render_3_);
        this.entityRangeText.render(p_render_1_, p_render_2_, p_render_3_);


        //this.drawCenteredString(this.font, "Something else goes here", this.width / 2, i, 16777215);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }


}
