package com.schematical.chaoscraft.ai.biology;

import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/25/18.
 */
public abstract class BiologyBase {
    public String id;
    public void parseData(JSONObject jsonObject){
        id = jsonObject.get("id").toString();
    }
}
