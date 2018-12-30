package com.schematical.chaoscraft.ai;

import com.google.common.collect.Sets;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by user1a on 12/8/18.
 */
public abstract class NeuronBase extends InnovationBase {
    public NeuralNet nNet;
    public float _lastValue;
    protected boolean hasBeenEvaluated = false;
    public static Set<NeuronDep> dependencies = Sets.<NeuronDep>newLinkedHashSet();
    public abstract String _base_type();

    public void parseData(JSONObject jsonObject){
        JSONArray dependencies = (JSONArray) jsonObject.get("dependencies");
        Iterator<JSONObject> iterator = dependencies.iterator();
        while (iterator.hasNext()) {
            JSONObject neuronDepJSON = iterator.next();
            NeuronDep neuronDep = new NeuronDep();
            neuronDep.parseData(neuronDepJSON);
        }
    }
    public void reset(){
        hasBeenEvaluated = false;
        _lastValue = -1;
    }
    public float evaluate(){
        if(hasBeenEvaluated){
            return _lastValue;
        }
        float totalScore = 0;
        for(NeuronDep neuronDep :dependencies){
            neuronDep._lastValue = neuronDep.weight * neuronDep.depNeuron.evaluate();
            totalScore += neuronDep._lastValue;
        }
        _lastValue = sigmoid(totalScore);
        hasBeenEvaluated = true;
        return _lastValue;

    }
    public float sigmoid(float x){
        return (float) (1 / (1 + Math.exp(-x)));
    }

    public void attachNNet(NeuralNet neuralNet) {
        this.nNet = neuralNet;
    }
}
