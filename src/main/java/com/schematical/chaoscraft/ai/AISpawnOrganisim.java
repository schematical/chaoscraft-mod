package com.schematical.chaoscraft.ai;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityEvilRabbit;
import com.schematical.chaoscraft.entities.EntityRick;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

        World world = rick.getEntityWorld();
        if(!world.isRemote) {
            EntityEvilRabbit rabbit = new EntityEvilRabbit(world, "ChaosCraft Rabbit", rick);
            rabbit.setCustomNameTag("ChaosCraft Rabbit");
            BlockPos pos = rick.getPosition();
            rabbit.setPosition(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            ChaosCraft.organisims.add(rabbit);
            world.spawnEntity(rabbit);
        }
    }



}

