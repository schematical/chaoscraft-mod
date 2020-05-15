package com.schematical.chaoscraft.ai.inputs.actioncandidate;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.action.ActionBuffer;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import net.minecraft.util.math.Vec3i;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class ACIsTypeInput extends InputNeuron {


    private String actionType;
    @Override
    public float evaluate(){
        ClientOrgManager clientOrgManager = ((OrgEntity)this.getEntity()).getClientOrgManager();
        ScanManager scanManager = clientOrgManager.getScanManager();
        ActionTargetSlot actionTargetSlot = scanManager.getFocusedActionTargetSlot();
        String targetActionType = actionTargetSlot.getActionBaseClass().getSimpleName();
        if(targetActionType.equals(actionType)){
            setCurrentValue(1);
        }

        return getCurrentValue();
    }
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

        actionType = jsonObject.get("actionType").toString();


    }


}
