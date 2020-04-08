package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Created by user1a on 12/8/18.
 */
public class RCHasCraftedRecentlyInput extends InputNeuron {
    public static final int MAX = 5;



    @Override
    public float evaluate(){
        ClientOrgManager clientOrgManager = ((OrgEntity)this.getEntity()).getClientOrgManager();
        ScanManager scanManager = clientOrgManager.getScanManager();
        ScanEntry scanEntry = scanManager.getScanItemInstance().getFocusedScanEntry();

        ArrayList<ActionBase> actionBases = clientOrgManager.getActionBuffer().getRecentActions();
        int start = actionBases.size() - MAX;
        if(start < 0){
            start = 0;
        }
        for (int i = start; i < actionBases.size(); i++) {
            ChaosTargetItem chaosTargetItem = actionBases.get(i).getTargetItem();
            if(chaosTargetItem.getRecipe() != null){
                if(chaosTargetItem.getRecipe().equals(scanEntry.recipe)){
                    setCurrentValue(i/ MAX);
                }
            }
        }
        return getCurrentValue();

    }



}
