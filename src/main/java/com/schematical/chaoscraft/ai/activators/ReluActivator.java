package com.schematical.chaoscraft.ai.activators;

public class ReluActivator implements iActivator {
    @Override
    public float activateValue(float val) {

        if(val > 0){
            return val;
        }
        return 0;
    }
}
