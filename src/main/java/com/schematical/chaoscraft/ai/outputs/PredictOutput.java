package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.events.OrgPredictionEvent;

import java.util.Iterator;

/**
 * Created by user1a on 12/8/18.
 */
public class PredictOutput extends OutputNeuron {
/*    public int coolOffStart = -1;
    public int coolOffCurrent = -1;*/
    @Override
    public float evaluate(){
        Iterator<OrgEvent> eventIterator = nNet.entity.getOrgEvents().iterator();
        while(eventIterator.hasNext()) {
            OrgEvent event = eventIterator.next();
            if (event instanceof OrgPredictionEvent) {
                return .5f;
            }
        }
        return super.evaluate();

    }
    @Override
    public void execute() {
        float reversedValue = this.reverseSigmoid(this._lastValue);
        if(Math.abs(reversedValue) < ChaosCraft.activationThreshold){
            return;
        }
        OrgEvent orgEvent = new OrgPredictionEvent(reversedValue, OrgEvent.DEFAULT_TTL);
        nNet.entity.getOrgEvents().add(orgEvent);

    }
}
