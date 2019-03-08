package com.schematical.chaoscraft.proxies;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIMessageHandeler implements IMessageHandler<CCIMessage, IMessage> {
    // Do note that the default constructor is required, but implicitly defined in this case

    @Override public IMessage onMessage(CCIMessage message, MessageContext ctx) {
        // This is the player the packet was sent to the server from
        //EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        // The value that was sent
        JSONObject payload = message.getPayloadJSON();
        JSONArray ouputValues = (JSONArray)payload.get("outputs");
        Iterator<Object> jsonObjectIterable = ouputValues.iterator();
        ChaosCraft.topLeftMessage = payload.get("namespace").toString() + "\n";
        ChaosCraft.topLeftMessage += "Score: " + payload.get("score") + "\n";
        while(jsonObjectIterable.hasNext()){
            JSONObject ouputValue = (JSONObject)jsonObjectIterable.next();
            ChaosCraft.topLeftMessage += ouputValue.get("summary") + " - " + ouputValue.get("_lastValue") + "\n";
        }

        // Execute the action on the main server thread by adding it as a scheduled task
       /* serverPlayer.getServerWorld().addScheduledTask(() -> {
            serverPlayer.inventory.addItemStackToInventory(new ItemStack(Items.DIAMOND, amount));
        });*/
        // No response packet
        return null;
    }
}