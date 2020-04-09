package com.schematical.chaoscraft;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

/**
 * Created by user1a on 12/6/18.
 */
public class ChaosCraftConfig {
    public String username;
    public String idToken;
    public String refreshToken;
    public String accessToken;
    public int expiration;
    public int maxBotCount = 50;
    public String sessionNamespace;
    public String trainingRoomUsernameNamespace;
    public String trainingRoomNamespace;
    public String env = "prod";
    public void save(){

        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("idToken", idToken);
        obj.put("refreshToken", refreshToken);
        obj.put("accessToken", accessToken);
        obj.put("maxBotCount", maxBotCount);
        obj.put("expiration", expiration);
        obj.put("sessionNamespace", sessionNamespace);
        obj.put("trainingRoomNamespace", trainingRoomNamespace);
        obj.put("trainingRoomUsernameNamespace", trainingRoomUsernameNamespace);
        obj.put("env", env);
        // try-with-resources statement based on post comment below :)
        try {
            String configFilePath = getConfigPath();
            File file = new File(configFilePath);
            String dirPath = file.getParent();
            File dir = new File(dirPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(configFilePath);


            fileWriter.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File..." + obj.toJSONString());
            System.out.println("\nJSON Object: " + obj);
            fileWriter.close();
        }catch (Exception e){
            ChaosCraft.LOGGER.error("Error saving Config: " + e.getMessage());
        }
    }
    public static String getDirPath(){
        Map<String, String> env = System.getenv();
        String chaosdir = env.get("chaosdir");
        if(chaosdir != null){
            return chaosdir;
        }
        return System.getProperty("user.home") + "/chaoscraft/";
    }
    public static String getConfigPath(){
        return getDirPath() + "config.json";
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
                username = obj.get("username").toString();
                idToken = obj.get("idToken").toString();
                refreshToken = obj.get("refreshToken").toString();
                accessToken = obj.get("accessToken").toString();
                expiration = Integer.parseInt(obj.get("expiration").toString());
                maxBotCount = Integer.parseInt(obj.get("maxBotCount").toString());
                trainingRoomNamespace = obj.get("trainingRoomNamespace").toString();
                trainingRoomUsernameNamespace = obj.get("trainingRoomUsernameNamespace").toString();
                //sessionNamespace = obj.get("sessionNamespace").toString();
                env = obj.get("env").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
