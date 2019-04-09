package com.schematical.chaoscraft.network;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by user1a on 3/8/19.
 */
public class CCIOutputNeuronMessageHandler implements IMessageHandler<CCIOutputNeuronMessage, IMessage> {
    // Do note that the default constructor is required, but implicitly defined in this case
    @SideOnly(Side.SERVER)
    @Override
    public IMessage onMessage(CCIOutputNeuronMessage message, MessageContext ctx) {
        EntityOrganism org = ChaosCraft.server.getEntityOrganismById(message.namespace);

        if(org == null){
            throw new ChaosNetException("Could not find org: " + message.namespace);
        }
        org.setLastOutputNeuronMessage(message);
        return null;

    }

}