package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.Enum;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.Eye;
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
        if(this._lastValue <= 0){
            return;
        }
        nNet.entity.swingArm(EnumHand.MAIN_HAND);
        RayTraceResult rayTraceResult = nNet.entity.rayTraceBlocks(nNet.entity.REACH_DISTANCE);
        Block block = null;
        switch(attributeId){
            case(Enum.BLOCK_ID):
                block = Block.getBlockById(Integer.parseInt(attributeValue));
            break;
            default:
                ChaosCraft.logger.error("Invalid `attributeId`: " + attributeId);
        }
        Vec3i vec3i = rayTraceResult.sideHit.getDirectionVec();
        BlockPos destBlockPos = rayTraceResult.getBlockPos().add(vec3i);
        nNet.entity.placeBlock(destBlockPos, block);
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();

    }

}
