package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.events.OrgPredictionEvent;
import com.schematical.chaosnet.model.ChaosNetException;
import org.json.simple.JSONObject;

import java.util.Iterator;

/**
 * Created by user1a on 12/8/18.
 */
public class EventInput extends InputNeuron {
    public static final String PREDICTION = "PREDICTION";
    public static final String HEALTH_CHANGE = "HEALTH_CHANGE";
    public static final String SCORE_EVENT = "SCORE_EVENT";
    protected String eventType;
    @Override
    public float evaluate(){
        float newValue = 0;
        Iterator<OrgEvent> eventIterator = nNet.entity.getOrgEvents().iterator();
        while(eventIterator.hasNext()){
            OrgEvent event = eventIterator.next();
            //TODO: Test if event is what we are looking for
            //Health
            //Reward
            //Reward prediction?
            switch(eventType){
                case(PREDICTION):
                    if(event instanceof OrgPredictionEvent){
                        OrgPredictionEvent orgPredictionEvent = (OrgPredictionEvent) event;
                        newValue += orgPredictionEvent.weight;
                    }
                break;
                case(SCORE_EVENT):
                    if(
                        event.getScoreEvent() != null
                    ) {
                        newValue += event.getScoreEvent().score * event.getTTLWeight();
                    }
                    break;
                case(HEALTH_CHANGE):
                    CCWorldEvent worldEvent = event.getWorldEvent();
                    if(
                        worldEvent != null &&
                            worldEvent.eventType == CCWorldEvent.Type.HEALTH_CHANGE
                    ) {
                        float eventTTLWeight = event.getTTLWeight();

                        float value = eventTTLWeight * worldEvent.amount;
                        newValue += value;
                    }
                break;
                default:
                    throw new ChaosNetException("Invalid `EventInput.eventType`: " + eventType);

            }



        }
        setCurrentValue(newValue);
        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        eventType = jsonObject.get("eventType").toString();
    }

}
