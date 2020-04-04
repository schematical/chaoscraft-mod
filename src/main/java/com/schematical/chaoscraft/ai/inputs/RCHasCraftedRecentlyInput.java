package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Created by user1a on 12/8/18.
 */
public class RCHasCraftedRecentlyInput extends InputNeuron {



    @Override
    public float evaluate(){
        ScanManager scanManager =  ((OrgEntity)this.getEntity()).getClientOrgManager().getScanManager();
        ScanEntry scanEntry = scanManager.getFocusedScanEntry();


        Vec3d vec3d = this.getEntity().getPositionVec();
        Vec3d vec3d1 = scanEntry.getPosition();
        boolean canBeSeen = this.getEntity().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this.getEntity())).getType() == RayTraceResult.Type.MISS;

        if(canBeSeen) {
            setCurrentValue(1);
        }


        return getCurrentValue();
    }


}
