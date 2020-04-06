package com.schematical.chaoscraft.ai.outputs.rawnav;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.ItemStack;

import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemStackHandler;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/10/18.
 */
public class EquipOutput extends OutputNeuron {

    public String attributeId;
    public String attributeValue;
    @Override
    public float evaluate(){
        if(getHasBeenEvaluated()){
            return getCurrentValue();
        }
        ItemStack currHeldItem = nNet.entity.getHeldItem(Hand.MAIN_HAND);
        ItemStack itemStack = null;
        switch(attributeId){
            case(CCAttributeId.ITEM_ID):
                if(currHeldItem.getItem().getRegistryName().toString().equals(attributeValue)){
                    return getCurrentValue();//It is already equipped
                }
                itemStack = nNet.entity.getItemStackFromInventory(attributeValue);
                break;
            default:
                throw new ChaosNetException("Invalid `EquipOutput.attributeId`: " + attributeId);
        }
        if(itemStack == null){
            return getCurrentValue();
        }
        return super.evaluate();
    }
    @Override
    public void execute() {
        if(this.getCurrentValue() <= .5f){
            return;
        }

        ItemStack currHeldItem = nNet.entity.getHeldItem(Hand.MAIN_HAND);
        ItemStack itemStack = null;
        switch(attributeId){
            case(CCAttributeId.ITEM_ID):
                if(currHeldItem.getItem().getRegistryName().toString().equals(attributeValue)){
                    return;//It is already equipped
                }
                itemStack = nNet.entity.equip(attributeValue);
            break;
            default:
                throw new ChaosNetException("Invalid `EquipOutput.attributeId`: " + attributeId);
        }
        if(itemStack == null){
            //throw new ChaosNetException("Matt Look Here: This should not be possible");
            return;
        }

        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.EQUIP);
        worldEvent.item = itemStack.getItem();
        nNet.entity.getServerOrgManager().test(worldEvent);
        String message = nNet.entity.getCCNamespace() +" Equipped " + this.toLongString();

        if(itemStack.getItem().getRegistryName().toString().equals("minecraft:crafting_table")){
            //String message = nNet.entity.getCCNamespace() + " Equipped " + itemStack.getItem().getRegistryName();
            ChaosCraft.LOGGER.debug(message);
        }

    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();

    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.attributeId + " - " + this.attributeValue;
        return response;

    }

}
