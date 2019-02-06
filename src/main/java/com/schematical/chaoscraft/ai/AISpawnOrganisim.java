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

import java.util.ArrayList;
import java.util.Iterator;
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
        int liveOrgCount = 0;
        for (EntityOrganism organism: ChaosCraft.organisims) {
            if(!organism.isDead){
                liveOrgCount += 1;
            }
        }
        if(
            ChaosCraft.ticksSinceLastSpawn < (20 * 5) ||
            liveOrgCount >= ChaosCraft.config.maxBotCount
        ) {
            return false;
        }
        return true;
    }
    public boolean shouldContinueExecuting() {

        return false;//super.shouldContinueExecuting();
    }
    public void startExecuting()
    {

        List<EntityOrganism> deadOrgs = new ArrayList<EntityOrganism>();
        Iterator<EntityOrganism> iterator = ChaosCraft.organisims.iterator();

        while(iterator.hasNext()){
            EntityOrganism organism = iterator.next();
            if(organism.isDead){
                if(
                    organism.getCCNamespace()  != null &&
                    organism.getSpawnHash() == ChaosCraft.spawnHash
                ) {
                    deadOrgs.add(organism);
                }
                //ChaosCraft.logger.info("Removing: " + organism.getName() + " - Org Size Before" + ChaosCraft.organisims.size());
                iterator.remove();

                //ChaosCraft.logger.info("Dead Orgs: " + deadOrgs.size() + " / " + ChaosCraft.organisims.size());
            }
        }
        /*int orgCountBefore = ChaosCraft.organisims.size();
        ChaosCraft.organisims.removeIf((org)-> org.isDead);
        if(deadOrgs.size() > 0){
            if((orgCountBefore - deadOrgs.size()) != ChaosCraft.organisims.size()){
                throw new ChaosNetException("orgCountBefore - deadOrgs.size() != ChaosCraft.organisims.size():  " + orgCountBefore + " - " + deadOrgs.size() + " != " + ChaosCraft.organisims.size());
            }
        }*/
        //ChaosCraft.logger.info("Queining Spawn");
        ChaosCraft.queueSpawn(deadOrgs);

    }



}

