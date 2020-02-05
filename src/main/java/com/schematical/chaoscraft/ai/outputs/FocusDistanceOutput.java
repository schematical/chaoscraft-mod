package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.AreaOfFocus;


/**
 * Created by user1a on 12/10/18.
 */
public class FocusDistanceOutput extends OutputNeuron {

    protected AreaOfFocus areaOfFocus;
    @Override
    public void execute() {

        if(areaOfFocus == null){
            areaOfFocus = (AreaOfFocus)nNet.getBiology("AreaOfFocus_0");
        }
        float adjustedValue = (this.getCurrentValue() * 2) - 1;
        if(adjustedValue <= 0){
            areaOfFocus.setDistance(0f);
            return;
        }
        areaOfFocus.setDistance((float)(adjustedValue * areaOfFocus.maxFocusDistance));


    }

}