package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.BlockPositionSensor;
import com.schematical.chaoscraft.util.PositionRange;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.json.simple.JSONObject;
import scala.actors.Debug;

/**
 * Created by user1a on 12/8/18.
 */
public class BlockPositionInput                                                                                                                          extends InputNeuron {

    public String attributeId;
    public String attributeValue;
    public BlockPositionSensor blockPositionSensor;

    //public PositionRange positionRange;

    @Override
    public float evaluate(){
        //Iterate through all blocks entities etc with in the range
        PositionRange adustedPositionRange = this.blockPositionSensor.positionRange.orientToEntity(this.nNet.entity);

        for(float x = adustedPositionRange.minX; x < adustedPositionRange.maxX && _lastValue != 1; x++){
            for(float y = adustedPositionRange.minY; y < adustedPositionRange.maxY && _lastValue != 1; y++){
                for(float z = adustedPositionRange.minZ; z < adustedPositionRange.maxZ && _lastValue != 1; z++){
                    BlockPos pos = new BlockPos(x,y,z);
                    Block block = this.nNet.entity.world.getBlockState(pos).getBlock();
                    switch(attributeId){
                        case(com.schematical.chaoscraft.Enum.BLOCK_ID):
                            ResourceLocation regeistryName = block.getRegistryName();
                            String key = regeistryName.getResourceDomain() + ":" + regeistryName.getResourcePath();
                            if(attributeValue.equals(key)){
                                _lastValue = 1;
                            }

                            /*int blockId = Block.getIdFromBlock(block);
                            if(blockId == Integer.parseInt(attributeValue)){
                                _lastValue = 1;
                            }*/
                        break;
                    }
                }
            }
        }
        return _lastValue;
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();
        for(int i = 0; i < nNet.biology.size(); i++){
            BiologyBase biologyBase = nNet.biology.get(i);
            if(biologyBase instanceof BlockPositionSensor){
                BlockPositionSensor blockPositionSensorTmp = (BlockPositionSensor) biologyBase;
                JSONObject eyeData = (JSONObject)jsonObject.get("blockPositionSensor");
                int eyeIndex = Integer.parseInt(eyeData.get("index").toString());
                if(blockPositionSensorTmp.index == eyeIndex){
                    blockPositionSensor = blockPositionSensorTmp;
                }
            }
        }
        if(blockPositionSensor == null){
            Debug.error("Invalid BlockPositionSensor Index: " + jsonObject.get("index"));
        }

    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.attributeId + " " + this.attributeValue;
        return response;

    }


}
