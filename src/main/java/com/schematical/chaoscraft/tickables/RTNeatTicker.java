package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ai.*;
import com.schematical.chaoscraft.ai.inputs.TargetCandidateTypeInput;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ObservedAttributesElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RTNeatTicker implements iChaosOrgTickable {
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

    @Override
    public void Tick(BaseOrgManager serverOrgManager) {
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
                        neuronBase.attachNNet(neuralNet);

                        neuronBase.attributeId = newAttribute.getAttributeId();
                        neuronBase.attributeValue = newAttribute.getAttributeValue();




                        break;
                    case(CCAttributeId.ITEM_ID):

                        break;
                    case(CCAttributeId.RECIPE_ID):

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
