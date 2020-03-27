package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCActionStateChangeEventPacket;
import com.schematical.chaoscraft.network.packets.CCClientActionPacket;
import com.schematical.chaoscraft.network.packets.CCClientSetCurrActionPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
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
            CCActionStateChangeEventPacket pkt = new CCActionStateChangeEventPacket(orgManager.getCCNamespace(), currAction.getActionState());
            //clientActionPacket.setBiology(targetSlot);
            ServerOrgManager serverOrgManager = (ServerOrgManager)getOrgManager();
            ChaosNetworkManager.sendTo(pkt, serverOrgManager.getServerPlayerEntity());
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

    public void applyStateChange(CCActionStateChangeEventPacket message) {
        if(this.isServer()){
            throw new ChaosNetException("The server sends this packet");
        }
        if(currAction == null){
            throw new ChaosNetException("The action is null. This should not be possible.");
        }
        if(message.actionState.equals(ActionBase.ActionState.Running)){
            currAction.markRunning();
        }else if(message.actionState.equals(ActionBase.ActionState.Completed)){
           currAction.markCompleted();
        }else if(message.actionState.equals(ActionBase.ActionState.Interrupted)){
            currAction.markInterrupted();
        }else{
            throw new ChaosNetException("Invalid state: " + message.actionState);
        }

    }

    public void tickClient() {
        if(currAction == null){
            //we should be scanning...
            ClientOrgManager clientOrgManager = (ClientOrgManager) getOrgManager();
            ScanManager scanManager = clientOrgManager.getScanManager();
            if(!scanManager.getState().equals(ScanManager.ScanState.Ticking)){
                //Start scanning again?
                scanManager.resetScan();
            }else{
                scanManager.tickScan();
            }
        }else {
            if (!currAction.getActionState().equals(ActionBase.ActionState.Running)) {
                if (currAction.getActionState().equals(ActionBase.ActionState.Pending)) {
                    //Do nothng
                } else if (
                    currAction.getActionState().equals(ActionBase.ActionState.Completed) ||
                    currAction.getActionState().equals(ActionBase.ActionState.Failed)
                ) {
                    recentActions.add(currAction);//Prob remove this. This should happen client side
                    currAction = null;
                    return;
                } else {
                    throw new ChaosNetException("Invalid ActionState at this point: " + currAction.getActionState().toString());
                }
            }
        }
    }
}
