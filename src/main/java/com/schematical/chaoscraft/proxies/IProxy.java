package com.schematical.chaoscraft.proxies;

/**
 * Created by user1a on 12/4/18.
 */

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

  @EventHandler
  public void preInit(FMLPreInitializationEvent event);

  @EventHandler
  public void init(FMLInitializationEvent event);

  @EventHandler
  public void postInit(FMLPostInitializationEvent event);

}