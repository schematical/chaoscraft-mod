package com.schematical.chaoscraft.client;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaosnet.model.Organism;

import java.util.ArrayList;

public class ClientOrgManager extends BaseOrgManager {
    protected State state = State.Uninitialized;
    private int ticksWithoutUpdate = 0;
    protected ArrayList<CCServerScoreEventPacket> serverScoreEvents = new ArrayList<CCServerScoreEventPacket>();
    public void addServerScoreEvent(CCServerScoreEventPacket pkt){
        serverScoreEvents.add(pkt);
    }

    public void attachOrganism(Organism organism){
        if(!state.equals(State.Uninitialized)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        super.attachOrganism(organism);
        state = State.OrgAttached;
    }
    public void attachOrgEntity(OrgEntity orgEntity){
        if(!state.equals(State.SpawnMessageSent)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        super.attachOrgEntity(orgEntity);
        this.orgEntity.attachNNetRaw(organism.getNNetRaw());
        state = State.EntityAttached;
    }
    public Double getServerScoreEventTotal(){
        Double total = 0d;
        for (CCServerScoreEventPacket serverScoreEvent: serverScoreEvents) {
            total += serverScoreEvent.getAdjustedScore();
        }
        return total;
    }


    public State getState() {
        return this.state;
    }

    public void markSpawnMessageSent() {
        if(!state.equals(State.OrgAttached)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        state = State.SpawnMessageSent;
    }
    public void markAttemptingReport() {
        if(!state.equals(State.ReadyToReport)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - Invalid State: " + state);
            return;
        }
        state = State.AttemptingToReport;
    }
    public void markReported() {
        if(!state.equals(State.AttemptingToReport)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - Invalid State: " + state);
            return;
        }
        state = State.FinishedReport;
    }


    public void markDead() {
        if(!state.equals(State.Ticking)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        state = State.ReadyToReport;
    }
    public void manualUpdateCheck(){
        ticksWithoutUpdate += 1;
        if(ticksWithoutUpdate > 5){
            this.getEntity().checkStatus();
        }
    }



    public enum State{
        Uninitialized,
        OrgAttached,
        SpawnMessageSent,
        EntityAttached,
        Ticking,
        ReadyToReport,
        AttemptingToReport,
        FinishedReport,
    }
}
