package com.schematical.chaoscraft.proxies;

import com.schematical.chaoscraft.commands.CommandChaosCraftAuth;
import com.schematical.chaoscraft.commands.CommandChaosCraftSessionStart;
import com.schematical.chaoscraft.commands.CommandChaosCraftSetConfig;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.entities.EntityRick;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by user1a on 12/4/18.
 */
public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftAuth());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftSetConfig());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftSessionStart());

        RenderingRegistry.registerEntityRenderingHandler(
                EntityRick.class,
                manager -> new EntityRick.RickRenderer(manager)
        );
        RenderingRegistry.registerEntityRenderingHandler(
                EntityOrganism.class,
                manager -> new EntityOrganism.EntityOrganismRenderer(manager)
        );

    }
    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

}