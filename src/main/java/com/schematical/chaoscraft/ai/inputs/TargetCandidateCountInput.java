package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetCandidateCountInput extends InputNeuron {



    @Override
    public float evaluate(){
        ScanManager scanManager =  ((OrgEntity)this.getEntity()).getClientOrgManager().getScanManager();

        int count = scanManager.getCountOfFocusedScanEntity();
        if(count != -1) {
            setCurrentValue(count);
        }



        return getCurrentValue();
    }


}
