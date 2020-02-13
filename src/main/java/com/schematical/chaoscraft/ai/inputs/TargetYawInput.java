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

        Vec3d targetPosition = getTargetPosition();
        if(targetPosition == null){
            return getCurrentValue();
        }
        Vec3d lookVec = getEntity().getLookVec();
        Vec3d vecToTarget = targetPosition.subtract(getEntity().getEyePosition(1));
        double yaw = - Math.atan2(vecToTarget.x, vecToTarget.z);
        double degrees = Math.toDegrees(yaw);

        double lookYaw = - Math.atan2(lookVec.x, lookVec.z);
        double lookDeg = Math.toDegrees(lookYaw);
        degrees -= lookDeg;
       setCurrentValue((float) degrees / YAW_DEGREES);
        /*if(nNet.entity.getDebug()){
            ChaosCraft.logger.info("TargetYawInput    " + degrees + "  " + _lastValue);
        }*/
        return getCurrentValue();
    }



}
