package com.schematical.chaoscraft.ai;

import com.schematical.chaoscraft.ai.biology.BiologyBase;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Created by user1a on 12/8/18.
 */
public class NeuralNet {
    public int neuronEvalDepth = -1;
    public OrgEntity entity;
    public HashMap<String, NeuronBase> neurons = new HashMap<String, NeuronBase>();
    public HashMap<String, BiologyBase> biology = new HashMap<String, BiologyBase>();
    public boolean ready = false;
    private EvalGroup currentTargetEvalGroup;

    public NeuralNet() {

    }
    public void attachEntity(OrgEntity entity){
        this.entity = entity;
    }
    public List<OutputNeuron> evaluate(){
        return evaluate(EvalGroup.DEFAULT);
    }
    public List<OutputNeuron> evaluate(EvalGroup targetEvalGroup){
        if(currentTargetEvalGroup != null){
            throw new ChaosNetException("Dobule eval - Two processes are calling eval at once or you forgot to set `currentTargetEvalGroup` to null when you are done");
        }
        currentTargetEvalGroup = targetEvalGroup;
        HashMap<String, OutputGroupResult> outputGroupResults = new HashMap<String, OutputGroupResult>();
        //Iterate through output neurons
        Iterator<Map.Entry<String, NeuronBase>> iterator = neurons.entrySet().iterator();

        while (iterator.hasNext()) {
            NeuronBase neuronBase = iterator.next().getValue();
            neuronBase.reset();
        }
        for(BiologyBase biologyBase: biology.values()){
            biologyBase.reset();
        }
        iterator = neurons.entrySet().iterator();
        List<OutputNeuron> outputs = new ArrayList<OutputNeuron>();
        while (iterator.hasNext()) {
            NeuronBase neuronBase = iterator.next().getValue();
            if(
                neuronBase._base_type().equals(com.schematical.chaoscraft.Enum.OUTPUT) &&
                neuronBase.getEvalGroup().equals(currentTargetEvalGroup)
            ){
                OutputNeuron outputNeuron = (OutputNeuron)neuronBase;
                neuronEvalDepth = 0;
                float _last_value = outputNeuron.evaluate();
                outputNeuron.setCurrentValue(_last_value);
                switch (outputNeuron._outputGroup){
                    case(OutputNeuron.OUTPUT_GROUP_NONE):
                        outputs.add(outputNeuron);
                    break;
                    default:
                        if(!outputGroupResults.containsKey(outputNeuron._outputGroup)){
                            OutputGroupResult outputGroupResult = new OutputGroupResult();
                            outputGroupResult.highNeuron = outputNeuron;
                            outputGroupResult.highScore = outputNeuron.getCurrentValue();
                            outputGroupResults.put(outputNeuron._outputGroup, outputGroupResult);
                        }else{
                            OutputGroupResult outputGroupResult = outputGroupResults.get(outputNeuron._outputGroup);
                            if(outputGroupResult.highScore < outputNeuron.getCurrentValue()){
                                outputGroupResult.highNeuron = outputNeuron;
                                outputGroupResult.highScore = outputNeuron.getCurrentValue();
                            }
                        }
                    break;
                }

            }

        }
        Iterator<OutputGroupResult> outputGroupResultIterator = outputGroupResults.values().iterator();
        while(outputGroupResultIterator.hasNext()){
            OutputGroupResult outputGroupResult = outputGroupResultIterator.next();
            outputs.add(outputGroupResult.highNeuron);
        }
        currentTargetEvalGroup = null;
        return outputs;
    }
    public EvalGroup getCurrentTargetEvalGroup(){
        return currentTargetEvalGroup;
    }
    public void parseData(JSONObject jsonObject){
        try {

            JSONArray jsonBiology = ((JSONArray)jsonObject.get("biology"));
            Iterator<JSONObject> iterator = jsonBiology.iterator();
            while (iterator.hasNext()) {
                JSONObject outputBaseJSON = iterator.next();

                String clsName = outputBaseJSON.get("$TYPE").toString();  // use fully qualified name


                String fullClassName = "com.schematical.chaoscraft.ai.biology." + clsName;
                //ChaosCraft.logger.info("Full Class name: " + fullClassName);
                Class cls = Class.forName(fullClassName);
                BiologyBase biologyBase = (BiologyBase) cls.newInstance();
                biologyBase.parseData(outputBaseJSON);
                biologyBase.setEntity(this.entity);
                biology.put(biologyBase.id, biologyBase);
            }



            JSONArray neurons = (JSONArray) jsonObject.get("neurons");
            /*Iterator<JSONObject>*/ iterator = neurons.iterator();
            while (iterator.hasNext()) {
                JSONObject neuronBaseJSON = iterator.next();
                Object type = neuronBaseJSON.get("$TYPE");


                if(type == null){
                    throw new Error("Invalid Neuron. Missing `$TYPE` - Org.namepace: " + entity.getCCNamespace() + " - Neuron: " + neuronBaseJSON.toJSONString());
                }

                String clsName = type.toString();  // use fully qualified name

                String fullClassName = "com.schematical.chaoscraft.ai." + neuronBaseJSON.get("_base_type").toString()  +  "s." + clsName;
                //ChaosCraft.logger.info("Full Class name: " + fullClassName);
                Class cls = Class.forName(fullClassName);
                NeuronBase neuronBase = (NeuronBase) cls.newInstance();
                neuronBase.attachNNet(this);
                neuronBase.parseData(neuronBaseJSON);
                this.neurons.put(neuronBase.id, neuronBase);
            }
            Iterator<Map.Entry<String, NeuronBase>> iterator2 = this.neurons.entrySet().iterator();
            while (iterator2.hasNext()){
                NeuronBase neuronBase = iterator2.next().getValue();
                neuronBase.populate();
            }
            ready = true;
        }/* catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/catch(Exception e){
            e.printStackTrace();
            throw new ChaosNetException(e.getClass().getSimpleName() + " - " + e.getMessage() + " -Look above for Stacktrace");
        }
    }
    public BiologyBase getBiology(String id){
        if(!biology.containsKey(id)){
            return null;
        }
        return biology.get(id);
    }
    public ArrayList<BiologyBase> getBiologyByType(Class c){
        ArrayList<BiologyBase> biologies = new ArrayList<BiologyBase>();
        for (BiologyBase biologyBase : this.biology.values()) {
            if(biologyBase.getClass().equals(c)){
                biologies.add(biologyBase);
            }
        }
        return biologies;
    }
    private class OutputGroupResult{
        public float highScore;
        public OutputNeuron highNeuron;
    }
    public enum EvalGroup{
        DEFAULT,
        TARGET
    }
}
