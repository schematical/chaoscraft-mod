package com.schematical.chaoscraft.client;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientOrgDebugStateChangeRequestPacket;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.tickables.BaseChaosEventListener;
import com.schematical.chaoscraft.tickables.ChaosHighScoreTracker;
import com.schematical.chaoscraft.tickables.ChaosTeamTracker;
import com.schematical.chaoscraft.tickables.OrgPositionManager;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientOrgManager extends BaseOrgManager {
    protected State state = State.Uninitialized;
    private int ticksWithoutUpdate = 0;
    protected ArrayList<CCServerScoreEventPacket> serverScoreEvents = new ArrayList<CCServerScoreEventPacket>();
    protected int expectedLifeEndTime = -1;
    protected ServerOrgManager.DebugState debugState = ServerOrgManager.DebugState.On;
    private int reportReattempts = 0;
    private int spawnCount = -1;
    private ScanManager scanManager;

    public ClientOrgManager(){

        this.attatchEventListener(new OrgPositionManager());
        this.attatchEventListener(new ChaosHighScoreTracker());
        this.attatchEventListener(new ChaosTeamTracker());
    }

    public int getExpectedLifeEndTime(){
        return expectedLifeEndTime;
    }


    public void addServerScoreEvent(CCServerScoreEventPacket pkt){
        expectedLifeEndTime = pkt.expectedLifeEndTime;
        serverScoreEvents.add(pkt);
        orgEntity.world.playSound((PlayerEntity)null, orgEntity.getPosition(), SoundEvents.BLOCK_BELL_USE, SoundCategory.AMBIENT, 3.0F, 1f);
        BasicParticleType particleType = ParticleTypes.ITEM_SLIME;
        int max = (int)Math.round(pkt.score * pkt.multiplier * .1f);

        if(max < 0){
           particleType = ParticleTypes.FLAME;
            max = Math.abs(max);
        }
        if(max > 10){
            max = 10;
        }
        for(int i = 0; i < max; i ++) {
            BlockPos pos = orgEntity.getPosition();
            orgEntity.world.addParticle(
                    particleType,
                (double) pos.getX() + 0.5D,
                (double) pos.getY() + 1.2D,
                (double) pos.getZ() + 0.5D,
                0.0D,
                    (double) i / 24.0D,
                0.0D
            );
        }
    }

    public void attachOrganism(Organism organism){
        if(!state.equals(State.Uninitialized)){
            throw new ChaosNetException(getCCNamespace() + " - has invalid state: " + state);
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
        this.scanManager = new ScanManager(this);
        orgEntity.attachClientOrgEntity(this);
        try {
            this.orgEntity.attachNNetRaw(organism.getNNetRaw());
            spawnCount += 1;
            state = State.EntityAttached;
        }catch(Exception exceptions){
            markOrgAsInvalid();
        }

        //this.attatchTickable(new TargetNNetManager(this.scanManager));
        //this.attatchTickable(new RTNeatTicker(this.orgEntity));

    }

    private void markOrgAsInvalid() {
        state = State.Invalid;
    }

    public ArrayList<CCServerScoreEventPacket> getServerScoreEvents(){
        return serverScoreEvents;
    }
    public Double getLatestScore(){
        Double total = 0d;
        for (CCServerScoreEventPacket serverScoreEvent: serverScoreEvents) {
            if(serverScoreEvent.runIndex == spawnCount){
                total += serverScoreEvent.getAdjustedScore();
            }
        }
        return total;
    }
    public Double getServerScoreEventTotal() {
        double total = 0;
        for (CCServerScoreEventPacket serverScoreEvent: serverScoreEvents) {
            if(serverScoreEvent.runIndex == 0){
                total += serverScoreEvent.getAdjustedScore();
            }
        }
        if(total > 0){
            //ChaosCraft.LOGGER.info(getCCNamespace() + " - Scored: " + total);
        }
        return total;
    }
    public Double getServerScoreEventMedian(){

        float flattenTo = 10;

        HashMap<Integer, Float> runScores = new HashMap<>();

        for (CCServerScoreEventPacket serverScoreEvent: serverScoreEvents) {
            if(!runScores.containsKey(serverScoreEvent.runIndex)){
                runScores.put(serverScoreEvent.runIndex, 0f);
            }
            float runTotal = runScores.get(serverScoreEvent.runIndex);
            runTotal += serverScoreEvent.getAdjustedScore();
            runScores.put(serverScoreEvent.runIndex, runTotal);

        }
        HashMap<Integer, Integer> medianScores = new HashMap<Integer, Integer>();
        for (Integer runIndex : runScores.keySet()) {
            float score = runScores.get(runIndex);
            int flatScore = (int)(Math.round(score / flattenTo) * flattenTo);
            if(!medianScores.containsKey(flatScore)){
                medianScores.put(flatScore, 0);
            }
            int scoreCount = medianScores.get(flatScore);
            scoreCount += 1;
            medianScores.put(flatScore, scoreCount);
        }
        float realMedianScore = -999999;
        float highestScoreCount = -99;
        for (Integer flatScore : medianScores.keySet()) {
            if(medianScores.get(flatScore) > highestScoreCount){
                highestScoreCount = medianScores.get(flatScore);
                realMedianScore = flatScore;
            }

        }
        return (double)realMedianScore;
    }


    public State getState() {
        return this.state;
    }

    public void markSpawnMessageSent() {
        if(!state.equals(State.OrgAttached)){
            throw new ChaosNetException(getCCNamespace() + " - has invalid state: " + state);
        }
        state = State.SpawnMessageSent;
    }
    public void markAttemptingReport() {
        if(
            !state.equals(State.ReadyToReport) &&
            !state.equals(State.Invalid)
        ){
            throw new ChaosNetException(getCCNamespace() + " - Invalid State: " + state);
        }
        state = State.AttemptingToReport;
    }
    public void markReported() {
        if(!state.equals(State.AttemptingToReport)){
            throw new ChaosNetException(getCCNamespace() + " - Invalid State: " + state);
        }
        state = State.FinishedReport;
    }


    public void markDead() {
        if(!state.equals(State.Ticking)){
            throw new ChaosNetException(getCCNamespace() + " - has invalid state: " + state);
        }
        //If high scoring and

       state = State.ReadyToReport;


    }
    public void markTicking() {
        if(!state.equals(State.EntityAttached)){
            throw new ChaosNetException(getCCNamespace() + " - has invalid state: " + state);

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
    public ServerOrgManager.DebugState getDebugState(){
        return debugState;
    }
    public ServerOrgManager.DebugState toggleDebugState(){
        ServerOrgManager.DebugState newState = null;
        if(debugState.equals(ServerOrgManager.DebugState.On)){
            newState = ServerOrgManager.DebugState.Off;
        }else{
            newState = ServerOrgManager.DebugState.On;
        }
        ChaosNetworkManager.sendToServer(new CCClientOrgDebugStateChangeRequestPacket(newState, getCCNamespace()));
        debugState = newState;
        return debugState;
    }

    public void markForRetryReport() {
        if(!state.equals(State.FinishedReport)){
            throw new ChaosNetException(getCCNamespace() + " - has invalid state: " + state);

        }
        state = State.AttemptingToReport;
        reportReattempts += 1;
        if(reportReattempts > 5){
            throw new ChaosNetException("ClientOrgManager - " + getCCNamespace() + " Attempted to reReport " + reportReattempts + " times");
        }
    }

    public ScanManager getScanManager() {
        return scanManager;
    }
    public void triggerOnReport(){
        for (BaseChaosEventListener eventListener : getEventListeners()) {
            eventListener.onClientReport(this);
        }
    }
    public void triggerOnTickEvent() {
        for (BaseChaosEventListener eventListener : getEventListeners()) {
            eventListener.onClientTick(this);
        }
    }

    public void setExpectedLifeEndTime(int expectedLifeEndTime) {
        this.expectedLifeEndTime =  expectedLifeEndTime;
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
        Invalid,
    }

}
