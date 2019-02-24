package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.Enum;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.Eye;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.Block;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.json.simple.JSONObject;
import scala.actors.Debug;

/**
 * Created by user1a on 12/8/18.
 */
public class PlaceBlockOutput extends OutputNeuron {
    public String attributeId;
    public String attributeValue;
    @Override
    public void execute() {
       /* if(nNet.entity.getDebug()) {
            ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Attempting to Place Block: " + attributeValue);
        }*/
        if(this._lastValue <= .5){
            return;
        }

        RayTraceResult rayTraceResult = nNet.entity.rayTraceBlocks(nNet.entity.REACH_DISTANCE);
        if(rayTraceResult == null){
            return;
        }
       /* Block block = null;
        switch(attributeId){
            case(Enum.BLOCK_ID):
                block = Block.getBlockFromName(attributeValue);
                if(block == null){
                   ChaosCraft.logger.error("Cannot find block from `Block.getBlockFromName('" + attributeValue + "');");
                    return;
                }
                //block = Block.getBlockById(Integer.parseInt(attributeValue));
            break;
            default:
                ChaosCraft.logger.error("Invalid `attributeId`: " + attributeId);
                return;
        }*/

        Vec3i vec3i = rayTraceResult.sideHit.getDirectionVec();
        BlockPos destBlockPos = rayTraceResult.getBlockPos().add(vec3i);

        nNet.entity.placeBlock(destBlockPos);
    }
    /*@Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();

    }
*/
}
