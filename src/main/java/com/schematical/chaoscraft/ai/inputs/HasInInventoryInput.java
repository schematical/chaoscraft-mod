package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.util.PositionRange;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.Item;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class HasInInventoryInput extends InputNeuron {

    public String attributeId;
    public String attributeValue;
    //public PositionRange positionRange;
    public Item item;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range
        int slot = nNet.entity.hasInInventory(item);
        if(slot == -1){
            return -1;
        }
        return 1;

    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();
        //positionRange = new PositionRange();
        //positionRange.parseData((JSONObject) jsonObject.get("positionRange"));
        switch(attributeId){
            case(CCAttributeId.ITEM_ID):
                item = Item.getByNameOrId(attributeValue);
                if(item == null){
                    throw new ChaosNetException("Could not find Item: " + attributeValue);
                }
            break;
            default:
                throw new ChaosNetException("Invalid `HasInInventoryInput.attributeId`: " + attributeId);
        }
    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.attributeId + " " + this.attributeValue;
        return response;

    }

}
