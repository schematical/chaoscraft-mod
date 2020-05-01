package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.outputs.rawnav.RawOutputNeuron;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientOutputNeuronActionPacket {


    private final RawOutputNeuron neuron;

    public CCClientOutputNeuronActionPacket( RawOutputNeuron neuron)
    {

        this.neuron = neuron;
    }

    public OutputNeuron getNeuron(){
        return this.neuron;
    }

    public static void encode(CCClientOutputNeuronActionPacket pkt, PacketBuffer buf)
    {

        pkt.neuron.encode(buf);
    }

    public static CCClientOutputNeuronActionPacket decode(PacketBuffer buf)
    {

        RawOutputNeuron rawOutputNeuron = RawOutputNeuron.decode(buf);
        return new CCClientOutputNeuronActionPacket( rawOutputNeuron);//(buf.readVarInt()
    }

    public static class Handler
    {
        public static void handle(final CCClientOutputNeuronActionPacket message, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {

                //Pretty sure the server should get this

                //Load the NNet into memory
               // ChaosCraft.getServer().processClientOutputNeuronActionPacket(message);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
