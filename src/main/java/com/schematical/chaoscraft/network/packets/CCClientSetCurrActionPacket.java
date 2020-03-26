package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCClientSetCurrActionPacket {

    public final String orgNamespace;
    public ActionBase actionBase;


    public CCClientSetCurrActionPacket(String orgNamespace, ActionBase actionBase)
    {
        this.orgNamespace = orgNamespace;
        this.actionBase = actionBase;

    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCClientSetCurrActionPacket pkt, PacketBuffer buf)
    {
        buf.writeString(pkt.orgNamespace);
        buf.writeString(pkt.actionBase.getClass().getName());
        buf.writeString(pkt.actionBase.getTarget().getSerializedString());
    }

    public static CCClientSetCurrActionPacket decode(PacketBuffer buf)
    {
        try {
            String orgNamespace = buf.readString();
            Class cls = null;
            cls = Class.forName(buf.readString());

            ActionBase actionBase = (ActionBase)cls.newInstance();
            actionBase.setTarget(
                ChaosTarget.deserializeTarget(
                    ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD),
                    buf.readString()
                )
            );
            return new CCClientSetCurrActionPacket(
                    orgNamespace,
                    actionBase
            );
        } catch (ClassNotFoundException e) {
            throw new ChaosNetException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ChaosNetException(e.getMessage());
        } catch (InstantiationException e) {
            throw new ChaosNetException(e.getMessage());
        }
    }

    public static class Handler
    {
        public static void handle(final CCClientSetCurrActionPacket message, Supplier<NetworkEvent.Context> ctx) {
            //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ServerOrgManager serverOrgManager = ChaosCraft.getServer().getOrgByNamespace(message.orgNamespace);
                if(serverOrgManager == null){
                    throw new ChaosNetException("Cannot find Org: " + message.orgNamespace);
                }
                serverOrgManager.getActionBuffer().addAction(message.actionBase);

            });
            ctx.get().setPacketHandled(true);
        }
    }

}
