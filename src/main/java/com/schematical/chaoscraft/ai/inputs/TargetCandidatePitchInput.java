package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.TargetHelper;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetCandidatePitchInput extends InputNeuron {

    private static final float  PITCH_DEGREES = 180f;

    @Override
    public float evaluate(){
        ScanManager scanManager =  ((OrgEntity)this.getEntity()).getClientOrgManager().getScanManager();
        ScanEntry scanEntry = scanManager.getFocusedScanEntry();
        if(scanEntry == null){
            return getCurrentValue();
        }
        Double degrees = TargetHelper.getPitchDelta(
                scanEntry.getPosition(),
                this.getEntity().getPositionVec(),
                this.getEntity().getLookVec()
        );
        if(degrees != null) {
            setCurrentValue( degrees.floatValue() / PITCH_DEGREES);
        }


        return getCurrentValue();
    }


}
