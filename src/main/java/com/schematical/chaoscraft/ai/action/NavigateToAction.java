package com.schematical.chaoscraft.ai.action;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public abstract class NavigateToAction extends ActionBase{
    private MixItUpAction currMixItUpAction = null;
    protected BlockPos lastCheckPos;
    private int stuckTicks = 0;
    private int strafe = 0;
    private int stuckThreshold =  3 * 20;

    protected boolean isStuck(){
        return stuckTicks > stuckThreshold;
    }

    public void tickNavigate(){
        getOrgEntity().getMoveHelper().strafe(2, strafe);
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
        tickStuckCheck();
    }
    public void stopWalking(){
        getOrgEntity().getMoveHelper().strafe(0, 0);
    }
    public void tickLook(){

        Vec3d pos = getTarget().getTargetPositionCenter();

        this.getOrgEntity(). setDesiredLookPosition(pos);

    }
    public void tickStuckCheck(){
        if(lastCheckPos == null){
            lastCheckPos = getOrgEntity().getPosition();
        }else {
            if (lastCheckPos.equals(getOrgEntity().getPosition())) {
                stuckTicks += 1;
            }else{
                stuckTicks = 0;
            }
        }
        if(isStuck()){
            //Mix it up
            MixItUpAction[] actions = MixItUpAction.values();
            int index = (int) Math.floor(Math.random() * actions.length);
            currMixItUpAction = actions[index];
            stuckTicks = 0;
        }
        if(currMixItUpAction == null){
            return;
        }
        if(currMixItUpAction.equals(MixItUpAction.Jump)){
            getOrgEntity().jump();
            return;
        }
        if(currMixItUpAction.equals(MixItUpAction.StrafeLeft)){
            strafe = -1;
            return;
        }
        if(currMixItUpAction.equals(MixItUpAction.StrafeLeft)){
            strafe = 1;
            return;
        }
    }
    public void tickArrived(){

        tickStuckCheck();
    }
    public enum MixItUpAction{
        StrafeLeft,
        StrafeRight,
        Jump
    }

}
