package com.schematical.chaoscraft.ai;

import com.google.common.collect.Sets;
import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.entity.Entity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.Set;
import com.schematical.chaoscraft.ai.inputs.*;
import com.schematical.chaoscraft.ai.outputs.*;
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
        JSONArray neurons = (JSONArray) jsonObject.get("neurons");
        Iterator<JSONObject> iterator = neurons.iterator();
        while (iterator.hasNext()) {
            JSONObject neuronBaseJSON = iterator.next();

            String clsName = neuronBaseJSON.get("type").toString();  // use fully qualified name

            try {
                String fullClassName = "com.schematical.chaoscraft.ai." + neuronBaseJSON.get("_base_type").toString()  +  "s." + clsName;
                ChaosCraft.logger.info("Full Class name: " + fullClassName);
                Class cls = Class.forName(fullClassName);
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
