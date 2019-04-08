package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.FloatMemory;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class GetFloatMemoryInput extends InputNeuron {

  private String memoryId;

  @Override
  public float evaluate() {

    FloatMemory floatMemory = (FloatMemory) nNet.getBiology(memoryId);
    return floatMemory.getValue();
  }

  @Override
  public void parseData(JSONObject jsonObject) {
    super.parseData(jsonObject);
    memoryId = jsonObject.get("memoryId").toString();
  }

}
