package com.schematical.chaoscraft.ai.inputs.targetnet;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCAttributeId;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.Eye;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.tickables.OrgPositionManager;
import com.schematical.chaoscraft.util.TargetHelper;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by user1a on 12/8/18.
 */
public class TargetCandidatePitchInput extends InputNeuron {

    private static final float  PITCH_DEGREES = 180f;

    @Override
    public float evaluate(){
        ScanManager scanManager =  ((OrgEntity)this.getEntity()).getClientOrgManager().getScanManager();
        ScanManager.ScanEntry scanEntry = scanManager.getFocusedScanEntry();
        Double degrees = TargetHelper.getPitchDelta(scanEntry.getPosition(), this.getEntity().getPositionVec(), this.getEntity().getLookVec());
        if(degrees != null) {
            setCurrentValue( degrees.floatValue() / PITCH_DEGREES);
        }


        return getCurrentValue();
    }


}
