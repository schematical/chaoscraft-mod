package com.schematical.chaoscraft.ai;

import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public abstract class NeuronBase extends InnovationBase {

    public String id;

    //Visualisation stuff
    public int x;
    public int y;
    public float layer;
    private int distanceFromOutput;
    private int distanceFromInput;

    public NeuralNet nNet;
    public float _lastValue;
    protected boolean hasBeenEvaluated = false;

    public List<NeuronDep> dependencies = new ArrayList<NeuronDep>();
    public abstract String _base_type();

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
        _lastValue = -1;
    }
    public float evaluate(){
        if(hasBeenEvaluated){
            return _lastValue;
        }
        nNet.neuronEvalDepth += 1;
        if (nNet.neuronEvalDepth > 15) {
            throw new ChaosNetException("Max Eval Depth Hit: " + this.nNet.entity.getCCNamespace() + "   " + this.id);
        }
        float totalScore = 0;
        for(NeuronDep neuronDep :dependencies){
            if(neuronDep.depNeuron == null){
                String orgNamespace = nNet.entity.getCCNamespace();
                throw new ChaosNetException("Missing `neuronDep.depNeuron` : " + orgNamespace + " " + neuronDep.depNeuronId);
            }
            neuronDep._lastValue = neuronDep.weight *
                    neuronDep.depNeuron.evaluate();
            totalScore += neuronDep._lastValue;
        }
        _lastValue = sigmoid(totalScore);
        hasBeenEvaluated = true;
        return _lastValue;

    }
    public void populate(){
        for (NeuronDep neuronDep: dependencies) {
            neuronDep.populate(this.nNet);
        }
    }
    public float sigmoid(float x){
        return (float) (1 / (1 + Math.exp(-x)));
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
        response += " " + (Math.round(this._lastValue * 100) / 100);
        response += " " + (Math.round(this._lastValue * 100) / 100);
        return response;
    }

    public void setcoords(int thisX, int thisY) {
        x = thisX;
        y = thisY;
    }

    public void setDistanceFromIO(int distanceFromOutput, int width, int height, int neuronSize) {
        this.distanceFromOutput = distanceFromOutput;

        this.distanceFromInput = 0;

        for (NeuronDep dependency : this.dependencies) {
            dependency.depNeuron.setDistanceFromIO(distanceFromOutput + 1, width, height, neuronSize);
            this.distanceFromInput = Math.max(this.distanceFromInput, dependency.depNeuron.distanceFromInput + 1);
        }

        if (this.distanceFromInput == 0 && this.distanceFromOutput == 0) {
            this.x = neuronSize;
        } else {
            this.x = (width - neuronSize * 2) / (this.distanceFromInput + this.distanceFromOutput) * this.distanceFromInput + neuronSize;
        }

        System.out.println(this.getClass() + " is " + (!(this instanceof InputNeuron || this instanceof OutputNeuron)));

        if (!(this instanceof InputNeuron || this instanceof OutputNeuron)) {
            // this.y = averageY;
            this.y = (int) (Math.random() * height);
            this.layer = this.distanceFromInput / (float) (this.distanceFromInput + this.distanceFromOutput);
        }
    }
}
