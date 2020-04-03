package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.BlockState;
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
        BlockRayTraceResult rayTraceResult = getOrgEntity().rayTraceBlocks(getOrgEntity().REACH_DISTANCE);
        if(rayTraceResult == null){
            markFailed();
            return;
        }
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
        ItemStack itemStack = orgEntity.getHeldItem(Hand.MAIN_HAND);
        if(itemStack.isEmpty()){
            return false;
        }
        if(!(itemStack.getItem() instanceof BlockItem)){
            return false;
        }
        /*if(chaosTarget.isSurroundedBySolid(orgEntity.world)){
            return false;
        }*/
        if(chaosTarget.isVisiblyBlocked(orgEntity)){
            return false;
        }
        return true;
    }
}
