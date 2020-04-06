package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import net.minecraft.util.Hand;

public class MeleeAttackAction extends NavigateToAction{

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
        stopWalking();
        tickArrived();
        //When looking at stuff do stuff.
        getOrgEntity().func_226292_a_(Hand.MAIN_HAND, true);
        getOrgEntity().attackEntityAsMob(getTarget().getTargetEntity());
        if(!getTarget().getTargetEntity().isAlive()){
            markCompleted();
        }
    }


    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {
        if(chaosTarget.getTargetEntity() == null){
            return false;
        }
        if(chaosTarget.isVisiblyBlocked(orgEntity)){
            return false;
        }
        return true;
    }
}
