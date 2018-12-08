package com.schematical.chaoscraft.ai;

import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by user1a on 12/8/18.
 */
public class NeuralNet {
    public Entity entity;
    public static Set<NeuronBase> neurons = Sets.<NeuronBase>newLinkedHashSet();

    public NeuralNet() {

    }
    public void attachEntity(Entity entity){
        this.entity = entity;
    }
    public void parseData(JSONObject jsonObject){
        JSONArray dependencies = (JSONArray) jsonObject.get("neurons");
        Iterator<JSONObject> iterator = dependencies.iterator();
        while (iterator.hasNext()) {
            JSONObject neuronBaseJSON = iterator.next();

            String clsName = neuronBaseJSON.get("type").toString();  // use fully qualified name

            try {
                Class cls = Class.forName(clsName);
                NeuronBase neuronBase = (NeuronBase) cls.newInstance();
                neuronBase.parseData(neuronBaseJSON);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
    }
}
