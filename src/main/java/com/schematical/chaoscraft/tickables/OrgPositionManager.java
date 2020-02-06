package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.event.world.WorldEvent;

import java.util.ArrayList;

public class OrgPositionManager implements iChaosOrgTickable {
    public Vec3d startPos;
    public Vec3d lastCheckPos;
    public Vec3d maxDist = new Vec3d(0,0,0);
    public ArrayList<Vec3i> touchedBlocks = new ArrayList<Vec3i>();
    @Override
    public void Tick(BaseOrgManager orgManager) {
        boolean isServer = orgManager instanceof ServerOrgManager;
        if( this.startPos == null){
            this.startPos = orgManager.getEntity().getPositionVec();
            this.lastCheckPos = this.startPos;

            Vec3i vec = new Vec3i(
                    (int)this.lastCheckPos.x,
                    (int)this.lastCheckPos.y,
                    (int)this.lastCheckPos.z
            );
            boolean hasTouchedBlock = touchedBlocks.contains(vec);
            if(isServer){
                CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TOUCHED_BLOCK);
                worldEvent.amount = 1;
                worldEvent.position = vec;
                worldEvent.blockTouchedState = hasTouchedBlock ? CCWorldEvent.BlockTouchedState.HAS_TOUCHED : CCWorldEvent.BlockTouchedState.HAS_NOT_TOUCHED;
                orgManager.getEntity().entityFitnessManager.test(worldEvent);
            }
            if(!hasTouchedBlock){
                touchedBlocks.add(vec);
            }
        }

        Vec3d currPos = orgManager.getEntity().getPositionVec();
        if(
                !this.lastCheckPos.equals(currPos)
        ){
            this.lastCheckPos = currPos;
            Vec3d diffVec = this.lastCheckPos.subtract(this.startPos);
            if(Math.round(Math.abs(diffVec.x))> this.maxDist.x){
                this.maxDist = new Vec3d(
                        Math.ceil(Math.abs(diffVec.x)),
                        this.maxDist.y,
                        this.maxDist.z
                );
                if(isServer) {
                    CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TRAVEL_ALONG_AXIS);
                    worldEvent.amount = 1;
                    worldEvent.axis = CCWorldEvent.Axis.X;
                    orgManager.getEntity().entityFitnessManager.test(worldEvent);
                }
            }
            if(Math.round(Math.abs(diffVec.y)) > this.maxDist.y){
                this.maxDist = new Vec3d(
                        this.maxDist.x,
                        Math.ceil(Math.abs(diffVec.y)),
                        this.maxDist.z
                );
                if(isServer) {
                    CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TRAVEL_ALONG_AXIS);
                    worldEvent.amount = 1;
                    worldEvent.axis = CCWorldEvent.Axis.Y;
                    orgManager.getEntity().entityFitnessManager.test(worldEvent);
                }
            }
            if(Math.round(Math.abs(diffVec.z)) > this.maxDist.z){
                this.maxDist = new Vec3d(
                        this.maxDist.x,
                        this.maxDist.y,
                        Math.ceil(Math.abs(diffVec.z))
                );
                if(isServer) {
                    CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TRAVEL_ALONG_AXIS);
                    worldEvent.amount = 1;
                    worldEvent.axis = CCWorldEvent.Axis.Z;
                    orgManager.getEntity().entityFitnessManager.test(worldEvent);
                }
            }
        }
    }
    public boolean hasTouchedBlock(Vec3d vec3d){
        Vec3i vec = new Vec3i(
                (int)this.lastCheckPos.x,
                (int)this.lastCheckPos.y,
                (int)this.lastCheckPos.z
        );
        return hasTouchedBlock(vec);

    }
    public boolean hasTouchedBlock(Vec3i vec3i){
        return (this.touchedBlocks.contains(vec3i));
    }
}
