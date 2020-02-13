package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetDistanceInput extends BaseTargetInputNeuron {


    @Override
    public float evaluate(){
        Vec3d targetPosition = getTargetPosition();
        if(targetPosition == null){
            return getCurrentValue();
        }
        double distanceTo = getEntity().getPositionVector().distanceTo(targetPosition);

        setCurrentValue((float) (distanceTo * -1) + 2);//TODO: Make this a real distance
        /*if(nNet.entity.getDebug()){
            ChaosCraft.logger.info("TargetDistanceInput    " + distanceTo + "  " + _lastValue);
        }*/
        return getCurrentValue();
    }


}
