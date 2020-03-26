package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;

public abstract class ActionBase {
    private ActionBuffer actionBuffer;
    private float actionScore = 0;
    private int actionAgeTicks = 0;
    private ActionState state = ActionState.Pending;
    private ChaosTarget target;

    //TODO: Track score events that happened when this action was happening

    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget){
        return true;
    }
    public void setActionBuffer(ActionBuffer actionBuffer){
        this.actionBuffer = actionBuffer;
    }
    protected abstract void _tick();
    public void tick(){
        if(!this.actionBuffer.isServer()){
            throw new ChaosNetException("Client should not be ticking `actionBuffer` state");
        }
        actionAgeTicks += 1;
        if(state.equals(ActionState.Pending)){
            markRunning();
        }
        _tick();

    }
    public void setTarget(ChaosTarget target){

        this.target = target;
    }

    private void markRunning() {
        if(!this.actionBuffer.isServer()){
            throw new ChaosNetException("Client should not be changing `actionBuffer` state");
        }
        if(!this.state.equals(ActionState.Pending)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Running);
    }

    public int getActionAgeTicks(){
        return actionAgeTicks;
    }
    public ActionState getActionState(){
        return state;
    }

    public void markInterrupted() {
        if(!this.actionBuffer.isServer()){
            throw new ChaosNetException("Client should not be changing `actionBuffer` state");
        }
        if(!this.state.equals(ActionState.Running)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Interrupted);
    }
    private void setState(ActionState state){
        this.state = state;
    }
    public OrgEntity getOrgEntity(){
        return actionBuffer.getOrgManager().getEntity();
    }
    public void markCompleted(){
        if(!this.actionBuffer.isServer()){
            throw new ChaosNetException("Client should not be changing `actionBuffer` state");
        }
        if(!this.state.equals(ActionState.Running)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Completed);
    }

    public void markFailed(){
        if(!this.actionBuffer.isServer()){
            throw new ChaosNetException("Client should not be changing `actionBuffer` state");
        }
        if(!this.state.equals(ActionState.Running)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Failed);
    }

    public ChaosTarget getTarget() {
        return target;
    }

    public enum ActionState{
        Pending,
        Running,
        Completed,
        Failed,
        Interrupted,
        Timedout
    }


}
