package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerIntroInfoPacket {
    private static final String GLUE = "@";
    private final String trainingRoomNamespace;
    private final String trainingRoomUsernameNamespace;
    private final String env;

    public ServerIntroInfoPacket(String trainingRoomNamespace, String trainingRoomUsernameNamespace, String env)
    {

        this.trainingRoomNamespace = trainingRoomNamespace;
        this.trainingRoomUsernameNamespace = trainingRoomUsernameNamespace;
        this.env = env;
    }
    public String getTrainingRoomNamespace(){
        return trainingRoomNamespace;
    }
    public String getTrainingRoomUsernameNamespace(){
        return trainingRoomUsernameNamespace;
    }
    public String getEnv(){
        return env;
    }
    public static void encode(ServerIntroInfoPacket pkt, PacketBuffer buf)
    {
        String payload = pkt.trainingRoomNamespace + GLUE + pkt.trainingRoomUsernameNamespace +  GLUE + pkt.env;
        buf.writeString(payload);
    }

    public static ServerIntroInfoPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);
        return new ServerIntroInfoPacket(parts[0], parts[1], parts[2]);//(buf.readVarInt()
    }

    public static class Handler
    {
        public static void handle(final ServerIntroInfoPacket message, Supplier<NetworkEvent.Context> ctx) {
            ChaosCraft.LOGGER.info("recived `ServerIntroInfoPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                ChaosCraft.LOGGER.info("recived `ServerIntroInfoPacket` 2");
                //Pretty sure the client should get this
                ChaosCraft.getClient().setTrainingRoomInfo(message);


            });
            ctx.get().setPacketHandled(true);
        }
    }
}
