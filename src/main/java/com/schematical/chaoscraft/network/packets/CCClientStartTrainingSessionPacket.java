package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientStartTrainingSessionPacket {
    private static final String GLUE = "@";
    private final String trainingRoomUsername;
    private final String trainingRoomNamespace;
    private final String env;


    public CCClientStartTrainingSessionPacket(String trainingRoomUsername, String trainingRoomNamespace, String env)
    {

        this.trainingRoomUsername = trainingRoomUsername;
        this.trainingRoomNamespace = trainingRoomNamespace;
        this.env = env;
    }
    public String getTrainingRoomUsername(){
        return trainingRoomUsername;
    }
    public String getTrainingRoomNamespace(){
        return trainingRoomNamespace;
    }
    public String getEnv(){ return  env; }
    public static void encode(CCClientStartTrainingSessionPacket pkt, PacketBuffer buf)
    {
        String payload = pkt.trainingRoomUsername + GLUE + pkt.trainingRoomNamespace + GLUE + pkt.env;
        buf.writeString(payload);
    }

    public static CCClientStartTrainingSessionPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);
        return new CCClientStartTrainingSessionPacket(parts[0], parts[1], parts[2]);//(buf.readVarInt()
    }

    public static class Handler
    {
        public static void handle(final CCClientStartTrainingSessionPacket message, Supplier<NetworkEvent.Context> ctx) {
           // ChaosCraft.LOGGER.info("recived `CCClientSpawnPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                ChaosCraft.LOGGER.info("recived `CCClientStartTrainingSessionPacket` 2: " +  message.trainingRoomUsername + "/trainingrooms/" + message.trainingRoomNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getServer().setTrainingRoom(message.trainingRoomUsername, message.trainingRoomNamespace, message.env);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
