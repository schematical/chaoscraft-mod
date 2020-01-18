package com.schematical.chaoscraft.client;

import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;

import java.util.ArrayList;

public class ClientOrgEntity {
    protected ArrayList<CCServerScoreEventPacket> serverScoreEvents = new ArrayList<CCServerScoreEventPacket>();
    public void addServerScoreEvent(CCServerScoreEventPacket pkt){
        serverScoreEvents.add(pkt);
    }
    public Double getServerScoreEventTotal(){
        Double total = 0d;
        for (CCServerScoreEventPacket serverScoreEvent: serverScoreEvents) {
            total += serverScoreEvent.getAdjustedScore();
        }
        return total;
    }
}
