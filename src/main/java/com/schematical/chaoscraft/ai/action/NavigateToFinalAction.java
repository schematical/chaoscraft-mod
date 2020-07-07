package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import net.minecraft.util.Hand;

public class NavigateToFinalAction extends NavigateToAction {

    @Override
    protected void _tick() {

        if (
            !getTarget().canEntityTouch(getOrgEntity(), OrgEntity.ATTACK_DISTANCE - 1)
        ) {
            tickNavigate();
        }
        markCompleted();
        //return;
    }


    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {

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
