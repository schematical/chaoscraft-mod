package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.services.targetnet.ScanManager;

public class TargetNNetManager implements iChaosOrgTickable {
    private final int MAX = 20 * 10;
    private final ScanManager scanManager;
    private  int ticksSinceLastScan = 0;
    public TargetNNetManager(ScanManager scanManager) {
        this.scanManager = scanManager;
        ticksSinceLastScan = (int)  (Math.round(Math.random() * MAX /2));
    }

    @Override
    public void Tick(BaseOrgManager serverOrgManager) {
        if(scanManager.getState().equals(ScanManager.ScanState.Ticking)){
            scanManager.tickScan();
            return;
        }
        //Check if it has been long enough to do this
        ticksSinceLastScan += 1;
        if(ticksSinceLastScan < MAX){
            return;
        }
        ticksSinceLastScan = 0;
        scanManager.resetScan();

    }
}
