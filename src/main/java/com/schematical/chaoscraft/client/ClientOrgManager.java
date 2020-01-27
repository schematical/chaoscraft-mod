package com.schematical.chaoscraft.client;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class ClientOrgManager extends BaseOrgManager {
    protected State state = State.Uninitialized;
    private int ticksWithoutUpdate = 0;
    protected ArrayList<CCServerScoreEventPacket> serverScoreEvents = new ArrayList<CCServerScoreEventPacket>();
    public void addServerScoreEvent(CCServerScoreEventPacket pkt){

        serverScoreEvents.add(pkt);
        orgEntity.world.playSound((PlayerEntity)null, orgEntity.getPosition(), SoundEvents.BLOCK_BELL_USE, SoundCategory.RECORDS, 3.0F, 1f);
        for(int i = 0; i < 20; i ++) {
            BlockPos pos = orgEntity.getPosition();
            orgEntity.world.addParticle(
                ParticleTypes.BUBBLE,
                (double) pos.getX() + 0.5D,
                (double) pos.getY() + 1.2D,
                (double) pos.getZ() + 0.5D,
                (double) i / 24.0D,
                0.0D,
                0.0D
            );
        }
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
        this.orgEntity.observableAttributeManager = new CCObservableAttributeManager(organism);
        this.orgEntity.attachNNetRaw(organism.getNNetRaw());
        orgEntity.attachClientOrgEntity(this);
        state = State.EntityAttached;
    }
    public ArrayList<CCServerScoreEventPacket> getServerScoreEvents(){
        return serverScoreEvents;
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
    public void markTicking() {
        if(!state.equals(State.EntityAttached)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        state = State.Ticking;
    }
    public void manualUpdateCheck(){
        ticksWithoutUpdate += 1;
        if(
            ticksWithoutUpdate > 5 &&
        this.getEntity() != null
        ){
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
