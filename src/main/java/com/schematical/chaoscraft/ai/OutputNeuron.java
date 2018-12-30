package com.schematical.chaoscraft.ai;

/**
 * Created by user1a on 12/8/18.
 */
public abstract class OutputNeuron extends NeuronBase {
    public String _base_type(){
        return com.schematical.chaoscraft.Enum.OUTPUT;
    }
    public abstract void execute();
}
