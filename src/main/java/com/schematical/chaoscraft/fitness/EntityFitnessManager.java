package com.schematical.chaoscraft.fitness;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.events.OrgPredictionEvent;
import com.schematical.chaosnet.model.ChaosNetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 1/4/19.
 */
public class EntityFitnessManager {

  protected HashMap<String, Integer> occurences = new HashMap<String, Integer>();
  public EntityOrganism entityOrganism;
  public List<EntityFitnessScoreEvent> scoreEvents = new ArrayList<EntityFitnessScoreEvent>();

  public EntityFitnessManager(EntityOrganism entityOrganism) {
    this.entityOrganism = entityOrganism;
  }

  public void test(CCWorldEvent event) {

    List<EntityFitnessScoreEvent> _scoreEvents = ChaosCraft.fitnessManager
        .testEntityFitnessEvent(this.entityOrganism, event);
    for (EntityFitnessScoreEvent scoreEvent : _scoreEvents) {

      Integer numOfOccurences = 0;

      if (scoreEvent.fitnessRule == null) {
        throw new ChaosNetException("`scoreEvent.fitnessRule` is `null`");
      }
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
          return;
        }
      }
      scoreEvents.add(scoreEvent);
      Iterator<OrgEvent> eventIterator = entityOrganism.events.iterator();

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
      }
      entityOrganism.events.add(new OrgEvent(scoreEvent));
      occurences.put(scoreEvent.fitnessRule.id, numOfOccurences);
      if (scoreEvent.life != 0) {
        entityOrganism.adjustMaxLife(scoreEvent.life);
      }


    }
  }

  public Double totalScore() {
    Double total = 0d;
    for (EntityFitnessScoreEvent scoreEvent : scoreEvents) {
      total += scoreEvent.getAdjustedScore();
    }
    return total;
  }
}
