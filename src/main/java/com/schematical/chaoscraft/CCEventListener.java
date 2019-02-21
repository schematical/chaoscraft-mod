package com.schematical.chaoscraft;

import com.schematical.chaoscraft.commands.CommandChaosCraftObserve;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 1/6/19.
 */
public class CCEventListener {

    @SubscribeEvent
    public static void onWorldTickEvent(TickEvent.WorldTickEvent worldTickEvent){
        ChaosCraft.ticksSinceLastSpawn += 1;

        Iterator<EntityOrganism> iterator = ChaosCraft.organisims.iterator();

        while(iterator.hasNext()){
            EntityOrganism organism = iterator.next();
            if(
                organism.getOrganism() == null ||
                organism.getSpawnHash() != ChaosCraft.spawnHash
            ){
                organism.setDead();
                iterator.remove();
                //ChaosCraft.logger.info("Setting Dead: " + organism.getName() + " - Has no `Organism` record");
            }
        }


        if(
            ChaosCraft.orgsToSpawn != null &&
            ChaosCraft.orgsToSpawn.size() > 0
        ){
            ChaosCraft.spawnOrgs(ChaosCraft.orgsToSpawn);
            ChaosCraft.orgsToSpawn.clear();
        }
        if(ChaosCraft.consecutiveErrorCount > 5){
            throw new ChaosNetException("ChaosCraft.consecutiveErrorCount > 5");
        }
        //ChaosCraft.organisims.removeIf((org)-> org.isDead);
        if(ChaosCraft.organisims.size() > 0) {
            for (EntityPlayerMP observingPlayer : ChaosCraft.observingPlayers) {
                Entity entity = observingPlayer.getSpectatingEntity();
                if (
                        entity.equals(observingPlayer) ||
                                entity == null ||
                                entity.isDead
                        ) {
                    int index = (int) Math.floor(ChaosCraft.organisims.size() * Math.random());
                    EntityOrganism orgToObserve = ChaosCraft.organisims.get(index);
                    observingPlayer.setSpectatingEntity(orgToObserve);
                }
            }
        }


    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void render(RenderGameOverlayEvent.Post event){
        if(ChaosCraft.fontRenderer == null){
            ChaosCraft.fontRenderer = Minecraft.getMinecraft().fontRenderer;
        }
        if(ChaosCraft.topLeftMessage != null) {
            String[] parts = ChaosCraft.topLeftMessage.split("\n");
            int placement = 5;
            for (String part : parts) {
                ChaosCraft.fontRenderer.drawStringWithShadow(part, 5, placement, 0xFFFFFF);
                placement += 10;
            }
        }
    }



}
