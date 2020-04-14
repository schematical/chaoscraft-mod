package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class WonderAction extends NavigateToAction{
    protected int ticksSinceLastChange = 0;
    protected int ticksSinceLastChangeThreshold = 15 * 20;
    protected int range = 50;

    @Override
    protected void _tick() {
        ticksSinceLastChange += 1;
        tickStuckCheck();
        if(getTarget() == null){
            shuffleBlockPos();
        }
        if(ticksSinceLastChange > ticksSinceLastChangeThreshold){
            shuffleBlockPos();
            ticksSinceLastChange = 0;
        }
        if(getTarget().getDist(getOrgEntity()) < 3){
            shuffleBlockPos();
        }
        tickLook();
        tickNavigate();

    }
    @Override
    public void markFailed(){
        actionAgeTicks = 0;
    }
    @Override
    public void markCompleted(){

    }
    @Override
    public void markRunning(){

    }
    public void shuffleBlockPos(){
        BlockPos pos = this.getOrgEntity().getPosition().add(
                (int)(Math.round(Math.random() * range * 2) - range),
                (int)(Math.round(Math.random() * 1 * 2) - 1),
                (int)(Math.round(Math.random() * range * 2) - range)
        );
        setTarget(new ChaosTarget(pos));
    }


    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {

        return true;
    }
}
