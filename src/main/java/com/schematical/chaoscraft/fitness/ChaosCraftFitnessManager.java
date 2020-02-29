package com.schematical.chaoscraft.fitness;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.server.ChaosCraftServer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 1/4/19.
 */
public class ChaosCraftFitnessManager {
    protected List<EntityFitnessRule> rules = new ArrayList<EntityFitnessRule>();
    List<EntityFitnessScoreEvent> scoreEvents = new ArrayList<EntityFitnessScoreEvent>();
    public List<EntityFitnessScoreEvent> testEntityFitnessEvent(OrgEntity entityOrganism, CCWorldEvent event){
            EntityFitnessScoreEvent scoreEvent = null;

            for (EntityFitnessRule rule: rules) {
                scoreEvent = rule.testWorldEvent(event);
                if(scoreEvent != null) {
                    scoreEvents.add(scoreEvent);
               }
            }
        return scoreEvents;
    }

    public void parseData(JSONArray rulesJSON){
        try {

            Iterator<JSONObject> iterator = rulesJSON.iterator();
            while (iterator.hasNext()) {
                JSONObject ruleJSON = iterator.next();


                String eventType = ruleJSON.get("eventType").toString();  // use fully qualified name
                EntityFitnessRule rule = null;
                switch(eventType){
                    case("SOMETHINGCUSTOM"):
                        String fullClassName = "com.schematical.chaoscraft.fitness." + eventType;
                        ChaosCraft.LOGGER.info("Full Class name: " + fullClassName);
                        Class cls = Class.forName(fullClassName);
                        rule = (EntityFitnessRule) cls.newInstance();
                    break;
                    default:
                        rule = new EntityFitnessRule();

                }

                rule.parseData(ruleJSON);
                this.rules.add(rule);
            }


            EntityFitnessRule fitnessRule = new EntityFitnessRule();
            fitnessRule.scoreEffect = 10;
            fitnessRule.id =  CCWorldEvent.Type.BUILD_COMPLETE.toString();
            fitnessRule.eventType = CCWorldEvent.Type.BUILD_COMPLETE.toString();
            fitnessRule.scoreEffect = 10;
            this.rules.add(fitnessRule);



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ChaosCraft.LOGGER.error(rulesJSON.toJSONString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            ChaosCraft.LOGGER.error(rulesJSON.toJSONString());
        } catch (InstantiationException e) {
            e.printStackTrace();
            ChaosCraft.LOGGER.error(rulesJSON.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            ChaosCraft.LOGGER.error(rulesJSON.toJSONString());
        }

    }
}
