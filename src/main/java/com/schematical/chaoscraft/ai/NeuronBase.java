package com.schematical.chaoscraft.ai;


import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

/**
 * Created by user1a on 12/8/18.
 */
public abstract class NeuronBase extends InnovationBase {

    public String id;

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
     * x must be a float between 0 and +1 (exclusive), but still should not fail if x is 0 or +1
     * @param x
     * @return
     */
    public float reverseSigmoid(float x){
        return (Math.log((x + Float.MIN_VALUE) / (1f + Float.MIN_VALUE - x)));
    }
    public void attachNNet(NeuralNet neuralNet) {
        this.nNet = neuralNet;
    }
    public String toString(){
        String response = this.getClass().getSimpleName().replace("Input","");
        response += " ";
        float prettyLastValue = (Math.round(this._lastValue * 1000f) / 1000f);
        response += prettyLastValue;

        return response;
    }
    public String toLongString(){
        String response = this.getClass().getSimpleName().replace("Input","");

        return response;
    }

    public boolean getHasBeenEvaluated() {
        return hasBeenEvaluated;
    }


}
