package com.schematical.chaoscraft.ai.activators;

public class SigmoidActivator implements iActivator {
    @Override
    public float activateValue(float val) {
        return (float) (1 / (1 + Math.exp(-val)));
    }
}
