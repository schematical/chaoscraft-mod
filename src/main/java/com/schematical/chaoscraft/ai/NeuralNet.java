package com.schematical.chaoscraft.ai;

import com.google.common.collect.Sets;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.entities.EntityOrganism;
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
    public EntityOrganism entity;
    public HashMap<String, NeuronBase> neurons = new HashMap<String, NeuronBase>();
    public List<BiologyBase> biology = new ArrayList<BiologyBase>();

    public NeuralNet() {

    }
    public void attachEntity(EntityOrganism entity){
        this.entity = entity;
    }
    public List<OutputNeuron> evaluate(){
        //Iterate through output neurons
        Iterator<Map.Entry<String, NeuronBase>> iterator = neurons.entrySet().iterator();

        while (iterator.hasNext()) {
            NeuronBase neuronBase = iterator.next().getValue();
            neuronBase.reset();
        }
        iterator = neurons.entrySet().iterator();
        List<OutputNeuron> outputs = new ArrayList<OutputNeuron>();
        while (iterator.hasNext()) {
            NeuronBase neuronBase = iterator.next().getValue();
            if(neuronBase._base_type() == com.schematical.chaoscraft.Enum.OUTPUT){
                OutputNeuron outputNeuron = (OutputNeuron)neuronBase;
                float _last_value = outputNeuron.evaluate();

                outputs.add(outputNeuron);
            }

        }
        return outputs;
    }
    public void parseData(JSONObject jsonObject){
        try {

            JSONArray eyes = (JSONArray) ((JSONObject)jsonObject.get("biology")).get("Eye");
            Iterator<JSONObject> iterator = eyes.iterator();
            while (iterator.hasNext()) {
                JSONObject outputBaseJSON = iterator.next();

                String clsName = outputBaseJSON.get("$TYPE").toString();  // use fully qualified name


                String fullClassName = "com.schematical.chaoscraft.ai.biology." + clsName;
                //ChaosCraft.logger.info("Full Class name: " + fullClassName);
                Class cls = Class.forName(fullClassName);
                BiologyBase biologyBase = (BiologyBase) cls.newInstance();
                biologyBase.parseData(outputBaseJSON);
                biology.add(biologyBase);
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
