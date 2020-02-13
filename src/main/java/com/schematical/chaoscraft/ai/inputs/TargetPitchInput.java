package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetPitchInput extends BaseTargetInputNeuron {
    private static final int  PITCH_DEGREES = 180;

    @Override
    public float evaluate(){


        Double degrees = targetHelper.getPitchDelta(this);
        if(degrees != null) {
            setCurrentValue( degrees.floatValue() / PITCH_DEGREES);
        }

        return getCurrentValue();
    }


}
