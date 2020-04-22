package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

public class PlaceBlockAction extends NavigateToAction{

    @Override
    protected void _tick() {
        tickLook();
        if(
                !getTarget().canEntityTouch(getOrgEntity()) &&
                !getTarget().isEntityLookingAt(getOrgEntity())
        ){
            tickNavigate();
            return;
        }
        tickArrived();
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
        getOrgEntity().rightClick(rayTraceResult);
        BlockPos placedBlockPos = rayTraceResult.getPos().offset(rayTraceResult.getFace());
        BlockState blockState = getOrgEntity().world.getBlockState(placedBlockPos);
        if(blockState.isSolid()){
            markCompleted();
        }else{
            markFailed();//TODO: figure out how this is possible
            //throw new ChaosNetException("Something went wrong. The placed block area is empty. Was trying to place: " + itemStack.getItem().getRegistryName().toString());
        }
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
