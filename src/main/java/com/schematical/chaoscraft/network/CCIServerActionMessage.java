package com.schematical.chaoscraft.network;


import com.schematical.chaoscraft.server.ChaosCraftServerAction;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.Organism;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.apache.commons.lang3.Validate;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIServerActionMessage implements IMessage {
    protected String payload;
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

        // Writes the int into the buf

        //ByteBufUtils.writeUTF8String(buf, jsonPayload.toJSONString());

        byte[] utf8Bytes = jsonPayload.toJSONString().getBytes(StandardCharsets.UTF_8);
        //Validate.isTrue(varIntByteCount(utf8Bytes.length) < 3, "The string is too long for this encoding.");
        ByteBufUtils.writeVarInt(buf, utf8Bytes.length, 32);
        buf.writeBytes(utf8Bytes);
    }

    @Override public void fromBytes(ByteBuf buf) {

        int len = ByteBufUtils.readVarInt(buf,5);
        payload = buf.toString(buf.readerIndex(), len, StandardCharsets.UTF_8);
        buf.readerIndex(buf.readerIndex() + len);

        // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
        //payload = ByteBufUtils.readUTF8String(buf);
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
            //organism.setParentNamespace(jsonOrg.get("parentNamespace").toString());
            organism.setTrainingRoomNamespace(jsonOrg.get("trainingRoomNamespace").toString());
        }
    }

    public Organism getOrganism() {
        return organism;
    }
    public ChaosCraftServerAction.Action getAction(){
        return action;
    }
    public void setOrganism(Organism organism) {
        this.organism = organism;
    }
    public void setAction(ChaosCraftServerAction.Action action){
        this.action = action;
    }
    public JSONObject getPayloadJSON() {

        try {

            JSONParser parser = new JSONParser();
            return  (JSONObject) parser.parse(
                this.payload
            );
        } catch (Exception e) {
            throw new ChaosNetException("Error `JSON.parse`: " + e.getMessage() + " -> " + this.payload);

        }

    }
}
