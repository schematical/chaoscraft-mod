package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import net.minecraft.util.Hand;

public class MeleeAttackAction extends NavigateToAction{

    @Override
    protected void _tick() {

        if(
            !getTarget().canEntityTouch(getOrgEntity(), OrgEntity.ATTACK_DISTANCE - 1)
        ){
            tickNavigate();
            return;
        }

        tickArrived();
        if(!getTarget().isEntityLookingAt(getOrgEntity())){
            tickLook();
            return;
        }
        //When looking at stuff do stuff.
        getOrgEntity().func_226292_a_(Hand.MAIN_HAND, true);
        if(!getTarget().isVisiblyBlocked(getOrgEntity())) {
            getOrgEntity().attackEntityAsMob(getTarget().getTargetEntity());
        }
        if(!getTarget().getTargetEntity().isAlive()){
            markCompleted();
        }
    }


    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {
        if(chaosTarget.getTargetEntity() == null){
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
