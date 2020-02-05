package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetYawInput extends InputNeuron {
    private static final int  YAW_DEGREES = 365;
    private String targetSlotId;
    @Override
    public float evaluate(){
        TargetSlot targetSlot = (TargetSlot) nNet.getBiology(targetSlotId);
        Vec3d targetPosition = targetSlot.getTargetPosition();
        if(targetPosition == null){
            return -1;
        }
        Vec3d vecToTarget = nNet.entity.getPositionVector().subtract(targetPosition);
        double yaw = -Math.atan2(vecToTarget.x, vecToTarget.z);

        double degrees = Math.toDegrees(yaw);
        degrees -= nNet.entity.rotationYaw;
       setCurrentValue((float) degrees / YAW_DEGREES);
        /*if(nNet.entity.getDebug()){
            ChaosCraft.logger.info("TargetYawInput    " + degrees + "  " + _lastValue);
        }*/
        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        targetSlotId = jsonObject.get("targetSlotId").toString();
    }

}
