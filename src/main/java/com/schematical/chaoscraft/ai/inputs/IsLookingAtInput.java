package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.Eye;
import com.schematical.chaoscraft.util.PositionRange;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;
import scala.actors.Debug;

/**
 * Created by user1a on 12/8/18.
 */
public class IsLookingAtInput extends InputNeuron {
    private static final String BLOCK_ID = "BLOCK_ID";
    private static final String ENTITY_ID = "ENTITY_ID";
    public String attributeId;
    public String attributeValue;
    public Eye eye;

    //public PositionRange positionRange;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range
        float value = -1;

        switch(attributeId){
            case(BLOCK_ID):
                RayTraceResult rayTraceResult = nNet.entity.rayTraceBlocks(3d);

                Block block = nNet.entity.world.getBlockState(rayTraceResult.getBlockPos()).getBlock();
                int blockId = Block.getIdFromBlock(block);
                if(blockId == Integer.parseInt(attributeValue)){
                    value = 1;
                }
            break;

        }
        return value;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();
        /*for(int i = 0; i < nNet.biology.size(); i++){
            BiologyBase biologyBase = nNet.biology.get(i);
            if(biologyBase instanceof Eye){
                Eye eyeTmp = (Eye) biologyBase;
                JSONObject eyeData = (JSONObject)jsonObject.get("eye");
                int eyeIndex = Integer.parseInt(eyeData.get("index").toString());
                if(eyeTmp.index == eyeIndex){
                    eye = eyeTmp;
                }
            }
        }
        if(eye == null){
            Debug.error("Invalid Eye Index: " + jsonObject.get("index"));
        }*/

    }

}
