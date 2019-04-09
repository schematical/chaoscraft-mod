package com.schematical.chaoscraft.network;

import com.schematical.chaosnet.model.ChaosNetException;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIOutputNeuronMessage implements IMessage {
    protected String payload;
    public String namespace;
    public HashMap<String, Float> map;

    public CCIOutputNeuronMessage(){}


    public CCIOutputNeuronMessage(String payload) {
        this.payload = payload;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if(this.namespace == null){
            throw new ChaosNetException("Invalid `namespace`");
        }
        this.payload = "";
        this.payload += this.namespace +"@";
        Iterator<String> keyIterator = map.keySet().iterator();
        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            this.payload += key + ":" + map.get(key) + ",";
        }
        this.payload = this.payload.substring(0, this.payload.length() - 1);//Remove that last comment

        // Writes the int into the buf
        ByteBufUtils.writeUTF8String(buf, payload);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
        payload = ByteBufUtils.readUTF8String(buf);

        map = new HashMap<String, Float>();
        try {
            String payload = this.payload;
            String[] parts = payload.split("@");
            this.namespace = parts[0];
            payload = parts[1];
            parts = payload.split(",");

            for(String part: parts){
                String[] parts2 = part.split(":");
                if(parts2.length != 2){
                    throw new ChaosNetException("Invalid Output Neuron Value");
                }
                map.put(parts2[0], Float.parseFloat(parts2[1]));
            }
        } catch (Exception e) {
            throw new ChaosNetException("Error ``: " + e.getMessage() + " -> " + this.payload);

        }
    }

    public String getPayload() {
        return this.payload;
    }

}
