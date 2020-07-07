package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import com.sun.javafx.geom.Vec2d;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class WonderAction extends NavigateToAction {
    protected int ticksSinceLastChange = 0;
    protected int ticksSinceLastChangeThreshold = 15 * 20;
    protected int range = 50;
    protected int range_min = 10;
    private int shuffleBlockCoolDown = 0;
    private boolean debug;

    public void tickFirst() {
        shuffleBlockPos();

        super.tickFirst();


    }
    @Override
    protected void _tick() {
        ticksSinceLastChange += 1;
        if(getTarget() == null){
            shuffleBlockPos();
            return;
        }
        if(ticksSinceLastChange > ticksSinceLastChangeThreshold){
            shuffleBlockPos();
            ticksSinceLastChange = 0;
            return;
        }



        if(
            !getTarget().canEntityTouch(getOrgEntity())//, OrgEntity.ATTACK_DISTANCE)
        ){
            tickNavigate();
            return;
        }
        tickArrived();
        if(debug) {
            //ChaosCraft.LOGGER.info("Arrived at block");
        }
        if(!getTarget().isEntityLookingAt(getOrgEntity())) {
            this.tickLook();
            return;
        }
        if(debug) {
            //ChaosCraft.LOGGER.info("Looking at block");
        }


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
        if(this.shuffleBlockCoolDown > 0){
            this.shuffleBlockCoolDown -= 1;
            return;
        }
        this.shuffleBlockCoolDown = 20;
       /* boolean found = false;

        for(int x = range * -1; x < range; x++){
            for(int y = 2; y < 5; y++){
                for(int z = range * -1; z < range; z++){
                    if(!found){
                        BlockPos searchingBlockPos = getOrgEntity().getPosition().add(x, y, z);
                        Block block = getOrgEntity().world.getBlockState(searchingBlockPos).getBlock();

                        if(
                            block instanceof BeehiveBlock ||
                            block.getRegistryName().toString().equals("minecraft:bee_hive")

                        ){
                            setTarget(new ChaosTarget(searchingBlockPos));
                            found = true;
                            debug = true;
                        }


                    }
                }
            }
        }

        if(!found) {*/
            BlockPos pos = null;
            int saftyCatch = 0;
            while(pos == null && saftyCatch < 10) {
                saftyCatch += 1;
                if(saftyCatch > 10){
                    throw new ChaosNetException("Could not find a block to wonder too");
                }
                pos = this.getOrgEntity().getPosition().add(
                        (int) (Math.round(Math.random() * range * 2) - range),
                        (int) (Math.round(Math.random() * 1 * 2) - 1),
                        (int) (Math.round(Math.random() * range * 2) - range)
                );
                if(!this.getOrgEntity().getNavigator().canEntityStandOnPos(pos)){
                    pos = null;
                }
            }
            if(pos == null){
                setTarget(new ChaosTarget(this.getOrgEntity().getPosition()));
            }else {
                setTarget(new ChaosTarget(pos));
            }
        //}
    }


    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {

        return true;
    }
}
