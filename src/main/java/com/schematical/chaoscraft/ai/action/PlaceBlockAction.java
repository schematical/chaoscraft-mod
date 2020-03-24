package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.util.ChaosTarget;
import net.minecraft.util.math.BlockRayTraceResult;

public class PlaceBlockAction extends NavigateToAction{

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
            markFailed();
            return;
        }
        //When looking at stuff do stuff.
        getOrgEntity().rightClick(rayTraceResult);
        if(!target.getTargetEntity().isAlive()){
            markCompleted();
        }
    }


    public static boolean validateTarget(ChaosTarget chaosTarget) {
        return chaosTarget.getTargetEntity() != null;
    }
}
