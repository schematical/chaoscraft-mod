package com.schematical.chaoscraft.ai.biology;

import org.json.simple.JSONObject;

/**
 * Created by user1a on 2/26/19.
 */
public class ObservableTraitsCollection extends BiologyBase {

  protected JSONObject data;

  public String getValue(String key) {
    if (!data.containsKey(key)) {
      return null;
    }
    return data.get(key).toString();
  }

  public Float getValueAsFloat(String key) {
    String value = getValue(key);
    if (value == null) {
      return null;
    }
    return Float.parseFloat(value);

  }

  public Integer getValueAsInt(String key) {
    String value = getValue(key);
    if (value == null) {
      return null;
    }
    return Integer.parseInt(value);
  }

  public Integer getValueAsColorInt(String key) {
    String value = getValue(key);
    if (value == null) {
      return null;
    }
    int iValue = (Integer.parseInt(value) * 32);
    if (iValue == 256) {
      iValue -= 1;
    }
    return iValue;
  }

  @Override
  public void parseData(JSONObject jsonObject) {
    super.parseData(jsonObject);
    data = jsonObject;
  }

}

