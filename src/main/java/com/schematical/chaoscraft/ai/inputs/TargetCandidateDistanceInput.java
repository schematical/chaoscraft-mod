package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.TargetHelper;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetCandidateDistanceInput extends InputNeuron {

    private static final float  YAW_DEGREES = 360f;

    @Override
    public float evaluate(){
        ScanManager scanManager =  ((OrgEntity)this.getEntity()).getClientOrgManager().getScanManager();
        ScanManager.ScanEntry scanEntry = scanManager.getFocusedScanEntry();
        Double dist = TargetHelper.getDistDelta(scanEntry.getPosition(), this.getEntity().getPositionVec());
        if(dist != null) {
            setCurrentValue( dist.floatValue() / scanManager.getRange());
        }


        return getCurrentValue();
    }


}
