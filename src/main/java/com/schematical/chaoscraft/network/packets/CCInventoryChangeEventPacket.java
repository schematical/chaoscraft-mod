package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCInventoryChangeEventPacket {
    private static final String GLUE = "@";
    public final String orgNamespace;
    public int selectedItemIndex;
    public int index;
    public ItemStack itemStack;


    public CCInventoryChangeEventPacket(String orgNamespace, int selectedItemIndex, int index, ItemStack itemStack)
    {
        this.orgNamespace = orgNamespace;
        this.selectedItemIndex = selectedItemIndex;
        this.index = index;
        this.itemStack = itemStack;

    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCInventoryChangeEventPacket pkt, PacketBuffer buf)
    {
        buf.writeString(pkt.orgNamespace);
        buf.writeInt(pkt.selectedItemIndex);
        buf.writeInt(pkt.index);
        buf.writeItemStack(pkt.itemStack);
    }

    public static CCInventoryChangeEventPacket decode(PacketBuffer buf)
    {

        return new CCInventoryChangeEventPacket(
                buf.readString(),
                buf.readInt(),
                buf.readInt(),
                buf.readItemStack()
        );
    }

    public static class Handler
    {
        public static void handle(final CCInventoryChangeEventPacket message, Supplier<NetworkEvent.Context> ctx) {
            //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ClientOrgManager clientOrgManager = ChaosCraft.getClient().myOrganisms.get(message.orgNamespace);
                if(clientOrgManager == null){
                    throw new ChaosNetException("Cannot find Org: " + message.orgNamespace);
                }
                clientOrgManager.getEntity().updateInventory(message.index, message.itemStack, message.selectedItemIndex);

            });
            ctx.get().setPacketHandled(true);
        }
    }

}
