package com.schematical.chaoscraft.ai;


import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.entity.LivingEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public abstract class NeuronBase extends InnovationBase {

    public String id;
    protected LivingEntity debugEntity = null;
    public NeuralNet nNet;
    private float _lastValue;
    private float _currentValue;
    private boolean hasBeenEvaluated = false;

    public List<NeuronDep> dependencies = new ArrayList<NeuronDep>();
    public abstract String _base_type();
    public void setCurrentValue(float currentValue){
        _currentValue = currentValue;
    }
    public float getCurrentValue(){
        return _currentValue;
    }
    public NeuralNet getNNet(){
        return nNet;
    }
    public void setDebugEntity(LivingEntity debugEntity){
        this.debugEntity = debugEntity;
    }
    public LivingEntity getEntity(){
        if(debugEntity != null){
            return debugEntity;
        }
        return nNet.entity;
    }
    public void parseData(JSONObject jsonObject){
        if(this.id != null){
            throw new Error("Neuron has already parsedData");
        }
        this.id = (String) jsonObject.get("id");
        JSONArray jsonDependencies = (JSONArray) jsonObject.get("dependencies");
        Iterator<JSONObject> iterator = jsonDependencies.iterator();
        while (iterator.hasNext()) {
            JSONObject neuronDepJSON = iterator.next();
            NeuronDep neuronDep = new NeuronDep();
            neuronDep.parseData(neuronDepJSON);
            if(neuronDep.depNeuronId.equals(id)){
                throw new ChaosNetException("Invalid `neuronDep` - NeuronDep on self: " + id);
            }
            this.dependencies.add(neuronDep);
        }

    }
    public void reset(){
        hasBeenEvaluated = false;
        _lastValue = _currentValue;
        _currentValue = -1;
    }
    public float evaluate(){
        float newValue = -1;
        if(hasBeenEvaluated){
            return getCurrentValue();
        }
        nNet.neuronEvalDepth += 1;
        if (nNet.neuronEvalDepth > 45) {
            throw new ChaosNetException("Max Eval Depth Hit: " + this.nNet.entity.getCCNamespace() + "   " + this.id);
        }
        float totalScore = 0;
        for(NeuronDep neuronDep :dependencies){
            if(neuronDep.depNeuron == null){
                String orgNamespace = nNet.entity.getCCNamespace();
                throw new ChaosNetException("Missing `neuronDep.depNeuron` : " + orgNamespace + " " + neuronDep.depNeuronId);
            }

            neuronDep.evaluate();
            totalScore += neuronDep.getCurrentValue();
        }
        newValue = sigmoid(totalScore);
        hasBeenEvaluated = true;
        nNet.neuronEvalDepth -= 1;
        setCurrentValue(newValue);
        return getCurrentValue();

    }
    public void populate(){
        for (NeuronDep neuronDep: dependencies) {
            neuronDep.populate(this.nNet);
        }
    }
    public float sigmoid(float x){
        return (float) (1 / (1 + Math.exp(-x)));
    }

    /**
     * This does an aproximation between -1 and 1 of its what the value coming in may have been
     * @param x
     * @return
     */
    public float reverseSigmoid(float x){
        return ((x * 2) -1);
    }
    public void attachNNet(NeuralNet neuralNet) {
        this.nNet = neuralNet;
    }
    public String toString(){
        String response = this.getClass().getSimpleName().replace("Input","");
        response += " ";

        response += getPrettyLastValue();

        return response;
    }
    public String toLongString(){
        String response = this.getClass().getSimpleName().replace("Input","") + " ";
        response += getPrettyLastValue();
        return response;
    }
    public float getPrettyLastValue(){
        float prettyLastValue = (Math.round(this._lastValue * 100f) / 100f);
        return prettyLastValue;
    }

    public boolean getHasBeenEvaluated() {
        return hasBeenEvaluated;
    }


}
