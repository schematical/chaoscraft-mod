package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;

/**
 * Created by user1a on 12/8/18.
 */
public class ChatOutput extends OutputNeuron {

  @Override
  public void execute() {
    if (this._lastValue <= .5) {
      return;
    }
    ChaosCraft.chat("I see something");


  }
}
