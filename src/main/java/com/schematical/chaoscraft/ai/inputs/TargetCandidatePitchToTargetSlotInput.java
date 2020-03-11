package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.TargetHelper;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetCandidatePitchToTargetSlotInput extends BaseTargetInputNeuron {
    private static final float  PITCH_DEGREES = 180f;


    @Override
    public float evaluate(){
        ScanManager scanManager =  ((OrgEntity)this.getEntity()).getClientOrgManager().getScanManager();
        ScanManager.ScanEntry scanEntry = scanManager.getFocusedScanEntry();
        if(!targetSlot.hasTarget()){
            return getCurrentValue();
        }
        if(scanEntry == null){
            return getCurrentValue();
        }
        Double degrees = TargetHelper.getPitchDelta(
                scanEntry.getPosition(),
                this.getEntity().getPositionVec(),
                this.getEntity().getLookVec()
        );
        Double degrees2 = TargetHelper.getPitchDelta(
                getTarget().getTargetPositionCenter(),
                this.getEntity().getPositionVec(),
                this.getEntity().getLookVec()
        );
        if(
            degrees != null &&
            degrees2 != null
        ) {
            setCurrentValue((float) (degrees - degrees2) / PITCH_DEGREES);
        }


        return getCurrentValue();
    }


}
