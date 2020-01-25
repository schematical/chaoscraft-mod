package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.events.CCWorldEvent;
import net.minecraft.item.ItemStack;

/**
 * Created by user1a on 12/10/18.
 */
public class TossEquippedStackOutput extends OutputNeuron {

    //protected String attributeId;
    //protected String attributeValue;

    @Override
    public void execute() {
        if(this._lastValue <= .5){
            return;
        }

        ItemStack itemStack = nNet.entity.tossEquippedStack();
        if(
                itemStack == null ||
                itemStack.isEmpty()
        ){
            return;
        }
        /*
        switch(attributeId){
            case(CCAttributeId.ITEM_ID):
                itemStack = nNet.entity.equip(attributeValue);
            break;
            default:
                throw new ChaosNetException("Invalid `EquipOutput.attributeId`: " + attributeId);
        }*/


        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TOSSED_EQUIPPED_STACK);
        worldEvent.item = itemStack.getItem();
        nNet.entity.entityFitnessManager.test(worldEvent);

    }

}
