package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.Enum;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public abstract class NavigateToAction extends ActionBase{
    private MixItUpAction currMixItUpAction = null;
    protected BlockPos lastCheckPos;
    private int stuckTicks = 0;
    private int strafe = 0;
    private int stuckThreshold =  2 * 20;
    private int stuckActionTicks = 0;
    private int ticksUntilUpdatePath = 20;
    private PathNavigator navigator;
    protected boolean isStuck(){
        return stuckTicks > stuckThreshold;
    }
    public void tickFirst(){
        super.tickFirst();
        if(getTarget() == null){
            throw new ChaosNetException("Missing Target in Action: " + getClass().getSimpleName());
        }
        boolean results = false;
        if(getTarget().getTargetEntity() != null) {
            results = this.getOrgEntity().getNavigator()
                    .tryMoveToEntityLiving(getTarget().getTargetEntity(), 1.0D);
        }else if(getTarget().getTargetBlockPos() != null){
            World world = getOrgEntity().world;
            BlockPos targetBlockPos = getTarget().getTargetBlockPos();
            BlockState blockState = world.getBlockState(getTarget().getTargetBlockPos());
            if(blockState.isSolid()){
                for (Direction direction : Enum.getDirections()) {
                    BlockPos newBlockPos =  getTarget().getTargetBlockPos().offset(direction);
                    BlockState newBlockState = world.getBlockState(newBlockPos);
                    if(!newBlockState.isSolid()){
                        targetBlockPos = newBlockPos;
                    }
                }
            }
            results = this.getOrgEntity().getNavigator()
                    .tryMoveToXYZ(
                            (double)((float)targetBlockPos.getX()) + 0.5D,
                            (double)(targetBlockPos.getY() + 1),
                            (double)((float)targetBlockPos.getZ()) + 0.5D,
                    1d
                    );
        }else{
            throw new ChaosNetException("TODO: Make this work");
        }
        if(!results){
            this.markFailed();
        }
    }
    public void tickNavigate(){
        ticksUntilUpdatePath += 1;
        if(ticksUntilUpdatePath > 20){
            ticksUntilUpdatePath = 0;
            if(getTarget().getTargetEntity() != null) {
                boolean results = this.getOrgEntity().getNavigator()
                        .tryMoveToEntityLiving(getTarget().getTargetEntity(), 1.0D);
            }
            this.getOrgEntity().getNavigator().updatePath();
        }
        /*tickStuckCheck();
        if(!isStuck()) {*/
            //this.getOrgEntity().getNavigator().tick();
        //}

    }
    /*public void tickNavigateSimple(){
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
    }*/
    public void stopWalking(){
        getOrgEntity().getMoveHelper().strafe(0, 0);
    }
    public void tickLook(){
/*
        Vec3d pos = getTarget().getTargetPositionCenter();

        this.getOrgEntity(). setDesiredLookPosition(pos);*/

    }
    public void tickStuckCheck(){
        if(lastCheckPos == null){
            lastCheckPos = getOrgEntity().getPosition();
            stuckTicks = 0;
        }else {
            if (lastCheckPos.equals(getOrgEntity().getPosition())) {
                stuckTicks += 1;

            }else{
                stuckTicks = 0;
                stuckActionTicks = 0;
                return;
            }
        }
        if(!isStuck()){
            return;
        }
        if(
                currMixItUpAction == null ||
                stuckActionTicks > stuckThreshold
        ){
            //Mix it up
            MixItUpAction[] actions = MixItUpAction.values();
            int index = (int) Math.floor(Math.random() * actions.length);
            currMixItUpAction = actions[index];
            stuckActionTicks = 0;
        }else{
            stuckActionTicks += 1;
        }

        if(currMixItUpAction.equals(MixItUpAction.Jump)){
            getOrgEntity()._jump();
            return;
        }else if(currMixItUpAction.equals(MixItUpAction.StrafeLeft)){
            strafe = -1;
            getOrgEntity().getMoveHelper().strafe(0, strafe);
            return;
        }else if(currMixItUpAction.equals(MixItUpAction.StrafeRight)){
            strafe = 1;
            getOrgEntity().getMoveHelper().strafe(0, strafe);
            return;
        } else if(currMixItUpAction.equals(MixItUpAction.BackUp)){
            strafe = 1;
            getOrgEntity().getMoveHelper().strafe(-1, 0);
            return;
        }else{
            throw new ChaosNetException("No idea what `currMixItUpAction` is : " + currMixItUpAction.toString());
        }
    }
    public void tickArrived(){

       // tickStuckCheck();
    }
    public enum MixItUpAction{
        StrafeLeft,
        StrafeRight,
        Jump,
        BackUp
    }

}
