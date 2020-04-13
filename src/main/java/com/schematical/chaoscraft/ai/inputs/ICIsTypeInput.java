package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import net.minecraft.item.crafting.IRecipe;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class ICIsTypeInput extends InputNeuron {


    private String itemId;

    @Override
    public float evaluate(){
        ScanManager scanManager =  ((OrgEntity)this.getEntity()).getClientOrgManager().getScanManager();
        ScanEntry scanEntry = scanManager.getScanItemInstance().getFocusedScanEntry();

        if(
            scanEntry.targetSlot != null &&
            nNet.entity.getItemHandler().getStackInSlot(scanEntry.targetSlot).getItem().getRegistryName().toString().equals(itemId)
        ) {
            setCurrentValue(1);
        }

        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        //recipeId = jsonObject.get("attributeId").toString();
        this.itemId = jsonObject.get("attributeValue").toString();

    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.itemId;
        return response;

    }



}
