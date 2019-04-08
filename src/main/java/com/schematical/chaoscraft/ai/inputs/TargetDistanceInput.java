package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetDistanceInput extends InputNeuron {

  private static final int YAW_DEGREES = 365;
  private String targetSlotId;

  @Override
  public float evaluate() {
    TargetSlot targetSlot = (TargetSlot) nNet.getBiology(targetSlotId);
    Vec3d targetPosition = targetSlot.getTargetPosition();
    if (targetPosition == null) {
      return -1;
    }
    double distanceTo = nNet.entity.getPositionVector().distanceTo(targetPosition);

    _lastValue = (float) distanceTo / 5;//TODO: Make this a real distance
    if (nNet.entity.getDebug()) {
      ChaosCraft.logger.info("TargetDistanceInput    " + distanceTo + "  " + _lastValue);
    }
    return _lastValue;
  }

  @Override
  public void parseData(JSONObject jsonObject) {
    super.parseData(jsonObject);
    targetSlotId = jsonObject.get("targetSlotId").toString();
  }

}
