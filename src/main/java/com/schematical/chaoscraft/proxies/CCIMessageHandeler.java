package com.schematical.chaoscraft.proxies;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIMessageHandeler implements IMessageHandler<CCIMessage, IMessage> {
    // Do note that the default constructor is required, but implicitly defined in this case
    public AxisAlignedBB grownBox;
    public Vec3d vec3d;
    @Override public IMessage onMessage(CCIMessage message, MessageContext ctx) {
        // This is the player the packet was sent to the server from
        //EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        // The value that was sent
        JSONObject payload = message.getPayloadJSON();
        JSONArray ouputValues = (JSONArray)payload.get("outputs");
        Iterator<Object> jsonObjectIterable = ouputValues.iterator();
        ChaosCraft.topLeftMessage = payload.get("namespace").toString() + "\n";
        ChaosCraft.topLeftMessage += "Score: " + payload.get("score") + " - Age: " +  payload.get("age") + " / " + payload.get("maxAge") + "\n";
        while(jsonObjectIterable.hasNext()){
            JSONObject ouputValue = (JSONObject)jsonObjectIterable.next();
            ChaosCraft.topLeftMessage += ouputValue.get("summary") + " = " + ouputValue.get("_lastValue") + "\n";
        }
        JSONObject areaOfFocusJSON = (JSONObject)payload.get("areaOfFocus");
        vec3d = new Vec3d(
            (double)areaOfFocusJSON.get("x"),
            (double)areaOfFocusJSON.get("y"),
            (double)areaOfFocusJSON.get("z")
        );
        int dist = Integer.parseInt(areaOfFocusJSON.get("range").toString());
        grownBox = new AxisAlignedBB(
                vec3d.x + dist,
                vec3d.y + dist,
                vec3d.z + dist,
                vec3d.x - dist,
                vec3d.y - dist,
                vec3d.z - dist
        );
        //TODO: Draw Lines around the above box



        // Execute the action on the main server thread by adding it as a scheduled task
       /* serverPlayer.getServerWorld().addScheduledTask(() -> {
            serverPlayer.inventory.addItemStackToInventory(new ItemStack(Items.DIAMOND, amount));
        });*/
        // No response packet
        return null;
    }

}