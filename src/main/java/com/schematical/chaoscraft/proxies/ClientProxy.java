package com.schematical.chaoscraft.proxies;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.commands.*;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.entities.EntityRick;
import com.schematical.chaoscraft.gui.CCKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user1a on 12/4/18.
 */
public class ClientProxy implements IProxy {
// declare an array of key bindings
    public static List<KeyBinding> keyBindings = new ArrayList<KeyBinding>();
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftAuth());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftSetConfig());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftSessionStart());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftRefresh());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftTP());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftList());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftAdam());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftRepair());
        ClientCommandHandler.instance.registerCommand(new CommandChaosCraftHardReset());
        RenderingRegistry.registerEntityRenderingHandler(
                EntityRick.class,
                manager -> new EntityRick.RickRenderer(manager)
        );
        RenderingRegistry.registerEntityRenderingHandler(
                EntityOrganism.class,
                manager -> new EntityOrganism.EntityOrganismRenderer(manager)
        );


// instantiate the key bindings
        //keyBindings[1] = new KeyBinding("key.structure.desc", Keyboard.KEY_P, "key.magicbeans.category");
        keyBindings.add(new KeyBinding(CCKeyBinding.SHOW_ORG_LIST, Keyboard.KEY_H, "key.chaoscraft"));
        keyBindings.add(new KeyBinding(CCKeyBinding.OBSERVER_MODE, Keyboard.KEY_O, "key.chaoscraft"));

// register all the key bindings
        for (int i = 0; i < keyBindings.size(); ++i)
        {
            ClientRegistry.registerKeyBinding(keyBindings.get(i));
        }
        ChaosCraft.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ChaosCraft.MODID);
        ChaosCraft.networkWrapper.registerMessage(CCIMessageHandeler.class, CCIMessage.class, 0, Side.CLIENT);
    }
    @Override
    public void init(FMLInitializationEvent event) {
        ChaosCraft.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }


}