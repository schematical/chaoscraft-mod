package com.schematical.chaoscraft.ai.outputs.rawnav;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.network.packets.CCClientOutputNeuronActionPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.core.jmx.Server;

/**
 * Created by user1a on 12/10/18.
 */
public abstract class RawOutputNeuron extends OutputNeuron {
    private boolean hasBeenSent = false;
    private Float serverValue = null;


    private ServerOrgManager serverOrgManager;


    public void encode(PacketBuffer buf){


        buf.writeString(this.nNet.entity.getCCNamespace());
        buf.writeString(this.id);
        buf.writeFloat(getCurrentValue());
        buf.writeBoolean(hasBeenSent);
        if(!hasBeenSent) {
            buf.writeString(
                    getClass().getSimpleName().toString()
            );
            buf.writeString(
                    _base_type().substring(0, 1).toLowerCase()
            );
        }
        hasBeenSent = true;

    }
    public static RawOutputNeuron decode(PacketBuffer buf){
       String ccNamespace = buf.readString(32767);
        String id = buf.readString(32767);
        float serverValue =  buf.readFloat();
        boolean hasBeenSent = buf.readBoolean();


        RawOutputNeuron rawOutputNeuron = null;
        ServerOrgManager serverOrgManager = ChaosCraft.getServer().getOrgByNamespace(ccNamespace);
        if(serverOrgManager == null){
            ChaosCraft.LOGGER.error("No `serverOrgManager`: " + ccNamespace);
            return null;
        }
        if(hasBeenSent) {
          rawOutputNeuron = serverOrgManager.getRawOutputNeuron(id);
        }else{
            Class cls = NeuralNet.getClassFromType(buf.readString(32767), buf.readString(32767));
            try {
                rawOutputNeuron = (RawOutputNeuron) cls.newInstance();
                rawOutputNeuron.id = id;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            serverOrgManager.addRawOutputNeuron(rawOutputNeuron);
        }
        if(rawOutputNeuron == null){
            return null;
        }
        rawOutputNeuron.queueToFire(serverValue);
        return rawOutputNeuron;
    }
    public void queueToFire(Float val){
        this.serverValue = val;
    }
    public Float getServerValue(){
        return this.serverValue;
    }
    public void markExecuted(){
        this.serverValue = null;
    }
    public void rawAttach(ServerOrgManager serverOrgManager) {
        this.serverOrgManager = serverOrgManager;
    }

    public OrgEntity getEntity(){
        return serverOrgManager.getEntity();
    };
}
