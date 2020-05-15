package com.schematical.chaoscraft.ai.inputs.targetcandidate;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.ai.inputs.BaseTargetInputNeuron;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetYawInput extends BaseTargetInputNeuron {
    private static final float  YAW_DEGREES = 360f;

    @Override
    public float evaluate(){

        Double degrees = getTarget().getYawDelta();
        if(degrees != null) {
            setCurrentValue(degrees.floatValue() / YAW_DEGREES);
        }



        return getCurrentValue();
    }



}
