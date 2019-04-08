package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;

public class SneakOutput extends OutputNeuron {

  public void execute() {
    if (this._lastValue <= .5) {
      nNet.entity.setSneaking(false);
      return;
    }
    nNet.entity.setSneaking(true);
  }
}
