package com.schematical.chaoscraft.client;

public class ChaosCraftClient {
    public enum State{
        Uninitiated
    }
    protected State state = State.Uninitiated;
    public void tick(){
        if(state.equals(State.Uninitiated)){
            init();
            return;
        }
    }

    public void init(){
        //TODO: Send Auth To Server

        //Get info on what the server is running


    }
}
