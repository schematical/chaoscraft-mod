package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;

public class DigBlockAction extends NavigateToAction{

    @Override
    protected void _tick() {
        if(!target.canEntityTouch(getOrgEntity())){
            tickNavigate();
            return;
        }
        //Attack stuff
        //Look at stuff
        if(!target.isEntityLookingAt(getOrgEntity())){
            tickLook();
            return;
        }

        BlockRayTraceResult rayTraceResult = getOrgEntity().rayTraceBlocks(getOrgEntity().REACH_DISTANCE);
        if(rayTraceResult == null){
            return;
        }
        ActionResultType actionResultType = getOrgEntity().dig(rayTraceResult.getPos());
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
        if(blockState.isAir(orgEntity.world, chaosTarget.getTargetBlockPos())){
            return false;
        }
        return true;
    }
}
