package com.schematical.chaoscraft.server;

import com.schematical.chaoscraft.events.CCWorldEvent;
import net.minecraft.util.math.Vec3d;

public class ServerOrgPositionManager implements iChaosOrgTickable {
    public Vec3d startPos;
    public Vec3d lastCheckPos;
    public Vec3d maxDist = new Vec3d(0,0,0);

    @Override
    public void Tick(ServerOrgManager serverOrgManager) {
        if( this.startPos == null){
            this.startPos = serverOrgManager.getEntity().getPositionVec();
            this.lastCheckPos = this.startPos;
        }

        Vec3d currPos = serverOrgManager.getEntity().getPositionVec();
        if(
                !this.lastCheckPos.equals(currPos)
        ){
            this.lastCheckPos = currPos;
            Vec3d diffVec = this.lastCheckPos.subtract(this.startPos);
            if(Math.round(Math.abs(diffVec.x))> this.maxDist.x){
                this.maxDist = new Vec3d(
                        Math.round(Math.abs(diffVec.x)),
                        this.maxDist.y,
                        this.maxDist.z
                );
                CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TRAVEL_ALONG_AXIS);
                worldEvent.amount = (int)this.maxDist.x;
                worldEvent.axis = CCWorldEvent.Axis.X;
                serverOrgManager.getEntity().entityFitnessManager.test(worldEvent);
            }
            if(Math.round(Math.abs(diffVec.y)) > this.maxDist.y){
                this.maxDist = new Vec3d(
                        this.maxDist.x,
                        Math.round(Math.abs(diffVec.y)),
                        this.maxDist.z
                );
                CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TRAVEL_ALONG_AXIS);
                worldEvent.amount = (int)this.maxDist.y;
                worldEvent.axis = CCWorldEvent.Axis.Y;
                serverOrgManager.getEntity().entityFitnessManager.test(worldEvent);
            }
            if(Math.round(Math.abs(diffVec.z)) > this.maxDist.z){
                this.maxDist = new Vec3d(
                        this.maxDist.x,
                        this.maxDist.y,
                        Math.round(Math.abs(diffVec.z))
                );
                CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TRAVEL_ALONG_AXIS);
                worldEvent.amount = (int)this.maxDist.z;
                worldEvent.axis = CCWorldEvent.Axis.Z;
                serverOrgManager.getEntity().entityFitnessManager.test(worldEvent);
            }
        }
    }
}
