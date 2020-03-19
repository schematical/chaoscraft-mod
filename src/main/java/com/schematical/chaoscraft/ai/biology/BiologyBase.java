package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/25/18.
 */
public abstract class BiologyBase {
    private OrgEntity entity;
    public String id;
    public void setEntity(OrgEntity entity){
        this.entity = entity;
    }
    public OrgEntity getEntity(){
        return entity;
    }
    public void parseData(JSONObject jsonObject){
        id = jsonObject.get("id").toString();
    }

    public void reset() {

    }
    public String toAbreviation(){
        String name =  id;

        String abreviation = "";
        int i = 0;
        try {
            for (i = 0; i < name.length(); i++) {
                String letter = name.substring(i, i + 1);
                if (letter.equals(letter.toUpperCase())) {
                    abreviation += letter;
                }
            }
        }catch (Exception e){
            String message = e.getMessage();
            message += " " + i + " " + abreviation;
            throw new ChaosNetException(message);
        }
        return abreviation;

    }

}
