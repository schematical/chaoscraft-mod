package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.util.ChaosTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCActionStateChangeEventPacket {

    public final String orgNamespace;
    public ActionBase.ActionState actionState;
    public String correctedChaosTargetSerialized = null;


    public CCActionStateChangeEventPacket(String orgNamespace, ActionBase.ActionState actionState)
    {
        this.orgNamespace = orgNamespace;
        this.actionState = actionState;

    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCActionStateChangeEventPacket pkt, PacketBuffer buf)
    {

        buf.writeString( pkt.orgNamespace);
        buf.writeString(pkt.actionState.toString());
        boolean blnHasCorrectedTarget = pkt.correctedChaosTargetSerialized != null;
        buf.writeBoolean(blnHasCorrectedTarget);
        if(blnHasCorrectedTarget){
            buf.writeString(pkt.correctedChaosTargetSerialized);
        }
    }
    public void setCorrectedChaosTarget(ChaosTarget chaosTarget){
        this.correctedChaosTargetSerialized = chaosTarget.getSerializedString();
    }
    public ChaosTarget getCorrectedChaosTarget(World world){
        if(this.correctedChaosTargetSerialized == null){
            return null;
        }
        return ChaosTarget.deserializeTarget(world, this.correctedChaosTargetSerialized);
    }
    public static CCActionStateChangeEventPacket decode(PacketBuffer buf)
    {

        CCActionStateChangeEventPacket ccActionStateChangeEventPacket = new CCActionStateChangeEventPacket(
            buf.readString(32767),
            ActionBase.ActionState.valueOf( buf.readString(32767))
        );
        if(buf.readBoolean()) {
            ccActionStateChangeEventPacket.correctedChaosTargetSerialized = buf.readString(32767);
        }
        return ccActionStateChangeEventPacket;
    }

    public static class Handler
    {
        public static void handle(final CCActionStateChangeEventPacket message, Supplier<NetworkEvent.Context> ctx) {
            //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ChaosCraft.getClient().attachActionStateChange(message);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
