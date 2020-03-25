package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;

public abstract class ActionBase {
    private OrgEntity orgEntity;
    private int actionAgeTicks = 0;
    private ActionState state = ActionState.Pending;
    public ChaosTarget target;
    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget){
        return true;
    }

    protected abstract void _tick();
    public void tick(){
        actionAgeTicks += 1;
        if(state.equals(ActionState.Pending)){
            markRunning();
        }
        _tick();

    }

    private void markRunning() {
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
        if(!this.state.equals(ActionState.Running)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Interrupted);
    }
    private void setState(ActionState state){
        this.state = state;
    }
    public OrgEntity getOrgEntity(){
        return orgEntity;
    }
    public void setOrgEntity(OrgEntity orgEntity){
        this.orgEntity = orgEntity;
    }
    public void markCompleted(){
        if(!this.state.equals(ActionState.Running)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Completed);
    }

    public void markFailed(){
        if(!this.state.equals(ActionState.Running)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Failed);
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
