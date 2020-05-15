package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCActionStateChangeEventPacket;
import com.schematical.chaoscraft.network.packets.CCClientSetCurrActionPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.services.targetnet.ScanInstance;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionBuffer {
    private BaseOrgManager orgManager;
    private int cooldown = -1;

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
    private WonderAction wonderAction = null;
    public void execute(){
        if(isClient()) {
            throw new ChaosNetException("This should not get called Client side");
        }
        if(currAction == null){
            if(wonderAction == null){
                wonderAction = new WonderAction();
                wonderAction.setActionBuffer(this);
            }

            wonderAction.tick();
            return;
        }else{
            ServerOrgManager serverOrgManager = ((ServerOrgManager) orgManager);
            if(serverOrgManager.getState().equals(ServerOrgManager.State.Spawned)){
                serverOrgManager.initInventory();
                serverOrgManager.markTicking();
            }
        }

        if(!currAction.getActionState().equals(ActionBase.ActionState.Running)){
            if( currAction.getActionState().equals(ActionBase.ActionState.Pending)){
                //Do nothng
            }else if(
                currAction.getActionState().equals(ActionBase.ActionState.Completed) ||
                currAction.getActionState().equals(ActionBase.ActionState.Failed) ||
                currAction.getActionState().equals(ActionBase.ActionState.Invalid)
            ){
                //recentActions.add(currAction);//Prob remove this. This should happen client side
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
        if(!currAction.isValid()){
            currAction.markInvalid();
        }


        return this;

    }
    public void sync(){
        //Send a packet to queue up the action
        if(currAction == null){
            throw new ChaosNetException("No current action to sync");
        }
        if(isClient()) {
            CCClientSetCurrActionPacket pkt = new CCClientSetCurrActionPacket(orgManager.getCCNamespace(), currAction);
            //clientActionPacket.setBiology(targetSlot);
            ChaosNetworkManager.sendToServer(pkt);
        }else{

            CCActionStateChangeEventPacket pkt = new CCActionStateChangeEventPacket(
                    orgManager.getCCNamespace(),
                    currAction.getActionState()
            );
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
            currAction.onClientMarkCompleted();
        }else if(message.actionState.equals(ActionBase.ActionState.Failed)){
            currAction.markFailed();
            simpleActionStats.numFails += 1;
            simpleActionStats.lastExecutedWorldTime = getOrgManager().getEntity().world.getGameTime();
            simpleActionStats.numTimesExecuted += 1;
            simpleActionStats.score += currAction.getScoreTotal();
        }else if(message.actionState.equals(ActionBase.ActionState.Interrupted)){
            currAction.markInterrupted();
        }else if(message.actionState.equals(ActionBase.ActionState.Invalid)){
            currAction.markInvalid();
        }else{
            throw new ChaosNetException("Invalid state: " + message.actionState);
        }


    }
    private SimpleActionStats getSimpleActionStats() {
        return getSimpleActionStats(currAction);
    }
    private SimpleActionStats getSimpleActionStats(ActionBase actionBase) {
        String key = actionBase.getSimpleActionStatsKey();
        return getSimpleActionStats(key);
    }
    public SimpleActionStats getSimpleActionStats(String key) {

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
            if(cooldown > 0){
                cooldown -= 1;
                return;
            }
            //we should be scanning...
            ClientOrgManager clientOrgManager = (ClientOrgManager) getOrgManager();
            ScanManager scanManager = clientOrgManager.getScanManager();
            if(
                scanManager.getScanInstance() == null ||
                !scanManager.getScanInstance().getScanState().equals(ScanInstance.ScanState.Ticking)
            ){
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
                    currAction.getActionState().equals(ActionBase.ActionState.Failed) ||
                    currAction.getActionState().equals(ActionBase.ActionState.Invalid)
                ) {
                    recentActions.add(currAction);
                    currAction = null;
                    cooldown = 10;
                    return;
                } else {
                    throw new ChaosNetException("Invalid ActionState at this point: " + currAction.getActionState().toString());
                }
            }
        }
    }
    public ArrayList<ActionBase> matchExecutedRecently(Class<ActionBase> actionBaseClass, ChaosTarget chaosTarget, ChaosTargetItem chaosTargetItem, Integer withInLastActions){
        ArrayList<ActionBase> actionBases = new ArrayList<>();
        int startIndex = 0;
        if(withInLastActions != null) {
            startIndex = recentActions.size() - withInLastActions;
            if (startIndex < 0) {
                startIndex = 0;
            }
        }

        for (int i = startIndex; i < recentActions.size(); i++) {
            ActionBase testActionBase = recentActions.get(i);
            if(testActionBase.match(actionBaseClass, chaosTarget, chaosTargetItem)){
                actionBases.add(testActionBase);
            }
        }
        return actionBases;
    }
    public ArrayList<ActionBase> matchExecutedRecently(ActionBase actionBase, Integer withInLastActions){
        ArrayList<ActionBase> actionBases = new ArrayList<>();
        int startIndex = 0;
        if(withInLastActions != null) {
            startIndex = recentActions.size() - withInLastActions;
            if (startIndex < 0) {
                startIndex = 0;
            }
        }

        for (int i = startIndex; i < recentActions.size(); i++) {
            ActionBase testActionBase = recentActions.get(i);
            if(testActionBase.match(actionBase)){
                actionBases.add(testActionBase);
            }
        }
        return actionBases;
    }
    public boolean hasExecutedRecently(ActionBase actionBase, Integer withInLastActions){
        return matchExecutedRecently(actionBase,withInLastActions).size() > 0;
    }
    public boolean hasExecutedRecently(Class<ActionBase> actionBaseClass, ChaosTarget chaosTarget, ChaosTargetItem chaosTargetItem, Integer withInLastActions){
        return matchExecutedRecently(actionBaseClass, chaosTarget, chaosTargetItem, withInLastActions).size() > 0;
    }
    public ArrayList<ActionBase> getRecentActions() {
        return recentActions;
    }

    public class SimpleActionStats{
        public int score = 0;
        public int numTimesExecuted = 0;
        public long lastExecutedWorldTime = 0;
        public int numFails = 0;
        public int numCompleted = 0;
        //TODO: Track other stat changes... health, etc
        public String toString(World world) {
            String s = "S:" + score + " ";
            s += "#E:" + numTimesExecuted + " ";
            s += "#C:" + numCompleted + " ";
            s += "#F:" + numFails + " ";
            s += "T:" + Math.round((lastExecutedWorldTime - world.getGameTime())/1000)/* + " "*/;
            return s;
        }
    }
}
