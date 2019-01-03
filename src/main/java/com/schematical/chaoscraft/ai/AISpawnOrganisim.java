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
        TrainingRoomSessionNextResponse response = ChaosCraft.getNextOrgs(null);
        List<Organism> organismList = response.getOrganisms();
        World world = rick.getEntityWorld();
        if(!world.isRemote) {
            for(int i = 0; i < organismList.size(); i++) {
                Organism organism = organismList.get(i);
                GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest request = new GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest();
                request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
                request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
                request.setOrganism(organism.getNamespace());
                GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult result = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomOrganismsOrganismNnet(request);
                NNetRaw nNetRaw = result.getNNetRaw();

                EntityOrganism entityOrganism = new EntityOrganism(world, organism.getNamespace());
                entityOrganism.setCustomNameTag(organism.getName());
                BlockPos pos = rick.getPosition();
                int range = 15;
                Vec3d rndPos = null;
                int saftyCatch = 0;
                while(
                        rndPos == null &&
                        saftyCatch < 10
                ) {
                    saftyCatch ++;
                    rndPos = new Vec3d(
                    pos.getX() + Math.floor((Math.random() * range * 2) - range),
                    pos.getY() + Math.floor((Math.random() * range * 2) - range),
                    pos.getZ() + Math.floor((Math.random() * range * 2) - range)
                    );
                    entityOrganism.setPosition(
                        rndPos.x,
                        rndPos.y,
                        rndPos.z
                    );
                    if(!entityOrganism.getCanSpawnHere()){
                        rndPos = null;
                    }
                }

                entityOrganism.attachOrganism(organism);
                entityOrganism.attachNNetRaw(nNetRaw);
                ChaosCraft.organisims.add(entityOrganism);
                world.spawnEntity(entityOrganism);
            }
        }
    }



}

