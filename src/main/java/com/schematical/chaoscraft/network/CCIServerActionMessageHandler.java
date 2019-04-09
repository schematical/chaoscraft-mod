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


        ChaosCraftServerAction serverAction = new ChaosCraftServerAction();


        serverAction.message = message;
        ChaosCraft.server.pendingActions.add(serverAction);


        return null;

    }

}