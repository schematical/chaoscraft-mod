package com.schematical.chaoscraft.client.gui;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ChaosCraftClient;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientServerPingRequestPacket;
import com.schematical.chaoscraft.network.packets.CCServerPingResponsePacket;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChaosNetworkInfoOverlayGui extends Screen {
    public static String serverPingMessage = null;
    public ChaosNetworkInfoOverlayGui() {
        super(new TranslationTextComponent("chaoscraft.gui.mainmenu.title"));

    }
    protected void init() {
        super.init();
        serverPingMessage = null;
    }
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        //this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, , 16777215);
        this.addButton(new Button(this.width - 100, 0, 100, 20, I18n.format("chaoscraft.gui.networkinfo.ping-server"), (p_214266_1_) -> {
            //Send server ping message
            ChaosNetworkManager.sendToServer(new CCClientServerPingRequestPacket());
        }));


        String message = serverPingMessage;
        if(message == null){
            message = getClientMessage();
        }
        String[] parts = message.split("\n");
        for (int i = 0; i < parts.length; i++) {
            this.drawString(this.font, parts[i], 0, i *  10, 16777215);
        }

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }
    public String getClientMessage(){
        ChaosCraftClient chaosCraftClient = ChaosCraft.getClient();
        String message = "";
        message += "Client: \n";
        message += "ticksSinceLastSpawn:  " + chaosCraftClient.getTicksSinceLastSpawn() + "\n";
        message += "State:  " + chaosCraftClient.getState() + "\n";
        message += "trainingRoomNamespace:  " + chaosCraftClient.getTrainingRoomNamespace() + "\n";
        message += "trainingRoomUsernameNamespace:  " + chaosCraftClient.getTrainingRoomUsernameNamespace() + "\n";
        message += "sessionNamespace:  " + chaosCraftClient.getSessionNamespace() + "\n";
        message += "debugSpawnedOrgNamespaces:  " + chaosCraftClient._debugSpawnedOrgNamespaces.size() + "\n";
        message += "debugReportedOrgNamespaces:  " + chaosCraftClient._debugReportedOrgNamespaces.size() + "\n";

        message += "consecutiveErrorCount:  " + chaosCraftClient.consecutiveErrorCount + "\n";
        message += "orgsToSpawn Count:  " + chaosCraftClient.orgsToSpawn.size() + "\n";
        message += "orgsQueuedToSpawn Count:  " + chaosCraftClient.orgsQueuedToSpawn.size() + "\n";
        message += "orgsToReport Count:  " + chaosCraftClient.orgsToReport.size() + "\n";
        message += "myOrganisims Count:  " + chaosCraftClient.myOrganisims.size() + "\n";
        message += "\n";
        message += "Orgs To Spawn:\n";
        for (Organism organism : chaosCraftClient.orgsToSpawn) {
            message += organism.getNamespace() + "\n";
        }
        return message;
    }

}
