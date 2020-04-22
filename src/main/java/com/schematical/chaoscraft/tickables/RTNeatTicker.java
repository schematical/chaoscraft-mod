package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ai.*;
import com.schematical.chaoscraft.ai.inputs.HasInInventoryInput;
import com.schematical.chaoscraft.ai.inputs.TargetCandidateTypeInput;
import com.schematical.chaoscraft.ai.outputs.rawnav.CraftOutput;
import com.schematical.chaoscraft.ai.outputs.rawnav.EquipOutput;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ObservedAttributesElement;

import java.util.ArrayList;
import java.util.List;

public class RTNeatTicker extends BaseChaosEventListener {
    private final int MAX = 20 * 5;
    private CCObservableAttributeManager observableAttributeManager;
    private  int ticksSinceLastMutation = 0;
    private  int newAttributesLength = 0;
    private  OrgEntity orgEntity;
    public RTNeatTicker(OrgEntity orgEntity) {
        this.orgEntity = orgEntity;
        this.observableAttributeManager = orgEntity.observableAttributeManager;
        ticksSinceLastMutation = (int)  (Math.round(Math.random() * MAX /2));
    }


    public void onClientTick(BaseOrgManager serverOrgManager) {
        if( this.observableAttributeManager.newAttributes.size() != newAttributesLength){
            List<ObservedAttributesElement> newAttributes = new ArrayList<ObservedAttributesElement>();
            for(int i = newAttributesLength; i < this.observableAttributeManager.newAttributes.size(); i++){
                newAttributes.add( this.observableAttributeManager.newAttributes.get(i));

            }
            newAttributesLength =  this.observableAttributeManager.newAttributes.size();
            NeuralNet neuralNet = orgEntity.getNNet();
            ArrayList<NeuronBase> newNeurons = new ArrayList<>();
            for (ObservedAttributesElement newAttribute : newAttributes) {
                switch(newAttribute.getAttributeId()){
                    case(CCAttributeId.ENTITY_ID):
                    case(CCAttributeId.BLOCK_ID):
                        TargetCandidateTypeInput neuronBase = new TargetCandidateTypeInput();
                        neuronBase.setEvalGroup(NeuralNet.EvalGroup.TARGET);
                        neuronBase.attachNNet(neuralNet);

                        neuronBase.attributeId = newAttribute.getAttributeId();
                        neuronBase.attributeValue = newAttribute.getAttributeValue();
                        newNeurons.add(neuronBase);



                        break;
                    case(CCAttributeId.ITEM_ID):
                        EquipOutput equipOutput = new EquipOutput();
                        equipOutput.attachNNet(neuralNet);

                        equipOutput.attributeId = newAttribute.getAttributeId();
                        equipOutput.attributeValue = newAttribute.getAttributeValue();
                        newNeurons.add(equipOutput);

                        HasInInventoryInput hasInInventoryInput = new HasInInventoryInput();
                        hasInInventoryInput.attachNNet(neuralNet);

                        hasInInventoryInput.attributeId = newAttribute.getAttributeId();
                        hasInInventoryInput.attributeValue = newAttribute.getAttributeValue();
                        newNeurons.add(hasInInventoryInput);


                        break;
                    case(CCAttributeId.RECIPE_ID):
                        //TODO: Add craft output
                        CraftOutput craftOutput = new CraftOutput();
                        craftOutput.attachNNet(neuralNet);

                        //craftOutput.recipeId = newAttribute.getAttributeId();
                        craftOutput.recipeId = newAttribute.getAttributeValue();
                        newNeurons.add(craftOutput);

                        break;
                }



            }
            neuralNet.randomAddNewNeurons(newNeurons);
            ticksSinceLastMutation = 0;
        }
        //Check if it has been long enough to do this
        ticksSinceLastMutation += 1;
        if(ticksSinceLastMutation < MAX){
            return;
        }
        ticksSinceLastMutation = 0;

    }


}
