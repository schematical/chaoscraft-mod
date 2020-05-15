package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Supplier;

public class CCInventoryResyncEventPacket {
    private static final String GLUE = "@";
    public final String orgNamespace;
    public int selectedItemIndex;
    public ItemStackHandler itemStackHandler;


    public CCInventoryResyncEventPacket(String orgNamespace, int selectedItemIndex, ItemStackHandler itemStackHandler)
    {
        this.orgNamespace = orgNamespace;
        this.selectedItemIndex = selectedItemIndex;

        this.itemStackHandler = itemStackHandler;

    }
    public String getOrgNamespace(){
        return orgNamespace;
    }

    public static void encode(CCInventoryResyncEventPacket pkt, PacketBuffer buf)
    {
        buf.writeString(pkt.orgNamespace);
        buf.writeInt(pkt.selectedItemIndex);
        for(int i = 0; i < pkt.itemStackHandler.getSlots(); i++) {
            buf.writeItemStack(pkt.itemStackHandler.getStackInSlot(i));
        }
    }

    public static CCInventoryResyncEventPacket decode(PacketBuffer buf)
    {
       String namespace =  buf.readString();
       int selectedItemIndex = buf.readInt();
        ItemStackHandler itemStackHandler = new ItemStackHandler(36);
        for(int i = 0; i < 36; i++){
            itemStackHandler.setStackInSlot(i, buf.readItemStack());
        }
        return new CCInventoryResyncEventPacket(
                namespace,
                selectedItemIndex,
                itemStackHandler
        );
    }

    public static class Handler
    {
        public static void handle(final CCInventoryResyncEventPacket message, Supplier<NetworkEvent.Context> ctx) {
            //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 1 ");
            ctx.get().enqueueWork(() -> {
                //ChaosCraft.LOGGER.info("recived `CCServerScoreEventPacket` 2: " + message.orgNamespace);
                //Pretty sure the server should get this

                //Load the NNet into memory
                ClientOrgManager clientOrgManager = ChaosCraft.getClient().myOrganisms.get(message.orgNamespace);
                if(clientOrgManager == null){
                    throw new ChaosNetException("Cannot find Org: " + message.orgNamespace);
                }
                OrgEntity orgEntity =  clientOrgManager.getEntity();
                for(int i = 0; i < orgEntity.getItemHandler().getSlots(); i++) {
                    orgEntity.updateInventory(i, message.itemStackHandler.getStackInSlot(i),  message.selectedItemIndex);

                }
                //clientOrgManager.getActionBuffer().clearCurrAction();

            });
            ctx.get().setPacketHandled(true);
        }
    }

}
