package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.entities.EntityOrganism;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/25/18.
 */
public abstract class BiologyBase {

  public EntityOrganism entity;
  public String id;

  public void parseData(JSONObject jsonObject) {
    id = jsonObject.get("id").toString();
  }

  public void reset() {

  }
}
