package com.schematical.chaoscraft.network;


import com.schematical.chaoscraft.server.ChaosCraftServerAction;
import com.schematical.chaosnet.model.Organism;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.json.simple.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIServerActionMessage extends CCIJSONMessage implements IMessage {
    protected ChaosCraftServerAction.Action action;
    protected Organism organism;
    @Override
    public void toBytes(ByteBuf buf) {
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("action", action.toString());
        if(organism != null){
            JSONObject jsonOrg = new JSONObject();
            jsonOrg.put("namespace", organism.getNamespace());
            jsonOrg.put("name", organism.getName());
            jsonOrg.put("speciesNamespace", organism.getSpeciesNamespace());
            jsonOrg.put("generation", organism.getGeneration());
            jsonOrg.put("nNetRaw", organism.getNNetRaw());
            jsonOrg.put("owner_username", organism.getOwnerUsername());
            jsonOrg.put("parentNamespace", organism.getParentNamespace());
            jsonOrg.put("trainingRoomNamespace", organism.getTrainingRoomNamespace());
            jsonPayload.put("organism", jsonOrg);
        }
        //organism.marshall(new JsonProtocolMarshaller<Organism>());
        // Writes the int into the buf
        ByteBufUtils.writeUTF8String(buf, jsonPayload.toJSONString());
    }

    @Override public void fromBytes(ByteBuf buf) {
        // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
        payload = ByteBufUtils.readUTF8String(buf);
        JSONObject jsonPayload = this.getPayloadJSON();
        action = ChaosCraftServerAction.Action.valueOf(jsonPayload.get("action").toString());
        if(jsonPayload.containsKey("organism")) {
            JSONObject jsonOrg = (JSONObject) jsonPayload.get("organism");
            organism = new Organism();
            organism.setNamespace(jsonOrg.get("namespace").toString());
            organism.setName(jsonOrg.get("name").toString());
            organism.setSpeciesNamespace(jsonOrg.get("speciesNamespace").toString());
            organism.setGeneration(Double.parseDouble(jsonOrg.get("generation").toString()));
            organism.setNNetRaw(jsonOrg.get("nNetRaw").toString());
            organism.setOwnerUsername(jsonOrg.get("owner_username").toString());
            organism.setParentNamespace(jsonOrg.get("parentNamespace").toString());
            organism.setTrainingRoomNamespace(jsonOrg.get("trainingRoomNamespace").toString());
        }
    }

    public Organism getOrganism() {
        return organism;
    }
    public void setOrganism(Organism organism) {
        this.organism = organism;
    }
    public void setAction(ChaosCraftServerAction.Action action){
        this.action = action;
    }
}
