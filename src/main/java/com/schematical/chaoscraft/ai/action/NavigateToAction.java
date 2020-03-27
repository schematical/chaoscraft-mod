package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public abstract class NavigateToAction extends ActionBase{

    public void tickNavigate(){
        getOrgEntity().getMoveHelper().strafe(2, 0);
        Double deltaYaw = getTarget().getYawDelta(getOrgEntity());
        if(deltaYaw == null){
            markFailed();
            return;
        }
        if(deltaYaw > 30){
            deltaYaw = 30d;
        }else if(deltaYaw < -30){
            deltaYaw = -30d;
        }
        this.getOrgEntity().setDesiredYaw(this.getOrgEntity().rotationYaw + deltaYaw);

    }
    public void stopWalking(){
        getOrgEntity().getMoveHelper().strafe(0, 0);
    }
    public void tickLook(){

        Vec3d pos = getTarget().getTargetPositionCenter();

        this.getOrgEntity(). setDesiredLookPosition(pos);

    }

}
