package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetYawInput extends BaseTargetInputNeuron {
    private static final int  YAW_DEGREES = 360;

    @Override
    public float evaluate(){

        Double degrees = targetHelper.getYawDelta(this);
        if(degrees != null) {
            setCurrentValue(degrees.floatValue() / YAW_DEGREES);
        }



        return getCurrentValue();
    }



}
