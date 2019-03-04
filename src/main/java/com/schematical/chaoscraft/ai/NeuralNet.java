package com.schematical.chaoscraft.ai;

import com.google.common.collect.Sets;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.entity.Entity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import com.schematical.chaoscraft.ai.inputs.*;
import com.schematical.chaoscraft.ai.outputs.*;
/**
 * Created by user1a on 12/8/18.
 */
public class NeuralNet {
    public int neuronEvalDepth = -1;
    public EntityOrganism entity;
    public HashMap<String, NeuronBase> neurons = new HashMap<String, NeuronBase>();
    public HashMap<String, BiologyBase> biology = new HashMap<String, BiologyBase>();
    public boolean ready = false;

    public NeuralNet() {

    }
    public void attachEntity(EntityOrganism entity){
        this.entity = entity;
    }
    public List<OutputNeuron> evaluate(){
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
            if(neuronBase._base_type().equals(com.schematical.chaoscraft.Enum.OUTPUT)){
                OutputNeuron outputNeuron = (OutputNeuron)neuronBase;
                neuronEvalDepth = 0;
                float _last_value = outputNeuron.evaluate();
                switch (outputNeuron._outputGroup){
                    case(OutputNeuron.OUPUT_GROUP_NONE):
                        outputs.add(outputNeuron);
                    break;
                    default:
                        if(!outputGroupResults.containsKey(outputNeuron._outputGroup)){
                            OutputGroupResult outputGroupResult = new OutputGroupResult();
                            outputGroupResult.highNeuron = outputNeuron;
                            outputGroupResult.highScore = outputNeuron._lastValue;
                            outputGroupResults.put(outputNeuron._outputGroup, outputGroupResult);
                        }else{
                            OutputGroupResult outputGroupResult = outputGroupResults.get(outputNeuron._outputGroup);
                            if(outputGroupResult.highScore < outputNeuron._lastValue){
                                outputGroupResult.highNeuron = outputNeuron;
                                outputGroupResult.highScore = outputNeuron._lastValue;
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

        return outputs;
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
                biologyBase.entity = this.entity;
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
            throw new ChaosNetException(e.getMessage() + " -Look above for Stacktrace");
        }
    }
    public BiologyBase getBiology(String id){
        if(!biology.containsKey(id)){
            return null;
        }
        return biology.get(id);
    }
    private class OutputGroupResult{
        public float highScore;
        public OutputNeuron highNeuron;
    }
}
