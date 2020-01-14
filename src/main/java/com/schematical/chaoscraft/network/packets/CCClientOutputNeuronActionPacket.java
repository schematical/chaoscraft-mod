package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientOutputNeuronActionPacket {
    private static final String GLUE = "@";
    private final String orgNamespace;
    private final String neuronId;
    private final float value;

    public CCClientOutputNeuronActionPacket(String orgNamespace, String neuronId, float value)
    {
        this.orgNamespace = orgNamespace;
        this.neuronId = neuronId;
        this.value = value;
    }
    public String getOrgNamespace(){
        return orgNamespace;
    }
    public String getNeuronId(){
        return this.neuronId;
    }
    public float getValue(){
        return value;
    }
    public static void encode(CCClientOutputNeuronActionPacket pkt, PacketBuffer buf)
    {
        String payload = pkt.orgNamespace + GLUE + pkt.neuronId + GLUE +  pkt.value;
        buf.writeString(payload);
    }

    public static CCClientOutputNeuronActionPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);
        return new CCClientOutputNeuronActionPacket(parts[0], parts[1], Float.parseFloat(parts[2]));//(buf.readVarInt()
    }

    public static class Handler
    {
        public static void handle(final CCClientOutputNeuronActionPacket message, Supplier<NetworkEvent.Context> ctx) {
            ChaosCraft.LOGGER.info("recived `CCClientOutputNeuronActionPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                ChaosCraft.LOGGER.info("recived `CCClientOutputNeuronActionPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getServer().processClientOutputNeuronActionPacket(message);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
