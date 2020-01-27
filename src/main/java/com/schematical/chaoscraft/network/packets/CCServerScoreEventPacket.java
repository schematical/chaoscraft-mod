package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCServerScoreEventPacket {
    private static final String GLUE = "@";
    public final String orgNamespace;
    public int score;
    public int life;
    public String fitnessRuleId;
    public float multiplier = 1;

    public CCServerScoreEventPacket(String orgNamespace, int score, int life, String fitnessRuleId, float multiplier)
    {
        this.orgNamespace = orgNamespace;
        this.score = score;
        this.life = life;
        this.fitnessRuleId = fitnessRuleId;
        this.multiplier = multiplier;
    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCServerScoreEventPacket pkt, PacketBuffer buf)
    {
        String payload = pkt.orgNamespace + GLUE + pkt.score + GLUE + pkt.life + GLUE + pkt.fitnessRuleId + GLUE + pkt.multiplier;
        buf.writeString(payload);
    }

    public static CCServerScoreEventPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);
        String[] parts = payload.split(GLUE);
        return new CCServerScoreEventPacket(
            parts[0],
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2]),
            parts[3],
            Float.parseFloat(parts[4])
        );
    }

    public static class Handler
    {
        public static void handle(final CCServerScoreEventPacket message, Supplier<NetworkEvent.Context> ctx) {
            ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getClient().attachScoreEventToEntity(message);

            });
            ctx.get().setPacketHandled(true);
        }
    }
    public float getAdjustedScore(){
        return Math.round(score * multiplier);
    }
}
