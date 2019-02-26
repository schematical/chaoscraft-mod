package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.util.PositionRange;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class HasEquippedInput extends InputNeuron {
    //TODO: Add a specific slot
    public String attributeId;
    public String attributeValue;


    @Override
    public float evaluate(){
        ItemStack itemStack = nNet.entity.getHeldItemMainhand();
        Item testItem = itemStack.getItem();
        CCObservableAttributeManager.CCObserviableAttributeCollection attributeCollection = nNet.entity.observableAttributeManager.Observe(testItem);
        switch(attributeId) {
            case(CCAttributeId.ITEM_ID):

                if (attributeCollection.resourceId != attributeValue) {
                    return -1;
                }
            break;
        }
        return 1;
    }
    @Override
    public void parseData(JSONObject jsonObject) {
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
