package com.schematical.chaoscraft.networking;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class orgToClient implements IMessage {

    private boolean messageGoodToGo;

    private EntityOrganism org;



    public orgToClient() {
        this.messageGoodToGo = false;
    }

    public orgToClient(EntityOrganism org) {
        this.org = org;
        this.messageGoodToGo = true;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
        } catch (IndexOutOfBoundsException ioe) {
            ChaosCraft.logger.catching(ioe);
            return;
        }
        this.messageGoodToGo = true;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if(!this.messageGoodToGo) { return; }
        ByteBufUtils.writeUTF8String(buf, org.getName());
    }
}
