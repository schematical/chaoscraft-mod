package com.schematical.chaoscraft.ai.biology;

import org.json.simple.JSONObject;

/**
 * Created by user1a on 2/26/19.
 */
public class FloatMemory extends BiologyBase {
    public int index;
    public float _lastValue = -1;

    public void setValue(float _lastValue){
         this._lastValue = _lastValue;
    }
    public float getValue(){
        return this._lastValue;
    }

    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        index = Integer.parseInt(jsonObject.get("index").toString());

    }
    /*@Override
    public void reset() {

    }*/

}

