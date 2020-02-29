package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.TargetHelper;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetDistanceInput extends BaseTargetInputNeuron {


    @Override
    public float evaluate(){

        Double distanceTo = getTarget().getDist();
        if(distanceTo != null) {
            setCurrentValue(1 -(distanceTo.floatValue()/ ScanManager.range));//TODO: Make this a real distance
        }

        return getCurrentValue();
    }


}
