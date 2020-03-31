package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCActionStateChangeEventPacket;
import com.schematical.chaoscraft.network.packets.CCClientSetCurrActionPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaosnet.model.ChaosNetException;

import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<String, SimpleActionStats> simpleActonTracker = new HashMap<>();
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

        SimpleActionStats simpleActionStats = getSimpleActionStats();

        if(message.actionState.equals(ActionBase.ActionState.Running)){
            currAction.markRunning();
        }else if(message.actionState.equals(ActionBase.ActionState.Completed)){
            currAction.markCompleted();
            simpleActionStats.numCompleted += 1;
            simpleActionStats.lastExecutedWorldTime = getOrgManager().getEntity().world.getGameTime();
            simpleActionStats.numTimesExecuted += 1;
            simpleActionStats.score += currAction.getScoreTotal();
        }else if(message.actionState.equals(ActionBase.ActionState.Failed)){
            currAction.markFailed();
            simpleActionStats.numFails += 1;
            simpleActionStats.lastExecutedWorldTime = getOrgManager().getEntity().world.getGameTime();
            simpleActionStats.numTimesExecuted += 1;
            simpleActionStats.score += currAction.getScoreTotal();
        }else if(message.actionState.equals(ActionBase.ActionState.Interrupted)){
            currAction.markInterrupted();
        }else{
            throw new ChaosNetException("Invalid state: " + message.actionState);
        }


    }

    private SimpleActionStats getSimpleActionStats() {
        String key = currAction.getClass().getSimpleName() + "-" + currAction.getTarget().getSerializedString();
        SimpleActionStats simpleActionStats = null;
        if(simpleActonTracker.containsKey(key)){
            simpleActionStats = simpleActonTracker.get(key);
        }else{
            simpleActionStats = new SimpleActionStats();
            simpleActonTracker.put(key, simpleActionStats);
        }
        return simpleActionStats;
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


    public class SimpleActionStats{
        public int score = 0;
        public int numTimesExecuted = 0;
        public long lastExecutedWorldTime = 0;
        public int numFails = 0;
        public int numCompleted = 0;
        //TODO: Track other stat changes... health, etc

    }
}
