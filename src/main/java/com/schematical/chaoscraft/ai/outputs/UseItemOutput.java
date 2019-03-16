package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;

/**
 * Created by user1a on 12/8/18.
 */
public class UseItemOutput extends OutputNeuron {
    public String attributeId;
    public String attributeValue;
    @Override
    public float evaluate() {
        ItemStack itemStack = nNet.entity.getHeldItemMainhand();
        if(itemStack == null || itemStack.isEmpty()){
            return _lastValue;
        }
       /* Item heldItem = itemStack.getItem();
        if(!(heldItem instanceof ItemBlock)){
            return -1;
        }*/
        return super.evaluate();
    }
    @Override
    public void execute() {
       /* if(nNet.entity.getDebug()) {
            ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Attempting to Place Block: " + attributeValue);
        }*/
        if(this._lastValue <= .5){
            return;
        }
        nNet.entity.setActiveHand(EnumHand.MAIN_HAND);
        ItemStack itemStack = nNet.entity.getHeldItemMainhand();
        if(itemStack == null || itemStack.isEmpty()){
            return;
        }
        Item heldItem = itemStack.getItem();
        ActionResult<ItemStack> rcResult = heldItem.onItemRightClick(
            nNet.entity.world,
            nNet.entity.getPlayerWrapper(),
            EnumHand.MAIN_HAND
        );
        if(rcResult.getType().equals(EnumActionResult.SUCCESS)){
            ChaosCraft.chat(nNet.entity.getCCNamespace() + " successfully rightClicked " + heldItem.getRegistryName().toString());
        }else if(rcResult.getType().equals(EnumActionResult.SUCCESS)){
            ChaosCraft.chat(nNet.entity.getCCNamespace() + " failed to rightClick " + heldItem.getRegistryName().toString());
        }
        RayTraceResult rayTraceResult = nNet.entity.rayTraceBlocks(nNet.entity.REACH_DISTANCE);
        if(rayTraceResult == null){
            return;
        }


        //Vec3i vec3i = rayTraceResult.sideHit.getDirectionVec();
        BlockPos destBlockPos = rayTraceResult.getBlockPos();//.add(vec3i);

        /*if(!(heldItem instanceof ItemBlock)){
            return;
        }*/

        EnumActionResult result = heldItem.onItemUse(
            nNet.entity.getPlayerWrapper(),
            nNet.entity.world,
            destBlockPos,
            EnumHand.MAIN_HAND,
            rayTraceResult.sideHit,
            (float)rayTraceResult.hitVec.x,
            (float)rayTraceResult.hitVec.y,
            (float)rayTraceResult.hitVec.z
        );
        if(result.equals(EnumActionResult.SUCCESS)) {
            ChaosCraft.chat(nNet.entity.getCCNamespace() + " successfully used " + heldItem.getRegistryName().toString());
        }else if(result.equals(EnumActionResult.FAIL)){
            ChaosCraft.chat(nNet.entity.getCCNamespace() + " failed to use " + heldItem.getRegistryName().toString());
        }
    }
    /*@Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        attributeId = jsonObject.get("attributeId").toString();
        attributeValue = jsonObject.get("attributeValue").toString();

    }
*/
}
