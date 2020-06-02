package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;

public class DigBlockAction extends NavigateToAction{

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
            return;
        }
        ActionResultType actionResultType = getOrgEntity().dig(getTarget().getTargetBlockPos());//rayTraceResult.getPos());
        if(actionResultType.equals(ActionResultType.FAIL)){
            markFailed();
            return;
        }
        if(actionResultType.equals(ActionResultType.SUCCESS)){
            markCompleted();
            return;
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
        float hardness = blockState.getBlockHardness(orgEntity.world, chaosTarget.getTargetBlockPos());
        if(hardness == -1.0F){
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
