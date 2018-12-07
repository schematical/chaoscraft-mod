package com.schematical.chaoscraft.entities;

/**
 * Created by user1a on 12/3/18.
 */


import com.google.common.collect.Sets;
import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class EntityRick extends EntityLiving {
    public Set<EntityCreature> organisims = Sets.<EntityCreature>newLinkedHashSet();

    public EntityRick(World worldIn) {
        this(worldIn, "Rick");
    }

    public EntityRick(World worldIn, String name) {
        super(worldIn);

        this.tasks.taskEntries.clear();

        this.tasks.addTask(2, new AISpawnOrganisim(this));
        this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        //this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    static class AISpawnOrganisim extends EntityAIBase
    {
        protected EntityRick rick;
        public AISpawnOrganisim(EntityRick _rick)
        {
            super();
            this.rick = _rick;
        }
        public boolean shouldExecute(){
            if(rick.organisims.size() >= ChaosCraft.config.maxBotCount) {
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
                rick.organisims.add(rabbit);
                world.spawnEntity(rabbit);
            }
        }



    }


    @Override
    public void onUpdate()
    {
        super.onUpdate();

    }
    public static class RickRenderer extends RenderLiving<EntityRick> {
        public RickRenderer(RenderManager rendermanagerIn) {
            super(rendermanagerIn, new ModelPlayer(.5f, false), 0.5f);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityRick entity) {
            return new ResourceLocation("chaoscraft:rick.png");//"minecraft:textures/entity/cow/cow.png");//ChaosCraft.MODID, "rick.png");
        }

    }
    public void onOrganisimDeath(EntityCreature creature){
        if(!world.isRemote) {
            if(organisims.contains(creature)) {
                organisims.remove(creature);
            }
        }
    }


}