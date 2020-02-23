package com.schematical.chaoscraft.ai.inputs.targetnet;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.TargetHelper;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetCandidateCountInput extends InputNeuron {



    @Override
    public float evaluate(){
        ScanManager scanManager =  ((OrgEntity)this.getEntity()).getClientOrgManager().getScanManager();

        int count = scanManager.getCountOfFocusedScanEntity();

        setCurrentValue(count);



        return getCurrentValue();
    }


}
