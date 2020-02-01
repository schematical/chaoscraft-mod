package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientSpawnPacket;
import com.schematical.chaoscraft.network.packets.CCClientStartTrainingSessionPacket;
import com.schematical.chaosnet.model.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@OnlyIn(Dist.CLIENT)
public class ChaosTrainingRoomSelectionOverlayGui extends Screen {


    private TextFieldWidget usernameInput;

    private TextFieldWidget trainingRoomNamespaceInput;

private String trainingRoomUsername;
private String trainingRoomNamespace;
    private String infoMessage;
    private Button button;
    private boolean nextClose = false;

    public ChaosTrainingRoomSelectionOverlayGui() {
        super(new TranslationTextComponent("chaoscraft.gui.auth.title"));

    }
    protected void init() {
        super.init();

        this.minecraft.keyboardListener.enableRepeatEvents(true);
        button = new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, I18n.format("chaoscraft.gui.auth.login"), (p_214266_1_) -> {
            if(nextClose){
                this.close();
                return;
            }
            CCClientStartTrainingSessionPacket packet = new CCClientStartTrainingSessionPacket(
                    trainingRoomUsername,
                    trainingRoomNamespace
            );
            ChaosNetworkManager.sendToServer(packet);
        });
        this.addButton(button);

        this.usernameInput = new TextFieldWidget(this.font, this.width / 2 - 100, 60, 200, 20, ChaosCraft.config.trainingRoomUsernameNamespace) {
            protected String getNarrationMessage() {
                return"IDK what this is";
            }
        };
        this.usernameInput.func_212954_a((username) -> {
            trainingRoomUsername = username;
            //authLogin.setPassword(this.password);
        });
        this.children.add(this.usernameInput);

        this.trainingRoomNamespaceInput = new TextFieldWidget(this.font, this.width / 2 - 100, 100, 200, 20, ChaosCraft.config.trainingRoomNamespace) {
            protected String getNarrationMessage() {
                return"IDK what this is";
            }
        };
        this.trainingRoomNamespaceInput.func_212954_a((namespace) -> {
           trainingRoomNamespace = namespace;
        });
        this.children.add(this.trainingRoomNamespaceInput);


    }
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);

        this.drawCenteredString(this.font, "Username", this.width / 2, 50, 16777215);
        this.drawCenteredString(this.font, "Training Room", this.width / 2, 90, 16777215);
        this.usernameInput.render(p_render_1_, p_render_2_, p_render_3_);
        this.trainingRoomNamespaceInput.render(p_render_1_, p_render_2_, p_render_3_);

        this.drawCenteredString(this.font, this.infoMessage, this.width / 2, 150, 16777215);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }
    private void close() {

        this.minecraft.displayGuiScreen((Screen)null);
    }


}
