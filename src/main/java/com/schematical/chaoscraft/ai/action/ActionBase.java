package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;

public abstract class ActionBase {
    private ActionBuffer actionBuffer;
    private float actionScore = 0;
    protected int actionAgeTicks = 0;
    private ActionState state = ActionState.Pending;
    private ChaosTarget target;
    private ChaosTargetItem targetItem;
    public ArrayList<CCServerScoreEventPacket> scoreEvents = new ArrayList<CCServerScoreEventPacket>();

    //TODO: Track score events that happened when this action was happening

    public static boolean validateTargetAndItem(OrgEntity orgEntity, ChaosTarget chaosTarget, ChaosTargetItem chaosTargetItem){
        if(
            validateTarget( orgEntity, chaosTarget) &&
            validateTargetItem( orgEntity, chaosTargetItem)
        ) {
            return true;
        }else{
            return false;
        }
    }
    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget){
        return true;
    }
    public static boolean validateTargetItem(OrgEntity orgEntity, ChaosTargetItem chaosTargetItem){
        return true;
    }
    public void setActionBuffer(ActionBuffer actionBuffer){
        this.actionBuffer = actionBuffer;
    }
    public ActionBuffer getActionBuffer(){
        return actionBuffer;
    }
    protected abstract void _tick();
    public void tick(){
        if(!this.actionBuffer.isServer()){
            throw new ChaosNetException("Client should not be ticking `actionBuffer` state");
        }
        actionAgeTicks += 1;
        if(actionAgeTicks > 15 * 20){
            markFailed();
            return;
        }
        if(state.equals(ActionState.Pending)){
            markRunning();
            tickFirst();
        }
        enforceItemEquip();
        _tick();

    }
    public void tickFirst(){
        enforceItemEquip();
    }
    public void enforceItemEquip(){
        if(this.getTargetItem() != null) {
            if (this.getTargetItem().getInventorySlot() != null) {
                if (this.getOrgEntity().getSelectedItemIndex() != this.getTargetItem().getInventorySlot()) {
                    this.getOrgEntity().equipSlot(this.getTargetItem().getInventorySlot());
                }
            }
        }
    }
    public void setTarget(ChaosTarget target){

        this.target = target;
    }
    public void setTargetItem(ChaosTargetItem item){
        targetItem = item;
    }

    void markRunning() {

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
  /*      if(!this.actionBuffer.isServer()){
            throw new ChaosNetException("Client should not be changing `actionBuffer` state");
        }*/
        if(!this.state.equals(ActionState.Running)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Interrupted);
    }
    private void setState(ActionState state){
        this.state = state;
        if(this.actionBuffer.isServer()) {
            this.actionBuffer.sync();
        }
    }
    public OrgEntity getOrgEntity(){
        return actionBuffer.getOrgManager().getEntity();
    }
    public void markCompleted(){
        /*if(!this.actionBuffer.isServer()){
            throw new ChaosNetException("Client should not be changing `actionBuffer` state");
        }*/
        if(!this.state.equals(ActionState.Running)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Completed);
    }

    public void markFailed(){
     /*   if(!this.actionBuffer.isServer()){
            throw new ChaosNetException("Client should not be changing `actionBuffer` state");
        }*/
        if(!this.state.equals(ActionState.Running)){
            throw new ChaosNetException("Invalid `ActionBase.state`: " + this.state);
        }
        this.setState(ActionState.Failed);
    }
    public void attachScoreEvent(CCServerScoreEventPacket scoreEventPacket){
        scoreEvents.add(scoreEventPacket);
    }
    public ChaosTarget getTarget() {
        return target;
    }

    public String toString(){
        String s = this.getClass().getSimpleName();
        s += " T:" + target.toString() + " -";
        s += " I:" + targetItem.toString(getActionBuffer().getOrgManager().getEntity());
        s += " " + state.toString();
        return s;
    }

    public int getScoreTotal() {
        int score = 0;
        for (CCServerScoreEventPacket scoreEvent : scoreEvents) {
            score += scoreEvent.score;
        }
        return score;
    }

    public String getSimpleActionStatsKey() {
        return getClass().getSimpleName() + "-" + getTarget().getActionStatString(getActionBuffer().getOrgManager().getEntity().world);
    }
    public boolean match(ActionBase actionBase){
        if(!actionBase.getClass().equals(this.getClass())){
            return false;
        }
        if(!actionBase.getTarget().equals(getTarget())){
            return false;
        }
        if(!actionBase.getTargetItem().equals(getTargetItem())){
            return false;
        }
        return true;
    }

    public boolean match(Class<ActionBase> actionBaseClass, ChaosTarget chaosTarget, ChaosTargetItem chaosTargetItem) {
        if(!actionBaseClass.equals(this.getClass())){
            return false;
        }
        if(!chaosTarget.equals(getTarget())){
            return false;
        }
        if(!chaosTargetItem.equals(getTargetItem())){
            return false;
        }
        return true;
    }

    public ChaosTargetItem getTargetItem() {
        return targetItem;
    }

    public void encode(PacketBuffer buf){

        buf.writeString(target.getSerializedString());
        buf.writeString(targetItem.getSerializedString());
    }

    public void decode(PacketBuffer buf){
        setTarget(
                ChaosTarget.deserializeTarget(
                        ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD),
                        buf.readString(32767)
                )
        );
        setTargetItem(
                ChaosTargetItem.deserialize(
                        ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD),
                        buf.readString(32767)
                )
        );
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
