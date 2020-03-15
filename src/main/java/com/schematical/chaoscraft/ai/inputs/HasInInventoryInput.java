package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import net.minecraft.item.Item;
import net.minecraftforge.items.ItemStackHandler;
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
        int slot = nNet.entity.hasInInventory(attributeValue);
        if(slot == -1){
            setCurrentValue(-1);
            return getCurrentValue();
        }
        ItemStackHandler itemStackHandler = nNet.entity.getItemStackHandeler();

        setCurrentValue(itemStackHandler.getStackInSlot(slot).getCount() / item.getItemStackLimit(itemStackHandler.getStackInSlot(slot)));
        return getCurrentValue();

    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();

    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.attributeId + " " + this.attributeValue;
        return response;

    }

}
