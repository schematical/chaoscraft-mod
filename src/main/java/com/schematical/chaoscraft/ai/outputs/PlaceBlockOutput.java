package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;

import static com.schematical.chaoscraft.ChaosCraft.LOGGER;

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
        if(this.getCurrentValue() <= .5f){
            return;
        }

        BlockRayTraceResult rayTraceResult = nNet.entity.rayTraceBlocks(nNet.entity.REACH_DISTANCE);
        if(rayTraceResult == null){
            return;
        }

        LOGGER.info("Placed Block on    : " + Minecraft.getInstance().world.getBlockState(rayTraceResult.getPos()).getBlock() + " at X: " + rayTraceResult.getPos().getX() + " Y: "
                + rayTraceResult.getPos().getY() + " Z: " + rayTraceResult.getPos().getZ());

        Vec3i vec3i = rayTraceResult.getFace().getDirectionVec();
        BlockPos destBlockPos = rayTraceResult.getPos().add(vec3i);

        nNet.entity.rightClick(rayTraceResult);

    }
    /*@Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();

    }
*/
}
