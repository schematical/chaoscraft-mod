package com.schematical.chaoscraft;


import com.amazonaws.opensdk.config.ConnectionConfiguration;
import com.amazonaws.opensdk.config.TimeoutConfiguration;

import com.schematical.chaoscraft.proxies.IProxy;

import com.schematical.chaosnet.ChaosNet;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;



@Mod(modid = ChaosCraft.MODID, name = ChaosCraft.NAME, version = ChaosCraft.VERSION)
public class ChaosCraft
{
    public static final String MODID = "chaoscraft";
    public static final String NAME = "ChaosCraft";
    public static final String VERSION = "1.0";
    public static ChaosNet sdk;

    @SidedProxy(modId=ChaosCraft.MODID,clientSide="com.schematical.chaoscraft.proxies.ClientProxy", serverSide="com.schematical.chaoscraft.proxies.ServerProxy")
    public static IProxy proxy;
    public static Logger logger;
    public static ChaosCraftConfig config;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        config = new ChaosCraftConfig();
        config.load();
        logger = event.getModLog();
        sdk =  ChaosNet.builder()
                .connectionConfiguration(
                    new ConnectionConfiguration()
                        .maxConnections(100)
                        .connectionMaxIdleMillis(1000)
                    )
                .   timeoutConfiguration(
                        new TimeoutConfiguration()
                                .httpRequestTimeout(10000)
                                .totalExecutionTimeout(20000)
                                .socketTimeout(10000)
                    )
                    .build();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {


        // some example code
        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());


    }



}