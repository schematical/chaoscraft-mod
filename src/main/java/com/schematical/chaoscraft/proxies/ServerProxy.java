package com.schematical.chaoscraft.proxies;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.commands.CommandChaosCraftObserve;
import com.schematical.chaoscraft.network.CCIOutputNeuronMessage;
import com.schematical.chaoscraft.network.CCIOutputNeuronMessageHandler;
import com.schematical.chaoscraft.network.CCIServerActionMessage;
import com.schematical.chaoscraft.network.CCIServerActionMessageHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by user1a on 12/4/18.
 */

public class ServerProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ChaosCraft.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ChaosCraft.MODID);
        ChaosCraft.networkWrapper.registerMessage(CCIServerActionMessageHandler.class, CCIServerActionMessage.class, 0, Side.SERVER);
        ChaosCraft.networkWrapper.registerMessage(CCIOutputNeuronMessageHandler.class, CCIOutputNeuronMessage.class, 0, Side.SERVER);
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }


}