package com.schematical.chaoscraft.fitness.managers;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.fitness.FitnessRun;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 1/4/19.
 */
public class EntityRuleFitnessManager extends FitnessManagerBase {

    protected HashMap<String, Integer> occurences = new HashMap<String, Integer>();
    public EntityRuleFitnessManager(ServerOrgManager serverOrgManager) {
        super(serverOrgManager);
    }

    // public List<EntityFitnessScoreEvent> scoreEvents = new ArrayList<EntityFitnessScoreEvent>();




    public void test(CCWorldEvent event){


        List<EntityFitnessScoreEvent> _scoreEvents = ChaosCraft.getServer().trainingRoomRoles.get(serverOrgManager.getOrganism().getTrainingRoomRoleNamespace()).fitnessManager.testEntityFitnessEvent(this.serverOrgManager.getEntity(), event);
        Iterator<EntityFitnessScoreEvent> iterator =_scoreEvents.iterator();
        while(iterator.hasNext()){
            EntityFitnessScoreEvent scoreEvent = iterator.next();
            Integer numOfOccurences = 0;

            if(scoreEvent.fitnessRule == null){
                throw new ChaosNetException("`scoreEvent.fitnessRule` is `null`");
            }
            boolean isValid = true;
            if(scoreEvent.fitnessRule.maxOccurrences != -1) {
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
            if(isValid) {
                if (scoreEvent.worldEvent.extraMultiplier != 0) {
                    scoreEvent.multiplier = scoreEvent.worldEvent.extraMultiplier;
                }
                occurences.put(scoreEvent.fitnessRule.id, numOfOccurences);
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

            }

        }
    }


}
