package com.schematical.chaoscraft.ai;

import com.google.common.collect.Sets;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import net.minecraft.entity.Entity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.schematical.chaoscraft.ai.inputs.*;
import com.schematical.chaoscraft.ai.outputs.*;
/**
 * Created by user1a on 12/8/18.
 */
public class NeuralNet {
    public Entity entity;
    public Set<NeuronBase> neurons = Sets.<NeuronBase>newLinkedHashSet();
    public List<BiologyBase> biology = new ArrayList<BiologyBase>();

    public NeuralNet() {

    }
    public void attachEntity(Entity entity){
        this.entity = entity;
    }
    public void evaluate(){
        //Iterate through output neurons
        Iterator<NeuronBase> iterator = neurons.iterator();

        while (iterator.hasNext()) {
            NeuronBase neuronBase = iterator.next();
            neuronBase.reset();
        }
        iterator = neurons.iterator();

        while (iterator.hasNext()) {
            NeuronBase neuronBase = iterator.next();
            if(neuronBase._base_type() == com.schematical.chaoscraft.Enum.OUTPUT){
                float _last_value = neuronBase.evaluate();

            }

        }
    }
    public void parseData(JSONObject jsonObject){
        try {

            JSONArray outputs = (JSONArray) ((JSONObject)jsonObject.get("biology")).get("Eye");
            Iterator<JSONObject> iterator = outputs.iterator();
            while (iterator.hasNext()) {
                JSONObject outputBaseJSON = iterator.next();

                String clsName = outputBaseJSON.get("$TYPE").toString();  // use fully qualified name


                String fullClassName = "com.schematical.chaoscraft.ai.biology." + clsName;
                ChaosCraft.logger.info("Full Class name: " + fullClassName);
                Class cls = Class.forName(fullClassName);
                BiologyBase biologyBase = (BiologyBase) cls.newInstance();
                biologyBase.parseData(outputBaseJSON);
                biology.add(biologyBase);
            }



            JSONArray neurons = (JSONArray) jsonObject.get("neurons");
            /*Iterator<JSONObject>*/ iterator = neurons.iterator();
            while (iterator.hasNext()) {
                JSONObject neuronBaseJSON = iterator.next();

                String clsName = neuronBaseJSON.get("$TYPE").toString();  // use fully qualified name


                String fullClassName = "com.schematical.chaoscraft.ai." + neuronBaseJSON.get("_base_type").toString()  +  "s." + clsName;
                ChaosCraft.logger.info("Full Class name: " + fullClassName);
                Class cls = Class.forName(fullClassName);
                NeuronBase neuronBase = (NeuronBase) cls.newInstance();
                neuronBase.attachNNet(this);
                neuronBase.parseData(neuronBaseJSON);
                this.neurons.add(neuronBase);
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
