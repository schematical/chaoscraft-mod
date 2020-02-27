package com.schematical.chaoscraft.network.packets;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.core.jmx.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lwjgl.opengl.GL;

import java.io.FileReader;
import java.util.UUID;
import java.util.function.Supplier;

public class CCClientActionPacket {

    private static final String GLUE = "@";
    private final String orgNamespace;
    private final Action action;
    private BlockPos blockPos;
    private Entity entity;
    private BiologyBase biologyBase;

    public CCClientActionPacket(String orgNamespace, Action action)
    {
        this.orgNamespace = orgNamespace;
        this.action = action;
    }
    public String getOrgNamespace(){
        return orgNamespace;
    }
    public void setBlockPos(BlockPos blockPos){
        this.blockPos = blockPos;
    }
    public BlockPos getBlockPos(){
       return blockPos;
    }
    public Entity getEntity(){
        return entity;
    }
    public void setEntity(Entity entity){
        this.entity = entity;
    }
    public static void encode(CCClientActionPacket pkt, PacketBuffer buf)
    {

        JSONObject obj = new JSONObject();
        obj.put("orgNamespace", pkt.orgNamespace);
        obj.put("action",pkt.action.toString());

        if(pkt.entity != null){
            obj.put("entity", pkt.entity.getUniqueID());
        }
        if(pkt.blockPos != null){
            obj.put("blockPos", pkt.blockPos.getX() + "," + pkt.blockPos.getY() + "," + pkt.blockPos.getZ());
        }
        if(pkt.biologyBase != null){
            obj.put("biology", pkt.biologyBase.id);
        }
        String payload = obj.toJSONString();
        buf.writeString(payload);
    }

    public static CCClientActionPacket decode(PacketBuffer buf)
    {
        String payload = buf.readString(32767);

        CCClientActionPacket pkt = null;
        try {

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(
                    payload
            );

            pkt = new CCClientActionPacket(
                    obj.get("orgNamespace").toString(),
                    Action.valueOf(obj.get("action").toString())
            );

            if(obj.get("entity") != null){
                pkt.entity = ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD).getEntityByUuid(UUID.fromString(obj.get("entity").toString()));
            }
            if(obj.get("blockPos") != null){
                String[] parts = obj.get("blockPos").toString().split(",");
                pkt.blockPos = new BlockPos(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
                );
            }
            if(obj.get("biology") != null){
                ServerOrgManager serverOrgManager = ChaosCraft.getServer().getOrgByNamespace(pkt.orgNamespace);
                pkt.biologyBase = serverOrgManager.getNNet().getBiology(obj.get("biology"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pkt;
    }

    public void setBiology(BiologyBase biologyBase) {
        this.biologyBase = biologyBase;
    }
    public BiologyBase getBiology(){
        return biologyBase;
    }


    public static class Handler
    {
        public static void handle(final CCClientActionPacket message, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {

                if(message.action.equals(Action.SET_TARGET)){
                    CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TARGET_SELECTED);
                    TargetSlot targetSlot = (TargetSlot)message.biologyBase;
                    if(message.entity != null) {
                        targetSlot.setTarget(message.entity);
                        worldEvent.entity = message.entity;
                    }else if (message.blockPos != null){
                        targetSlot.setTarget(message.blockPos);
                        worldEvent.entity = message.entity;

                    }else{
                        throw new ChaosNetException("No valid target in message");
                    }
                    ServerOrgManager serverOrgManager = ChaosCraft.getServer().getOrgByNamespace(message.orgNamespace);
                    serverOrgManager.getEntity().entityFitnessManager.test(worldEvent);
                }

            });
            ctx.get().setPacketHandled(true);
        }
    }
    public enum Action{
        SET_TARGET
    }
}
