package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetPitchInput extends BaseTargetInputNeuron {
    private static final int  PITCH_DEGREES = 180;

    @Override
    public float evaluate(){
        Vec3d targetPosition = getTargetPosition();
        if(targetPosition == null){
            return getCurrentValue();
        }
        Vec3d lookVec = nNet.entity.getLookVec();
        Vec3d vecToTarget = nNet.entity.getEyePosition(1).subtract(targetPosition);
        double pitch = -Math.atan2(vecToTarget.y, Math.sqrt(Math.pow(vecToTarget.x, 2) + Math.pow(vecToTarget.z, 2)));
        double degrees = Math.toDegrees(pitch);

        double lookPitch = -Math.atan2(lookVec.y, Math.sqrt(Math.pow(lookVec.x, 2) + Math.pow(lookVec.z, 2)));
        double lookDeg = Math.toDegrees(lookPitch);
        degrees -= lookDeg;
        setCurrentValue((float) degrees / PITCH_DEGREES);

        return getCurrentValue();
    }


}
