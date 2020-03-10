package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/10/18.
 */
public class LookAtTargetOutput extends OutputNeuron {

    public TargetSlot targetSlot;
    public float evaluate() {
        if (getHasBeenEvaluated()) {
            return getCurrentValue();
        }

        if(!targetSlot.hasTarget()){
            return getCurrentValue();//targetSlot.populateDebug();
        }
        return super.evaluate();
    }
    public void execute() {

        Double deltaYaw = targetSlot.getYawDelta();
        if(deltaYaw == null){
            return;
        }
        if(deltaYaw > 30){
            deltaYaw = 30d;
        }else if(deltaYaw < -30){
            deltaYaw = -30d;
        }
        this.nNet.entity.setDesiredYaw(this.nNet.entity.rotationYaw + deltaYaw);
        this.nNet.entity.setDesiredHeadYaw(this.nNet.entity.rotationYaw + deltaYaw);

        Double deltaPitch = targetSlot.getPitchDelta();
        if(deltaPitch > 5d){
            deltaPitch = 5d;
        }else if(deltaPitch < -5d){
            deltaPitch = -5d;
        }


        Vec3d pos = targetSlot.getTargetPositionCenter();

        this.nNet.entity.setDesiredLookPosition(pos);

        this.nNet.entity.setDesiredPitch(this.nNet.entity.rotationPitch + deltaPitch);

    }

    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        if(jsonObject.get("targetSlotId") != null){
            targetSlot = (TargetSlot)nNet.getBiology(jsonObject.get("targetSlotId").toString());
        }

    }
}
