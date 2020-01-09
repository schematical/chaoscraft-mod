package com.schematical.chaoscraft.client;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.ClientAuthPacket;

public class ChaosCraftClient {


    public enum State{
        Uninitiated,
        Authed
    }
    protected State state = State.Uninitiated;

    public State getState() {
        return state;
    }
    public void tick(){


    }

    public void init(){
        //TODO: Send Auth To Server
        ChaosCraft.LOGGER.info("Client Sending Auth!!");
        ChaosNetworkManager.sendToServer(new ClientAuthPacket(ChaosCraft.config.accessToken));
        state = State.Authed;
        //Get info on what the server is running


    }
}
