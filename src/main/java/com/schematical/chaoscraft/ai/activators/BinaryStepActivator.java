package com.schematical.chaoscraft.ai.activators;

public class BinaryStepActivator implements iActivator {
    @Override
    public float activateValue(float val) {
        if(val > 0){
            return 1;
        }
        return -1;
    }
}
