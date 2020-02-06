package com.schematical.chaoscraft.ai;

import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class NeuronDep extends InnovationBase {
    public NeuronBase depNeuron;
    public String depNeuronId;
    public float weight;
    private float _lastValue;
    private float _currentValue;
    private boolean hasBeenEvaluated = false;
    public NeuronDep(){

    }
    public float evaluate(){
        if(hasBeenEvaluated){
            return getCurrentValue();
        }
        setCurrentValue( weight * depNeuron.evaluate());
        return getCurrentValue();
    }
    public float getCurrentValue(){
        return _currentValue;
    }
    public void setCurrentValue(float currentValue){
        _currentValue = currentValue;
    }
    public void reset(){
        hasBeenEvaluated = false;
        _lastValue = _currentValue;
        _currentValue = -1;
    }
    public void parseData(JSONObject jsonObject){
        Object neuronIdObj = jsonObject.get("neuronId");
        if(neuronIdObj == null){
            throw new Error("Could not find a valid `neuronId` " + jsonObject.toJSONString());
        }
        String neuronId = neuronIdObj.toString();

        this.depNeuronId = neuronId;
        this.weight = Float.parseFloat(jsonObject.get("weight").toString());
    }
    public void populate(NeuralNet neuralNet){
        this.depNeuron = neuralNet.neurons.get(this.depNeuronId);
        if(this.depNeuron == null){
            throw new ChaosNetException("Could not find a neuron for " + this.depNeuronId);
        }
    }
}
