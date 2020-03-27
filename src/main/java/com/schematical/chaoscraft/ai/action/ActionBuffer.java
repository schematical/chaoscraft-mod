package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientActionPacket;
import com.schematical.chaoscraft.network.packets.CCClientSetCurrActionPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;

import java.util.ArrayList;

public class ActionBuffer {
    private BaseOrgManager orgManager;
    public ActionBuffer(BaseOrgManager baseOrgManager){
        orgManager = baseOrgManager;
    }
    public boolean isServer(){
        return (orgManager instanceof ServerOrgManager);
    }
    public boolean isClient(){
        return (orgManager instanceof ClientOrgManager);
    }
    private ArrayList<ActionBase> recentActions = new ArrayList<ActionBase>();
    private ActionBase currAction = null;
    public void execute(){
        if(isClient()) {
            throw new ChaosNetException("This should not get called Client side");
        }
        if(currAction == null){
            return;
        }

        if(!currAction.getActionState().equals(ActionBase.ActionState.Running)){
            if( currAction.getActionState().equals(ActionBase.ActionState.Pending)){
                //Do nothng
            }else if(
                currAction.getActionState().equals(ActionBase.ActionState.Completed) ||
                currAction.getActionState().equals(ActionBase.ActionState.Failed)
            ){
                recentActions.add(currAction);//Prob remove this. This should happen client side
                currAction = null;
                return;
            }else{
                throw new ChaosNetException("Invalid ActionState at this point: " + currAction.getActionState().toString());
            }
        }
        currAction.tick();
    }
    public ActionBuffer addAction(ActionBase action){
       /* if(currAction != null){
          throw new ChaosNetException("We already have a currAction.... interrupt or something");
        }*/
        action.setActionBuffer(this);
        currAction = action;
        return this;

    }
    public void sync(){
        //Send a packet to queue up the action
        if(isClient()) {
            CCClientSetCurrActionPacket pkt = new CCClientSetCurrActionPacket(orgManager.getCCNamespace(), currAction);
            //clientActionPacket.setBiology(targetSlot);
            ChaosNetworkManager.sendToServer(pkt);
        }else{
            throw new ChaosNetException("Todo: Write me");
        }
    }
    public void interrupt(){
        if(currAction == null){
            return;
        }
        currAction.markInterrupted();
    }

    public BaseOrgManager getOrgManager() {
        return orgManager;
    }

    public ActionBase getCurrAction() {
        return currAction;
    }
}
