package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class TossItemAction extends NavigateToAction{

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
        //When looking at stuff do stuff.
        getOrgEntity().func_226292_a_(Hand.MAIN_HAND, true);
        ItemStack itemStack = getOrgEntity().tossEquippedStack(getTarget().getPosition());
        if(
            itemStack == null ||
            itemStack.isEmpty()
        ){
            markFailed();
            return;
        }

        markCompleted();
        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TOSSED_EQUIPPED_STACK);
        worldEvent.item = itemStack.getItem();
        getOrgEntity().getServerOrgManager().test(worldEvent);
    }
    public static boolean validateTargetItem(OrgEntity orgEntity, ChaosTargetItem chaosTargetItem) {
        if(chaosTargetItem.getInventorySlot() == null){
            return false;
        }
        ItemStack itemStack = orgEntity.getItemHandler().getStackInSlot(chaosTargetItem.getInventorySlot());
        if(itemStack.isEmpty()){
            return false;
        }

        return true;
    }

    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {
        if(orgEntity.world.getBlockState(chaosTarget.getPosition()).isSolid()){
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
