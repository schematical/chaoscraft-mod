package com.schematical.chaoscraft.entities;

/**
 * Created by user1a on 12/3/18.
 */


import com.google.common.collect.Sets;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.AIFindExistingOrganisims;
import com.schematical.chaoscraft.ai.AISpawnOrganisim;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.Set;

public class EntityRick extends EntityLiving {


    public EntityRick(World worldIn) {
        this(worldIn, "Rick");
    }

    public EntityRick(World worldIn, String name) {
        super(worldIn);

        this.tasks.taskEntries.clear();

        this.tasks.addTask(3, new AISpawnOrganisim(this));
        this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        //this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.tasks.addTask(2, new AIFindExistingOrganisims(this, EntityOrganism.class));
    }




    @Override
    public void onUpdate()
    {
        ChaosCraft.rickPos = this.getPosition();
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
            if(ChaosCraft.organisims.contains(creature)) {
                ChaosCraft.organisims.remove(creature);
            }
        }
    }


}