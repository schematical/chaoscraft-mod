package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ai.memory.BlockStateMemoryBufferSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

public class PlaceBlockAction extends NavigateToAction{

    @Override
    protected void _tick() {

        if(
            !getTarget().canEntityTouch(getOrgEntity()) /*||
            getTarget().isVisiblyBlocked(getOrgEntity())*/
        ){
            tickNavigate();
            return;
        }
        tickArrived();
        if(!getTarget().isEntityLookingAt(getOrgEntity())){
            tickLook();
            return;
        }

        BlockRayTraceResult rayTraceResult = getOrgEntity().rayTraceBlocks(getOrgEntity().REACH_DISTANCE);
        if(rayTraceResult == null){
            markFailed();
            return;
        }
    /*    if(!validateTargetItem(getOrgEntity(),  new ChaosTargetItem(getOrgEntity().getSelectedItemIndex()))){
            throw new ChaosNetException("Invalid Target");
        }*/
        ItemStack itemStack = getOrgEntity().getHeldItem(Hand.MAIN_HAND);
        //When looking at stuff do stuff.
        ActionResultType result = getOrgEntity().rightClick(rayTraceResult);
        if(result.equals(ActionResultType.SUCCESS)) {
            BlockPos placedBlockPos = rayTraceResult.getPos().offset(rayTraceResult.getFace());
            BlockState blockState = getOrgEntity().world.getBlockState(placedBlockPos);
            if (blockState.isSolid()) {
                setCorrectedChaosTarget(new ChaosTarget(placedBlockPos));
                markCompleted();
                return;
            } /*else {
                // markFailed();//TODO: figure out how this is possible
                throw new ChaosNetException("Something went wrong. The placed block area is empty. Was trying to place: " + itemStack.getItem().getRegistryName().toString());
            }*/
        }else if(result.equals(ActionResultType.FAIL)){
            markFailed();
            return;
        }
    }
    public void onClientMarkCompleted() {
        ClientOrgManager clientOrgManager = (ClientOrgManager) this.getActionBuffer().getOrgManager();
        /*BlockRayTraceResult rayTraceResult = getOrgEntity().rayTraceBlocks(getOrgEntity().REACH_DISTANCE);
        if(rayTraceResult == null){
            throw new ChaosNetException("null rayTraceResult onClientMarkCompleted");
        }

        BlockPos placedBlockPos = rayTraceResult.getPos().offset(rayTraceResult.getFace());*/
        ChaosTarget correctedChaosTarget = this.getCorrectedChaosTarget();
        if(correctedChaosTarget == null){
            throw new ChaosNetException("`correctedChaosTarget` should not be null in `PlaceBlockAction`");
        }
        BlockStateMemoryBufferSlot blockStateMemoryBufferSlot = new BlockStateMemoryBufferSlot(correctedChaosTarget.getTargetBlockPos());
        blockStateMemoryBufferSlot.debugBlockPos = this.getTarget().getTargetBlockPos();
        blockStateMemoryBufferSlot.ownerEntityId = getOrgEntity().getEntityId();
        clientOrgManager.getBlockStateMemory().put(
            this.getCorrectedChaosTarget().getTargetBlockPos(),
            blockStateMemoryBufferSlot
        );
    }

    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {
        if(chaosTarget.getTargetBlockPos() == null){
            return false;
        }
        BlockState blockState = orgEntity.world.getBlockState(chaosTarget.getTargetBlockPos());


        Material material = blockState.getMaterial();
        if(
                material == Material.WATER ||
                material == Material.AIR ||
                material == Material.LAVA
        ){
            return false;
        }
        return true;
    }
    public static boolean validateTargetItem(OrgEntity orgEntity,  ChaosTargetItem chaosTargetItem) {
        if(chaosTargetItem.getInventorySlot() == null){
            return false;
        }
        ItemStack itemStack = orgEntity.getItemHandler().getStackInSlot(chaosTargetItem.getInventorySlot());
        if(
            itemStack == null ||
            itemStack.isEmpty()
        ){
            return false;
        }

        if(!(itemStack.getItem() instanceof BlockItem)){
            return false;
        }

        return true;
    }
    public static boolean validateTargetAndItem(OrgEntity orgEntity, ChaosTarget chaosTarget, ChaosTargetItem chaosTargetItem){
        if(
                validateTarget( orgEntity, chaosTarget) &&
                validateTargetItem( orgEntity, chaosTargetItem)
        ) {
            return true;
        }else{
            return false;
        }
    }
}
