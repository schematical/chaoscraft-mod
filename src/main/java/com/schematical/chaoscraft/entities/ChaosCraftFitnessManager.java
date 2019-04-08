package com.schematical.chaoscraft.entities;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.fitness.EntityFitnessRule;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 1/4/19.
 */
public class ChaosCraftFitnessManager {

  protected List<EntityFitnessRule> rules = new ArrayList<EntityFitnessRule>();

  public List<EntityFitnessScoreEvent> testEntityFitnessEvent(EntityOrganism entityOrganism,
      CCWorldEvent event) {
    List<EntityFitnessScoreEvent> scoreEvents = new ArrayList<EntityFitnessScoreEvent>();
    EntityFitnessScoreEvent scoreEvent = null;
    for (EntityFitnessRule rule : rules) {
      scoreEvent = rule.testWorldEvent(event);
      if (scoreEvent != null) {
        scoreEvents.add(scoreEvent);
      }

    }

    return scoreEvents;
  }

  public void parseData(JSONArray rulesJSON) {
    try {

      Iterator<JSONObject> iterator = rulesJSON.iterator();
      while (iterator.hasNext()) {
        JSONObject ruleJSON = iterator.next();

        String eventType = ruleJSON.get("eventType").toString();  // use fully qualified name
        EntityFitnessRule rule = null;
        switch (eventType) {
          case ("SOMETHINGCUSTOM"):
            String fullClassName = "com.schematical.chaoscraft.fitness." + eventType;
            ChaosCraft.logger.info("Full Class name: " + fullClassName);
            Class cls = Class.forName(fullClassName);
            rule = (EntityFitnessRule) cls.newInstance();
            break;
          default:
            rule = new EntityFitnessRule();

        }

        rule.parseData(ruleJSON);
        this.rules.add(rule);
      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }

  }
}
