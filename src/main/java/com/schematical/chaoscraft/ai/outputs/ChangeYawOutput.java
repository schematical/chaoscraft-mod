package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;

/**
 * Created by user1a on 12/10/18.
 */
public class ChangeYawOutput extends OutputNeuron {

  @Override
  public void execute() {

    float delta = reverseSigmoid(this._lastValue);
    if (Math.abs(delta) < ChaosCraft.activationThreshold) {
      return;
    }
    //ChaosCraft.logger.info(nNet.entity.getName() + " ChangeYawOutput: " + this._lastValue + " - " + delta);
    //this.nNet.entity.setDesiredYaw(delta);
    this.nNet.entity.rotationYaw += delta * 15;


  }
}
