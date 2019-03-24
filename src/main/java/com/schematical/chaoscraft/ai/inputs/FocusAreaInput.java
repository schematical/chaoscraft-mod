package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.AreaOfFocus;
import com.schematical.chaoscraft.ai.biology.Eye;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;
import scala.actors.Debug;

import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public class FocusAreaInput extends InputNeuron {

    protected AreaOfFocus areaOfFocus;
    public String attributeId;
    public String attributeValue;
    public float x;
    public float y;
    public float z;
    private int viewRange;

    //public PositionRange positionRange;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range
        if(this.nNet.entity.getDebug()){
            //ChaosCraft.logger.info("Debugging...");
        }
        if(areaOfFocus == null){
            areaOfFocus = (AreaOfFocus)nNet.getBiology("AreaOfFocus_0");
        }

        switch(attributeId){
            case(CCAttributeId.BLOCK_ID):
                CCObserviableAttributeCollection observiableAttributeCollection = areaOfFocus.canSeenBlock(x, y, z);
                if(observiableAttributeCollection.resourceId.equals(attributeValue)){
                    _lastValue = 1;
                }
            break;
            case(CCAttributeId.ENTITY_ID):
                List<CCObserviableAttributeCollection> attributeCollections =  areaOfFocus.canSeenEntities(x, y, z);
                for(CCObserviableAttributeCollection attributeCollection: attributeCollections) {
                    if (attributeCollection.resourceId.equals(attributeValue)) {
                        _lastValue = 1;
                    }
                }

            break;

        }
        return _lastValue;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();
        x = Float.parseFloat(jsonObject.get("x").toString());
        y = Float.parseFloat(jsonObject.get("y").toString());
        z = Float.parseFloat(jsonObject.get("z").toString());
        if(jsonObject.get("viewRange") != null) {
            viewRange = Integer.parseInt(jsonObject.get("viewRange").toString());
        }else{
            viewRange = 1;
        }


    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.attributeId + " " + this.attributeValue + " " + this.x + "," + this.y + "," + this.z;
        return response;

    }

}
