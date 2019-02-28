package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.CCWorldEventType;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/10/18.
 */
public class EquipOutput extends OutputNeuron {

    protected String attributeId;
    protected String attributeValue;

    @Override
    public void execute() {
        if(this._lastValue <= .5){
            return;
        }
        if(nNet.entity.getDebug()) {
            //ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Attempting to Craft: " + recipe.getRegistryName() + " - " + recipe.getRecipeOutput().getDisplayName());
        }
        ItemStack itemStack = null;
        switch(attributeId){
            case(CCAttributeId.ITEM_ID):
                itemStack = nNet.entity.equip(attributeValue);
            break;
            default:
                throw new ChaosNetException("Invalid `EquipOutput.attributeId`: " + attributeId);
        }
        if(itemStack == null){
            return;
        }

        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEventType.EQUIP);
        worldEvent.item = itemStack.getItem();
        nNet.entity.entityFitnessManager.test(worldEvent);

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
