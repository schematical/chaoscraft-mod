package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;

import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

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
            return getCurrentValue();
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
        if(this.getCurrentValue() <= .5){
            return;
        }
        nNet.entity.setActiveHand(Hand.MAIN_HAND);
        ItemStack itemStack = nNet.entity.getHeldItemMainhand();
        if(itemStack == null || itemStack.isEmpty()){
            return;
        }
        Item heldItem = itemStack.getItem();
        ActionResult<ItemStack> rcResult = heldItem.onItemRightClick(
            nNet.entity.world,
            nNet.entity.getPlayerWrapper(),
                Hand.MAIN_HAND
        );
        if(rcResult.getType().equals(ActionResultType.SUCCESS)){
            ChaosCraft.LOGGER.debug(nNet.entity.getCCNamespace() + " successfully rightClicked " + heldItem.getRegistryName().getNamespace());
        }else if(rcResult.getType().equals(ActionResultType.SUCCESS)){
            ChaosCraft.LOGGER.debug(nNet.entity.getCCNamespace() + " failed to rightClick " + heldItem.getRegistryName().getNamespace());
        }
        BlockRayTraceResult rayTraceResult = nNet.entity.rayTraceBlocks(nNet.entity.REACH_DISTANCE);
        if(rayTraceResult == null){
            return;
        }


        //Vec3i vec3i = rayTraceResult.sideHit.getDirectionVec();
        BlockPos destBlockPos = new BlockPos(rayTraceResult.getHitVec());//.add(vec3i);

        /*if(!(heldItem instanceof ItemBlock)){
            return;
        }*/
        ItemUseContext context = new ItemUseContext(
                nNet.entity.getPlayerWrapper(),
                Hand.MAIN_HAND,
                rayTraceResult
        );
        ActionResultType result = heldItem.onItemUse(
            context
        );
        if(result.equals(ActionResultType.SUCCESS)) {
            ChaosCraft.LOGGER.debug(nNet.entity.getCCNamespace() + " successfully used " + heldItem.getRegistryName().toString());
        }else if(result.equals(ActionResultType.FAIL)){
            ChaosCraft.LOGGER.debug(nNet.entity.getCCNamespace() + " failed to use " + heldItem.getRegistryName().toString());
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
