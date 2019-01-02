package com.schematical.chaoscraft.ai;

import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class NeuronDep extends InnovationBase{
    public NeuronBase depNeuron;
    public String depNeuronId;
    public float weight;
    public float _lastValue;
    public NeuronDep(){
        /*(NeuronBase depNeuron, float weight){
        this.depNeuron = depNeuron;
        this.weight = weight; */
    }
    public void parseData(JSONObject jsonObject){
        String neuronId = jsonObject.get("neuronId").toString();
        //this.depNeuron = //TODO: Load neuron
        this.depNeuronId = neuronId;
        this.weight = Float.parseFloat(jsonObject.get("weight").toString());
    }
    public void populate(NeuralNet neuralNet){
        this.depNeuron = neuralNet.neurons.get(this.depNeuronId);
    }
}
