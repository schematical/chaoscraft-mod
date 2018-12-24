package com.schematical.chaoscraft.entities;

/**
 * Created by user1a on 12/3/18.
 */


import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Set;

public class EntityEvilRabbit extends EntityRabbit {
    public Organism organism;
    public NeuralNet neuralNet;

    public EntityEvilRabbit(World worldIn) {
        this(worldIn, "Evil Rabbit");

    }

    public EntityEvilRabbit(World worldIn, String name) {
        super(worldIn);

        this.tasks.taskEntries.clear();
        Set<EntityAITasks.EntityAITaskEntry> taskEntries = this.tasks.taskEntries;
        /*for(int i = 0; i < taskEntries.size(); i ++){
            .

            this.tasks.removeTask(taskEntries.e.action);
        }));*/
        this.tasks.addTask(2, new AIRabbitAttack(this));
        this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(1, new EntityRabbit.AIPanic(this, 2.2D));
        this.tasks.addTask(2, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.CARROT, false));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.GOLDEN_CARROT, false));
        //this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Item.getItemFromBlock(Blocks.YELLOW_FLOWER), false));
        //this.tasks.addTask(4, new EntityRabbit.AIAvoidEntity(this, EntityPlayer.class, 8.0F, 2.2D, 2.2D));
        //this.tasks.addTask(4, new EntityRabbit.AIAvoidEntity(this, EntityWolf.class, 10.0F, 2.2D, 2.2D));
        //this.tasks.addTask(4, new EntityRabbit.AIAvoidEntity(this, EntityMob.class, 4.0F, 2.2D, 2.2D));
       // this.tasks.addTask(5, new EntityRabbit.AIRaidFarm(this));
        this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    static class AIRabbitAttack extends EntityAIAttackMelee
    {
        public AIRabbitAttack(EntityEvilRabbit rabbit)
        {
            super(rabbit, 1.0D, true);
        }

        public boolean shouldContinueExecuting() {
            if(this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget((EntityLivingBase)null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget)
        {
            return (double)(4.0F + attackTarget.width);
        }
    }
    /*
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10000000149011612D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.LUCK);
        this.getAttributeMap().registerAttribute(EntityPlayer.REACH_DISTANCE);
    } */

    @Override
    public void onUpdate()
    {
        super.onUpdate();

    }
    @Override
    public void onDeathUpdate(){
        super.onDeathUpdate();
        if(!world.isRemote) {
            ChaosCraft.rick.onOrganisimDeath(this);
        }
    }


}