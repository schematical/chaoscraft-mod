package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.ai.biology.iTargetable;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.DebugTargetHolder;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.lang.annotation.Target;
import java.util.ArrayList;

public class OrgPositionManager implements iChaosOrgTickable {
    public Vec3i startPos;
    public Vec3i lastCheckPos;
    public Vec3d maxDist = new Vec3d(0,0,0);
    public ArrayList<Vec3i> touchedBlocks = new ArrayList<Vec3i>();
    public int ticksSinceLastMove = 0;

    public int ticksWhileLookingAtTarget = 0;
    public boolean isLookingAtTarget = false;
    public float lookAtScore = 0;

    @Override
    public void Tick(BaseOrgManager orgManager) {
        boolean isServer = orgManager instanceof ServerOrgManager;
        if( this.startPos == null){
            Vec3d vec3d = orgManager.getEntity().getPositionVec();
            this.startPos =  new Vec3i(
                    (int)vec3d.getX(),
                    (int)vec3d.getY(),
                    (int)vec3d.getZ()
            );
            this.lastCheckPos = this.startPos;


        }
        ticksSinceLastMove += 1;

        Vec3d currPosD = orgManager.getEntity().getPositionVec();
        Vec3i currPos = new Vec3i(
                (int)currPosD.x,
                (int)currPosD.y,
                (int)currPosD.z
        );
        if(
            !this.lastCheckPos.equals(currPos)
        ){
            ticksSinceLastMove = 0;
            this.lastCheckPos = currPos;
            Vec3i diffVec = new Vec3i(
                    this.lastCheckPos.getX() - this.startPos.getX(),
                    this.lastCheckPos.getY() - this.startPos.getY(),
                    this.lastCheckPos.getZ() - this.startPos.getZ()
            );
            if(Math.round(Math.abs(diffVec.getX()))> this.maxDist.x){
                this.maxDist = new Vec3d(
                        Math.ceil(Math.abs(diffVec.getX())),
                        this.maxDist.y,
                        this.maxDist.z
                );
                if(isServer) {
                    CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TRAVEL_ALONG_AXIS);
                    worldEvent.amount = 1;
                    worldEvent.axis = CCWorldEvent.Axis.X;
                    ((ServerOrgManager)orgManager).test(worldEvent);
                }
            }
            if(Math.round(Math.abs(diffVec.getY())) > this.maxDist.y){
                this.maxDist = new Vec3d(
                        this.maxDist.x,
                        Math.ceil(Math.abs(diffVec.getY())),
                        this.maxDist.z
                );
                if(isServer) {
                    CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TRAVEL_ALONG_AXIS);
                    worldEvent.amount = 1;
                    worldEvent.axis = CCWorldEvent.Axis.Y;
                    ((ServerOrgManager)orgManager).test(worldEvent);
                }
            }
            if(Math.round(Math.abs(diffVec.getZ())) > this.maxDist.z){
                this.maxDist = new Vec3d(
                        this.maxDist.x,
                        this.maxDist.y,
                        Math.ceil(Math.abs(diffVec.getZ()))
                );
                if(isServer) {
                    CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TRAVEL_ALONG_AXIS);
                    worldEvent.amount = 1;
                    worldEvent.axis = CCWorldEvent.Axis.Z;
                    ((ServerOrgManager)orgManager).test(worldEvent);
                }
            }


            boolean hasTouchedBlock = touchedBlocks.contains(this.lastCheckPos);
            if(isServer){
                CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TOUCHED_BLOCK);
                worldEvent.amount = 1;
                worldEvent.position = this.lastCheckPos;
                worldEvent.blockTouchedState = hasTouchedBlock ? CCWorldEvent.BlockTouchedState.HAS_TOUCHED : CCWorldEvent.BlockTouchedState.HAS_NOT_TOUCHED;
                ((ServerOrgManager)orgManager).test(worldEvent);
            }
            if(!hasTouchedBlock){
                touchedBlocks.add(this.lastCheckPos);
            }
        }


        if(
            isServer &&
            ticksSinceLastMove != 0 &&
            ticksSinceLastMove % (20 * 5) == 0
        ){
            CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.HAS_NOT_TRAVELED);
            ((ServerOrgManager)orgManager).test(worldEvent);
        }
    }

    public boolean hasTouchedBlock(Vec3d vec3d){
        Vec3i vec = new Vec3i(
                (int)vec3d.getX(),
                (int)vec3d.getY(),
                (int)vec3d.getZ()
        );
        return hasTouchedBlock(vec);

    }
    public boolean hasTouchedBlock(Vec3i vec3i){
        return (this.touchedBlocks.contains(vec3i));
    }
}
