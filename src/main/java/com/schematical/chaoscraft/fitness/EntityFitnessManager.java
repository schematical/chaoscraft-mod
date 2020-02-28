package com.schematical.chaoscraft.fitness;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ChaosClientThread;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.events.OrgPredictionEvent;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;
import org.apache.logging.log4j.spi.LoggerRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.schematical.chaoscraft.ChaosCraft.LOGGER;

/**
 * Created by user1a on 1/4/19.
 */
public class EntityFitnessManager {
    protected int currRunIndex = -1;
    protected HashMap<Integer, FitnessRun> fitnessRuns = new HashMap<Integer, FitnessRun>();
    protected HashMap<String, Integer> occurences = new HashMap<String, Integer>();
    public OrgEntity orgEntity;
   // public List<EntityFitnessScoreEvent> scoreEvents = new ArrayList<EntityFitnessScoreEvent>();

    public EntityFitnessManager(OrgEntity orgEntity) {
        this.orgEntity = orgEntity;
    }

    public FitnessRun getCurrFitnessRun(){
        return fitnessRuns.get(currRunIndex);
    }
    public FitnessRun addNewRun(){
        currRunIndex += 1;
        return fitnessRuns.put(currRunIndex, new FitnessRun());
    }
    public void test(CCWorldEvent event){
            List<EntityFitnessScoreEvent> _scoreEvents = ChaosCraft.getServer().fitnessManager.testEntityFitnessEvent(this.orgEntity, event);
            Iterator<EntityFitnessScoreEvent> iterator = _scoreEvents.iterator();
            while (iterator.hasNext()) {
                EntityFitnessScoreEvent scoreEvent = iterator.next();
                Integer numOfOccurences = 0;

                if (scoreEvent.fitnessRule == null) {
                    throw new ChaosNetException("`scoreEvent.fitnessRule` is `null`");
                }
                boolean isValid = true;
                if (scoreEvent.fitnessRule.maxOccurrences != -1) {
                    if (
                            occurences.containsKey(
                                    scoreEvent.fitnessRule.id
                            )
                    ) {

                        numOfOccurences = occurences.get(scoreEvent.fitnessRule.id);
                    }
                    numOfOccurences += 1;
                    if (numOfOccurences > scoreEvent.fitnessRule.maxOccurrences) {
                        isValid = false;
                    }
                }
                if (isValid) {
                    if (scoreEvent.worldEvent.extraMultiplier != 0) {
                        scoreEvent.multiplier = scoreEvent.worldEvent.extraMultiplier;
                    }
                    addScoreEvent(scoreEvent);
                /*Iterator<OrgEvent> eventIterator = orgEntity.getOrgEvents().iterator();

                while (eventIterator.hasNext()) {
                    OrgEvent orgEvent = eventIterator.next();
                    //Check to see if there is a reward prediction
                    if (orgEvent instanceof OrgPredictionEvent) {
                        OrgPredictionEvent orgPredictionEvent = (OrgPredictionEvent) orgEvent;
                        float multiplier = 1;
                        if (orgPredictionEvent.weight > 0) {
                            if (scoreEvent.score > 0) {
                                //Bonus
                                multiplier += orgPredictionEvent.weight;
                            } else {
                                //Penalize
                                multiplier -= orgPredictionEvent.weight;

                            }
                        } else {
                            if (scoreEvent.score < 0) {
                                //Bonus
                                multiplier -= orgPredictionEvent.weight;
                            } else {
                                //Penalize
                                multiplier += orgPredictionEvent.weight;
                            }

                        }
                        scoreEvent.multiplier = multiplier;

                    }
                }*/
                    orgEntity.addOrgEvent(new OrgEvent(scoreEvent));
                    occurences.put(scoreEvent.fitnessRule.id, numOfOccurences);
                    if (scoreEvent.life != 0) {
                        orgEntity.getServerOrgManager().adjustMaxLife(scoreEvent.life);
                    }
                }

            }
        }

    private void addScoreEvent(EntityFitnessScoreEvent scoreEvent) {
        if(getCurrFitnessRun()==null)
        {
            LOGGER.error("Nope, getCurrentFitnessRun is null");
            return;
        }
        getCurrFitnessRun().scoreEvents.add(scoreEvent);
        ServerOrgManager serverOrgManager = orgEntity.getServerOrgManager();
        //Send score event
        CCServerScoreEventPacket serverScoreEventPacket = new CCServerScoreEventPacket(
                serverOrgManager.getCCNamespace(),
                scoreEvent.score,
                scoreEvent.life,
                scoreEvent.fitnessRule.id,
                scoreEvent.multiplier,
                (int) (orgEntity.world.getGameTime() + ((orgEntity.getServerOrgManager().getMaxLife() - orgEntity.getServerOrgManager().getAgeSeconds()) * 20)),
                currRunIndex
        );
        ChaosNetworkManager.sendTo(serverScoreEventPacket, serverOrgManager.getServerPlayerEntity());

    }

/*    public Double totalScore() {
        Double total = 0d;
        for (EntityFitnessScoreEvent scoreEvent: scoreEvents) {
            total += scoreEvent.getAdjustedScore();
        }
        return total;
    }*/
}
