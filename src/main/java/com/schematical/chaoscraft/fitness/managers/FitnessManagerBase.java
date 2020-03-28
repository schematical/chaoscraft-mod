package com.schematical.chaoscraft.fitness.managers;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.fitness.FitnessRun;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;

import java.util.HashMap;

public abstract class FitnessManagerBase {
       protected int currRunIndex = -1;
       protected HashMap<Integer, FitnessRun> fitnessRuns = new HashMap<Integer, FitnessRun>();
       public ServerOrgManager serverOrgManager;
       public abstract void test(CCWorldEvent event);
       public FitnessManagerBase(ServerOrgManager serverOrgManager) {
              this.serverOrgManager = serverOrgManager;
              addNewRun();
       }
       public FitnessRun getCurrFitnessRun(){
              return fitnessRuns.get(currRunIndex);
       }
       public FitnessRun addNewRun(){
              currRunIndex += 1;
              return fitnessRuns.put(currRunIndex, new FitnessRun());
       }
       protected void addScoreEvent(EntityFitnessScoreEvent scoreEvent) {
              getCurrFitnessRun().scoreEvents.add(scoreEvent);
              String orgCCNamespace = serverOrgManager.getCCNamespace();
              OrgEntity orgEntity = serverOrgManager.getEntity();
              //Send score event
              CCServerScoreEventPacket serverScoreEventPacket = new CCServerScoreEventPacket(
                      orgCCNamespace,
                      scoreEvent.score,
                      scoreEvent.life,
                      scoreEvent.fitnessRule.id,
                      scoreEvent.multiplier,
                      (int) (orgEntity.world.getGameTime() + ((serverOrgManager.getMaxLife() - serverOrgManager.getAgeSeconds()) * 20)),
                      currRunIndex
              );
              ChaosNetworkManager.sendTo(serverScoreEventPacket, serverOrgManager.getServerPlayerEntity());

       }
}
