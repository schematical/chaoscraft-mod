package com.schematical.chaoscraft.network;

import akka.serialization.Serializer;
import com.amazonaws.protocol.json.internal.JsonProtocolMarshaller;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.schematical.chaosnet.model.Organism;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.lang.reflect.Type;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIServerActionMessage extends CCIJSONMessage implements IMessage {
    protected Organism organism;
    @Override
    public void toBytes(ByteBuf buf) {
        //organism.marshall(new JsonProtocolMarshaller<Organism>());
        // Writes the int into the buf
        ByteBufUtils.writeUTF8String(buf, "TODO: PUT STUFF HERE");
    }

    @Override public void fromBytes(ByteBuf buf) {
        // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
        payload = ByteBufUtils.readUTF8String(buf);
    }


}
