package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public abstract class NavigateToAction extends ActionBase{

    public void tickNavigate(){
        getOrgEntity().getMoveHelper().strafe(2, 0);
        Double deltaYaw = target.getYawDelta(getOrgEntity());
        if(deltaYaw == null){
            return;
        }
        if(deltaYaw > 30){
            deltaYaw = 30d;
        }else if(deltaYaw < -30){
            deltaYaw = -30d;
        }
        this.getOrgEntity().setDesiredYaw(this.getOrgEntity().rotationYaw + deltaYaw);

    }
    public void tickLook(){
        getOrgEntity().getMoveHelper().strafe(0, 0);
        Vec3d pos = target.getTargetPositionCenter();

        this.getOrgEntity().setDesiredLookPosition(pos);


        //!!!NOT SURE IF ANYTHING BELOW THIS WORKS
        Double deltaPitch = target.getPitchDelta(getOrgEntity());

        if(deltaPitch > 5d){
            deltaPitch = 5d;
        }else if(deltaPitch < -5d){
            deltaPitch = -5d;
        }

        getOrgEntity().setDesiredPitch(getOrgEntity().rotationPitch + deltaPitch);
    }

}
