package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.util.ChaosTarget;

public class MeleeAttackAction extends NavigateToAction{

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

        //When looking at stuff do stuff.
        getOrgEntity().attackEntityAsMob(target.getTargetEntity());
        if(!target.getTargetEntity().isAlive()){
            markCompleted();
        }
    }


    public static boolean validateTarget(ChaosTarget chaosTarget) {
        return chaosTarget.getTargetEntity() != null;
    }
}
