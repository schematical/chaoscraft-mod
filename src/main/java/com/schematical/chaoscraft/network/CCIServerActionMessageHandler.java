package com.schematical.chaoscraft.network;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.server.ChaosCraftServerAction;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIServerActionMessageHandler implements IMessageHandler<CCIServerActionMessage, IMessage> {
    // Do note that the default constructor is required, but implicitly defined in this case
    @SideOnly(Side.SERVER)
    //@Override
    public IMessage onMessage(CCIServerActionMessage message, MessageContext ctx) {
        // This is the player the packet was sent to the server from
        //EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        // The value that was sent
        JSONObject payload = message.getPayloadJSON();
        JSONArray actions = (JSONArray)payload.get("actions");
        Iterator<Integer> iterator = actions.iterator();
        while(iterator.hasNext()){
            Integer key = iterator.next();
            ChaosCraftServerAction serverAction = new ChaosCraftServerAction();
            JSONObject jsonAction = (JSONObject) actions.get(key);
            serverAction.orgNamespace = jsonAction.get("namespace").toString();
            serverAction.action = ChaosCraftServerAction.Action.valueOf(jsonAction.get("action").toString());
            serverAction.jsonObject = jsonAction;
            ChaosCraft.server.pendingActions.add(serverAction);
        }

        return null;

    }

}