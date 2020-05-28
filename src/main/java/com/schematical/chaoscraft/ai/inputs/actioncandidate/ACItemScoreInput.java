package com.schematical.chaoscraft.ai.inputs.actioncandidate;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class ACItemScoreInput extends InputNeuron {


    //private String actionType;
    @Override
    public float evaluate(){
        ClientOrgManager clientOrgManager = ((OrgEntity)this.getEntity()).getClientOrgManager();
        ScanManager scanManager = clientOrgManager.getScanManager();

        setCurrentValue(scanManager.getFocusedActionItemScore());

        return getCurrentValue();
    }
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);

        //actionType = jsonObject.get("actionType").toString();


    }


}
