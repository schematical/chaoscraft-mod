package com.schematical.chaoscraft.ai.biology;

import com.schematical.chaoscraft.util.PositionRange;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/25/18.
 */
public class Eye extends BiologyBase{
    public PositionRange positionRange;
    @Override
    public void parseData(JSONObject jsonObject){

        positionRange = new PositionRange();
        positionRange.parseData((JSONObject) jsonObject.get("positionRange"));
    }

}
