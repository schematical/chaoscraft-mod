package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.NeuronBase;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.*;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class ChaosAuthOverlayGui extends Screen {


    private TextFieldWidget usernameInput;

    private TextFieldWidget passwordInput;
    private  AuthLogin authLogin;
    private String infoMessage;
    private Button button;
    private boolean nextClose = false;

    public ChaosAuthOverlayGui() {
        super(new TranslationTextComponent("chaoscraft.gui.auth.title"));

    }

    @Override
    protected void init() {
        super.init();
        authLogin = new AuthLogin();
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        button = new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, I18n.format("chaoscraft.gui.auth.login"), (p_214266_1_) -> {
            if(nextClose){
                this.close();
                return;
            }

            //Run auth
            PostAuthLoginRequest postAuthLoginRequest = new PostAuthLoginRequest();





            postAuthLoginRequest.authLogin(
                    authLogin
            );
            try{

                AuthLoginResponse authLoginResponse = ChaosCraft.sdk.postAuthLogin(postAuthLoginRequest).getAuthLoginResponse();

                ChaosCraft.LOGGER.info("Access Token >> {}", authLoginResponse.getAccessToken());
                ChaosCraft.config.username = authLogin.getUsername();
                ChaosCraft.config.idToken =  authLoginResponse.getIdToken();
                ChaosCraft.config.refreshToken =  authLoginResponse.getRefreshToken();
                ChaosCraft.config.accessToken =  authLoginResponse.getAccessToken();
                ChaosCraft.config.save();
                ChaosCraft.getClient().init();
                //this.close();
                this.infoMessage = "Auth Success: " + authLogin.getUsername();
                button.setMessage( I18n.format("gui.back"));
                nextClose = true;
            }catch(ChaosNetException exception){
                //logger.error(exeception.getMessage());
                ChaosCraft.getClient().consecutiveErrorCount += 1;

                int statusCode = exception.sdkHttpMetadata().httpStatusCode();
                switch(statusCode){
                    case(400):
                        BadRequestException badRequestException = (BadRequestException)exception;
                        if(badRequestException.getError() != null){
                            this.infoMessage = badRequestException.getError().getMessage();
                        }
                        //ChaosCraft.getClient().repair();
                        break;
                    case(401):

                        break;
                    case(409):
                        //ChaosCraft.auth();
                        break;
                }
                ByteBuffer byteBuffer = exception.sdkHttpMetadata().responseContent();
                String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();//new String(byteBuffer.as().array(), StandardCharsets.UTF_8 );
                ChaosCraft.LOGGER.error("ChaosAuthOverlayGui `/login` Error: " + message + " - statusCode: " + statusCode);
                ChaosCraft.getClient().thread = null;

            }catch(Exception exception){
                ChaosCraft.getClient().consecutiveErrorCount += 1;

                ChaosCraft.LOGGER.error("ChaosAuthOverlayGui `/login` Error: " + exception.getMessage() + " - exception type: " + exception.getClass().getName());
                ChaosCraft.getClient().thread = null;

            }

        });
        this.addButton(button);

        this.usernameInput = new TextFieldWidget(this.font, this.width / 2 - 100, 60, 200, 20, I18n.format("selectWorld.enterName")) {
            protected String getNarrationMessage() {
                return"IDK what this is";
            }
        };
        this.usernameInput.setResponder((username) -> {
            authLogin.setUsername(username);
            //authLogin.setPassword(this.password);
        });
        this.children.add(this.usernameInput);

        this.passwordInput = new TextFieldWidget(this.font, this.width / 2 - 100, 100, 200, 20, I18n.format("selectWorld.enterName")) {
            protected String getNarrationMessage() {
                return"IDK what this is";
            }
        };
        this.passwordInput.setResponder((password) -> {
            authLogin.setPassword(password);
        });
        this.children.add(this.passwordInput);


    }


    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 70, 16777215);

        this.drawCenteredString(this.font, "Username", this.width / 2, 50, 16777215);
        this.drawCenteredString(this.font, "Password", this.width / 2, 90, 16777215);
        this.usernameInput.render(p_render_1_, p_render_2_, p_render_3_);
        this.passwordInput.render(p_render_1_, p_render_2_, p_render_3_);

        this.drawCenteredString(this.font, this.infoMessage, this.width / 2, 150, 16777215);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    private void close() {
        this.minecraft.displayGuiScreen((Screen)null);
    }


}
