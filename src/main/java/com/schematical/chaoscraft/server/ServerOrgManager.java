package com.schematical.chaoscraft.server;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.fitness.EntityFitnessManager;
import com.schematical.chaoscraft.network.packets.CCClientOutputNeuronActionPacket;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerOrgManager extends BaseOrgManager {
    protected State state = State.Uninitialized;
    protected ServerPlayerEntity serverPlayerEntity;
    public ArrayList<CCClientOutputNeuronActionPacket> neuronActions = new ArrayList<CCClientOutputNeuronActionPacket>();

    public void attachOrgEntity(OrgEntity orgEntity){
        if(!state.equals(State.Uninitialized)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        super.attachOrgEntity(orgEntity);
        this.orgEntity.attachSeverOrgManager(this);

        orgEntity.entityFitnessManager = new EntityFitnessManager(orgEntity);
        orgEntity.observableAttributeManager = new CCObservableAttributeManager(organism);
        orgEntity.setCustomName(new TranslationTextComponent(orgEntity.getCCNamespace()));
        orgEntity.setSpawnHash(ChaosCraft.getServer().spawnHash);
        state = State.Spawned;
    }
    public void setPlayerEntity(ServerPlayerEntity serverPlayerEntity){
        this.serverPlayerEntity = serverPlayerEntity;
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
        if(!state.equals(State.Uninitialized)){
            ChaosCraft.LOGGER.error("ServerOrgManager.state != " + State.Uninitialized);
            return;
        }
        state = State.QueuedForSpawn;
    }


    public void tickServer(){
        if( this.orgEntity.getBoundingBox() == null){
            return;
        }








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
                outputNeuron._lastValue = neuronAction.getValue();
                outputs.add(outputNeuron);
            }
            Iterator<OutputNeuron> iterator = outputs.iterator();
            while(iterator.hasNext()) {
                OutputNeuron outputNeuron = iterator.next();
                outputNeuron.execute();
            }
            this.neuronActions.clear();
        }
    }
    public void markClientAttached() {
        if(!state.equals(ServerOrgManager.State.Spawned)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        state = State.ClientAttached;
    }
    public void markDead() {
        if(!state.equals(ServerOrgManager.State.Ticking)){
            ChaosCraft.LOGGER.error(getCCNamespace() + " - has invalid state: " + state);
            return;
        }
        state = State.Dead;
    }


    public enum State{
        Uninitialized,
        QueuedForSpawn,
        Spawned,
        ClientAttached,
        Ticking,
        Dead,
    }
}
