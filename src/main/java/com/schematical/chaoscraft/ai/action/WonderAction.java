package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
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

    public void tickFirst() {
        shuffleBlockPos();

        super.tickFirst();


    }
    @Override
    protected void _tick() {
        ticksSinceLastChange += 1;
        if(getTarget() == null){
            shuffleBlockPos();
        }
        if(ticksSinceLastChange > ticksSinceLastChangeThreshold){
            shuffleBlockPos();
            ticksSinceLastChange = 0;
        }else {
            Vec2d vec2d = new Vec2d(
                    getTarget().getPosition().getX() - getOrgEntity().getPosition().getX(),
                    getTarget().getPosition().getZ() - getOrgEntity().getPosition().getZ()
            );
            double dist = Math.sqrt(Math.pow(vec2d.x, 2) + Math.pow(vec2d.y, 2));
            if (dist < 3) {
                shuffleBlockPos();
                return;
            }
        }

        tickNavigate();
        tickLook();



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
        boolean found = false;

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
                        }


                    }
                }
            }
        }

        if(!found) {

            BlockPos pos = this.getOrgEntity().getPosition().add(
                    (int)(Math.round(Math.random() * range * 2) - range),
                    (int)(Math.round(Math.random() * 1 * 2) - 1),
                    (int)(Math.round(Math.random() * range * 2) - range)
            );
            setTarget(new ChaosTarget(pos));
        }
    }


    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {

        return true;
    }
}
