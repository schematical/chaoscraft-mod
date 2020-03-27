package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

public class UseItemAction extends NavigateToAction{

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

        ItemStack itemStack = getOrgEntity().getHeldItem(Hand.MAIN_HAND);
        Item heldItem = itemStack.getItem();
        ActionResult<ItemStack> rcResult = heldItem.onItemRightClick(
                getOrgEntity().world,
                getOrgEntity().getPlayerWrapper(),
                Hand.MAIN_HAND
        );
        if(rcResult.getType().equals(ActionResultType.SUCCESS)){
            ChaosCraft.LOGGER.debug(getOrgEntity().getCCNamespace() + " successfully rightClicked " + heldItem.getRegistryName().getNamespace());
        }else if(rcResult.getType().equals(ActionResultType.SUCCESS)){
            ChaosCraft.LOGGER.debug(getOrgEntity().getCCNamespace() + " failed to rightClick " + heldItem.getRegistryName().getNamespace());
        }

        BlockRayTraceResult rayTraceResult = getOrgEntity().rayTraceBlocks(getOrgEntity().REACH_DISTANCE);
        if(
                rayTraceResult == null
        ){
            markFailed();
            return;
        }


        //Vec3i vec3i = rayTraceResult.sideHit.getDirectionVec();
        BlockPos destBlockPos = new BlockPos(rayTraceResult.getHitVec());//.add(vec3i);

        /*if(!(heldItem instanceof ItemBlock)){
            return;
        }*/
        ItemUseContext context = new ItemUseContext(
                getOrgEntity().getPlayerWrapper(),
                Hand.MAIN_HAND,
                rayTraceResult
        );
        ActionResultType result = heldItem.onItemUse(
                context
        );
        if(result.equals(ActionResultType.SUCCESS)) {
            ChaosCraft.LOGGER.debug(getOrgEntity().getCCNamespace() + " successfully used " + heldItem.getRegistryName().toString());
        }else if(result.equals(ActionResultType.FAIL)){
            ChaosCraft.LOGGER.debug(getOrgEntity().getCCNamespace() + " failed to use " + heldItem.getRegistryName().toString());
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
       /* if(!(itemStack.getItem() instanceof BlockItem)){
            return false;
        }*/
        return true;
    }
}
