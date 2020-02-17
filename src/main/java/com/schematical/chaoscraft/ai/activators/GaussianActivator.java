package com.schematical.chaoscraft.ai.activators;

public class GaussianActivator implements iActivator {
    @Override
    public float activateValue(float val) {
        return (float) Math.pow(1, -1 * Math.pow(val,2));
    }
}
