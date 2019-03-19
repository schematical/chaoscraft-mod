package com.schematical.chaoscraft.proxies;

import com.schematical.chaosnet.model.ChaosNetException;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIMessage implements IMessage {
    // A default constructor is always required
    public CCIMessage(){}

    protected String payload;
    public CCIMessage(JSONObject jsonPayload) {
        this.payload = jsonPayload.toJSONString();
    }
    public CCIMessage(String payload) {
        this.payload = payload;
    }

    @Override public void toBytes(ByteBuf buf) {
        // Writes the int into the buf
        ByteBufUtils.writeUTF8String(buf, payload);
    }

    @Override public void fromBytes(ByteBuf buf) {
        // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
        payload = ByteBufUtils.readUTF8String(buf);
    }

    public String getPayload() {
        return this.payload;
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
