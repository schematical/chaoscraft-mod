package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.network.packets.CCClientActionPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;

public class BaseChaosEventListener {
    public void onClientTick(ClientOrgManager baseOrgManager){

    }
    public void onServerTick(ServerOrgManager baseOrgManager){

    }
    public void onClientReport(ClientOrgManager baseOrgManager){

    }
    public void onServerSpawn(ServerOrgManager baseOrgManager){

    }

    public void onServerDeath(ServerOrgManager baseOrgManager){

    }
    public void onServerActionComplete(ServerOrgManager serverOrgManager, ActionBase actionBase){

    }
}
