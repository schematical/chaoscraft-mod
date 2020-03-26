package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientActionPacket;
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
            //clean it up
            if(
                currAction.getActionState().equals(ActionBase.ActionState.Running) ||
                currAction.getActionState().equals(ActionBase.ActionState.Failed)
            ){
                recentActions.add(currAction);
                currAction = null;
            }else{
                throw new ChaosNetException("Invalid ActionState at this point: " + currAction.getActionState().toString());
            }
        }
        currAction.tick();
    }
    public void addAction(ActionBase action){
        if(currAction != null){
          throw new ChaosNetException("We already have a currAction.... interrupt or something");
        }
        currAction = action;
        sync();
    }
    public void sync(){
        //Send a packet to queue up the action
        if(isClient()) {
            CCClientActionPacket clientActionPacket = new CCClientActionPacket(orgManager.getCCNamespace(), CCClientActionPacket.Action.SET_TARGET);
            //clientActionPacket.setBiology(targetSlot);
            ChaosNetworkManager.sendToServer(clientActionPacket);
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
}
