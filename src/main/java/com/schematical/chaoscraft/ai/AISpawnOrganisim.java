package com.schematical.chaoscraft.ai;

import com.amazonaws.protocol.json.internal.JsonProtocolMarshaller;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityEvilRabbit;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.entities.EntityRick;
import com.schematical.chaosnet.model.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by user1a on 12/7/18.
 */
public class AISpawnOrganisim extends EntityAIBase
{
    protected EntityRick rick;
    public AISpawnOrganisim(EntityRick _rick)
    {
        super();
        this.rick = _rick;
    }
    public boolean shouldExecute(){
        for (EntityOrganism organism: ChaosCraft.organisims) {
            if(organism.isDead){

            }
        }
        if(ChaosCraft.organisims.size() >= ChaosCraft.config.maxBotCount) {
            return false;
        }
        return true;
    }
    public boolean shouldContinueExecuting() {

        return false;//super.shouldContinueExecuting();
    }
    public void startExecuting()
    {
       ChaosCraft.spawnOrgs();
    }



}

