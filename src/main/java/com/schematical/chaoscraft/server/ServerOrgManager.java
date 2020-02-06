package com.schematical.chaoscraft.server;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.fitness.EntityFitnessManager;
import com.schematical.chaoscraft.network.packets.CCClientOutputNeuronActionPacket;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerOrgManager extends BaseOrgManager {
    protected DebugState debugState = DebugState.Off;
    protected String tmpNamespace;
    protected State state = State.Uninitialized;
    protected ServerPlayerEntity serverPlayerEntity;
    protected long spawnTime = 0;
    public ArrayList<CCClientOutputNeuronActionPacket> neuronActions = new ArrayList<CCClientOutputNeuronActionPacket>();
    private float maxLifeSeconds = 10;
    protected ArrayList<iChaosOrgTickable> tickables = new ArrayList<iChaosOrgTickable>();
    public ServerOrgManager(){
        this.attatchTickable(new ServerOrgPositionManager());
    }
    public void setTmpNamespace(String _tmpNamespace){
        tmpNamespace = _tmpNamespace;
    }
    public String getTmpNamespace(){
       return tmpNamespace;
    }
    public void attatchTickable(iChaosOrgTickable tickable){
        tickables.add(tickable);
    }
    @Override
    public void attachOrganism(Organism organism){
        if(!state.equals(State.PlayerAttached)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        super.attachOrganism(organism);
        state = State.OrgAttached;
    }
    @Override
    public void attachOrgEntity(OrgEntity orgEntity){
        if(!state.equals(State.QueuedForSpawn)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        super.attachOrgEntity(orgEntity);
        this.orgEntity.attachSeverOrgManager(this);
        this.orgEntity.attachNNetRaw(this.organism.getNNetRaw());
        orgEntity.entityFitnessManager = new EntityFitnessManager(orgEntity);
        orgEntity.observableAttributeManager = new CCObservableAttributeManager(organism);
        orgEntity.setCustomName(new TranslationTextComponent(getCCNamespace()));
        orgEntity.setSpawnHash(ChaosCraft.getServer().spawnHash);
        spawnTime = orgEntity.world.getGameTime();
        state = State.Spawned;
    }
    public void setPlayerEntity(ServerPlayerEntity serverPlayerEntity){
        if(!state.equals(State.Uninitialized)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        this.serverPlayerEntity = serverPlayerEntity;
        state = State.PlayerAttached;
    }

    public ServerPlayerEntity getServerPlayerEntity(){
        return this.serverPlayerEntity;
    }
    public void queueOutputNeuronAction(CCClientOutputNeuronActionPacket message) {
        neuronActions.add(message);
    }
    public State getState(){
        return state;
    }
    public void queueForSpawn() {
        if(!state.equals(State.OrgAttached)){
            ChaosCraft.LOGGER.error("ServerOrgManager.state != " + State.Uninitialized);
            return;
        }
        state = State.QueuedForSpawn;
    }
    public float getAgeSeconds(){
        return (this.orgEntity.world.getGameTime() - spawnTime)  / 20;
    }



    public void tickServer(){
        if( this.orgEntity.getBoundingBox() == null){
            return;
        }
        if (
            this.getEntity().getNNet() == null ||
            this.getEntity().getNNet().neurons == null
        ){
            ChaosCraft.LOGGER.error("Missing Entity or NNEt or something");
            return;
        }


//TODO: DELETE THIS
        /*ItemStack healdItemStack = this.orgEntity.getHeldItem(Hand.MAIN_HAND);
        if(!healdItemStack.getItem().equals(Items.FLINT_AND_STEEL)){
            healdItemStack = new ItemStack(Items.FLINT_AND_STEEL);
            healdItemStack.setCount(32);
            this.orgEntity.setHeldItem(Hand.MAIN_HAND, healdItemStack);
            healdItemStack = this.orgEntity.getHeldItem(Hand.MAIN_HAND);
        }else{
            //ChaosCraft.LOGGER.info("ALready have the TNT");
        }*/



        if(this.neuronActions.size() > 0){
            //Iterate through and find output neurons
            List<OutputNeuron> outputs = new ArrayList<OutputNeuron>();
            Iterator<CCClientOutputNeuronActionPacket> keyIterator = this.neuronActions.iterator();
            while(keyIterator.hasNext()) {
                CCClientOutputNeuronActionPacket neuronAction = keyIterator.next();
                if (!this.getEntity().getNNet().neurons.containsKey(neuronAction.getNeuronId())) {
                    throw new ChaosNetException(this.getCCNamespace() + " is missing an OutputNeuron: " + neuronAction.getNeuronId());
                }
                if (!(this.getEntity().getNNet().neurons.get(neuronAction.getNeuronId()) instanceof  OutputNeuron)) {
                    throw new ChaosNetException(this.getCCNamespace() + " is found but not an instanceof OutputNeuron: " + neuronAction.getNeuronId());
                }
                OutputNeuron outputNeuron = (OutputNeuron)this.getEntity().getNNet().neurons.get(neuronAction.getNeuronId());
                outputNeuron.setCurrentValue(neuronAction.getValue());
                outputs.add(outputNeuron);
            }
            Iterator<OutputNeuron> iterator = outputs.iterator();
            while(iterator.hasNext()) {
                OutputNeuron outputNeuron = iterator.next();
                outputNeuron.execute();
            }
            this.neuronActions.clear();
            if(this.state.equals(State.Spawned)){
                this.markTicking();

            }
            fireTickables();
        }

    }

    private void fireTickables() {
        for (iChaosOrgTickable tickable : tickables) {
            tickable.Tick(this);
        }
    }

    public void markTicking() {
        if(!state.equals(ServerOrgManager.State.Spawned)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        state = State.Ticking;
    }
    public void markDead() {
        if(!state.equals(ServerOrgManager.State.Ticking)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        state = State.Dead;
    }
    public float getMaxLife(){
        return maxLifeSeconds;
    }
    public void adjustMaxLife(int life) {
        maxLifeSeconds += life;
    }
    public boolean checkStatus() {
        if (
            //this.organism == null ||

            (
                !debugState.equals(DebugState.On) &&
                getAgeSeconds() > maxLifeSeconds
            )

        ) {
            //this.dropInventory();
            this.orgEntity.setHealth(-1);
            return true;
        }
        return false;
    }

    public void setDebugState(DebugState debugState) {
        this.debugState = debugState;
    }


    public enum State{
        Uninitialized,
        OrgAttached,
        Spawned,
        //ClientAttached//Dont think we need this any more
        Ticking,
        Dead,
        QueuedForSpawn,
        PlayerAttached,
    }
    public enum DebugState{
        Off,
        On
    }
}
