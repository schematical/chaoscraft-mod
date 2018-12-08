package com.schematical.chaoscraft.ai;

import com.google.common.collect.Sets;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by user1a on 12/8/18.
 */
public class NeuronBase extends InnovationBase {
    public NeuralNet nNet;
    public float _lastValue;
    protected boolean hasBeenEvaluated = false;
    public static Set<NeuronDep> dependencies = Sets.<NeuronDep>newLinkedHashSet();

    public void parseData(JSONObject jsonObject){
        JSONArray dependencies = (JSONArray) jsonObject.get("dependencies");
        Iterator<JSONObject> iterator = dependencies.iterator();
        while (iterator.hasNext()) {
            JSONObject neuronDepJSON = iterator.next();
            NeuronDep neuronDep = new NeuronDep();
            neuronDep.parseData(neuronDepJSON);
        }
    }
    public float evaluate(){
        float totalScore = 0;
        for(NeuronDep neuronDep :dependencies){
            neuronDep._lastValue = neuronDep.weight * neuronDep.depNeuron.evaluate();
            totalScore += neuronDep._lastValue;
        }
        _lastValue = sigmoid(totalScore);
        return _lastValue;

    }
    public float sigmoid(float x){
        return (float) (1 / (1 + Math.exp(-x)));
    }
}
