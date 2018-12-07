package com.schematical.chaoscraft;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by user1a on 12/6/18.
 */
public class ChaosCraftConfig {
    public String refreshToken;
    public String accessToken;
    public int maxBotCount = 5;
    public void save(){

        JSONObject obj = new JSONObject();
        obj.put("refreshToken", refreshToken);
        obj.put("accessToken", accessToken);
        obj.put("maxBotCount", maxBotCount);
        // try-with-resources statement based on post comment below :)
        try {
            FileWriter file = new FileWriter(getConfigPath());

            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File..." + obj.toJSONString());
            System.out.println("\nJSON Object: " + obj);
            file.close();
        }catch (Exception e){
            ChaosCraft.logger.error("Error saving Config: " + e.getMessage());
        }
    }
    public static String getConfigPath(){
        return System.getProperty("user.home") +"/chaoscraft/config.json";
    }
    public void load(){
        File f = new File(getConfigPath());
        JSONObject obj = null;
        if(f.exists() && !f.isDirectory()) {
            // do something

            try {

                JSONParser parser = new JSONParser();
                obj = (JSONObject) parser.parse(
                    new FileReader(getConfigPath())
                );
                refreshToken = obj.get("refreshToken").toString();
                accessToken = obj.get("accessTokenToken").toString();
                maxBotCount = Integer.parseInt(obj.get("maxBotCount").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
