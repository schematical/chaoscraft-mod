package com.schematical.chaoscraft.init;

/**
 * Created by user1a on 12/3/18.
 */

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityEvilRabbit;
import com.schematical.chaoscraft.entities.EntityRick;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.apache.http.client.entity.EntityBuilder;

@EventBusSubscriber(modid= ChaosCraft.MODID)
public class ModEntities {

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {

        EntityEntryBuilder entityBuilder = EntityEntryBuilder.create();
        EntityEntry e = entityBuilder.entity(EntityRick.class)
                .name("rick")
                .tracker(80, 1, false)
                .id(new ResourceLocation(ChaosCraft.MODID, "rick"), 1)
                .build();
        event.getRegistry().register(e);

        EntityEntry e2 = entityBuilder.entity(EntityEvilRabbit.class)
                .name("evilrabbit")
                .tracker(81, 1, false)
                .id(new ResourceLocation(ChaosCraft.MODID, "evilrabbit"), 2)
                .build();
        event.getRegistry().registerAll(e2);
    }


}