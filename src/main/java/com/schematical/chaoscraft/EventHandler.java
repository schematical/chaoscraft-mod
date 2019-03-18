package com.schematical.chaoscraft;

import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void spawnEvent(EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof EntityMob) {
            EntityMob mob = (EntityMob) event.getEntity();

            if (!(mob instanceof EntityPigZombie)) {
                if (mob instanceof EntityEnderman) {

                } else {
                    mob.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(mob, EntityOrganism.class, true));
                }
            }
        }
    }
}
