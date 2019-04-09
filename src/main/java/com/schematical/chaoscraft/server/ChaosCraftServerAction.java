package com.schematical.chaoscraft.server;

import org.json.simple.JSONObject;

/**
 * Created by user1a on 4/8/19.
 */
public class ChaosCraftServerAction {
    public enum Action {
        Spawn,
        Stop
    }
    public String orgNamespace;
    public Action action;
    public JSONObject jsonObject;
}
