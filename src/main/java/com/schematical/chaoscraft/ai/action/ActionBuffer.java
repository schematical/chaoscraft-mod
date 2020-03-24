package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.entities.OrgEntity;

import java.util.ArrayList;

public class ActionBuffer {

    private ArrayList<ActionBase> recentActions = new ArrayList<ActionBase>();
    private ActionBase currAction = null;
    public void execute(OrgEntity orgEntity){
        if(currAction == null){
            return;
        }
        currAction.tick();
    }
    public void addAction(ActionBase action){

    }
    public void interrupt(){
        if(currAction == null){
            return;
        }
        currAction.markInterrupted();
    }

}
