package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.AreaOfFocus;

/**
 * Created by user1a on 12/8/18.
 */
public class FocusDistanceInput extends InputNeuron {
    protected AreaOfFocus areaOfFocus;
    @Override

    public float evaluate() {
        if (areaOfFocus == null) {
            areaOfFocus = (AreaOfFocus) nNet.getBiology("AreaOfFocus_0");
        }
        _lastValue = areaOfFocus.currDistance / areaOfFocus.maxFocusDistance;
        return _lastValue;
    }


}
