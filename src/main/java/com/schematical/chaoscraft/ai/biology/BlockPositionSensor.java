package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.util.PositionRange;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/25/18.
 */
public class BlockPositionSensor extends BiologyBase{
    public int index;
    public PositionRange positionRange;
    public boolean _cached = false;
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

        positionRange = new PositionRange();
        positionRange.parseData((JSONObject) jsonObject.get("positionRange"));
    }
    @Override
    public void reset() {
        _cached = false;
    }

}
