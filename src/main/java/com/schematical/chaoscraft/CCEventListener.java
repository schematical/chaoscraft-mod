package com.schematical.chaoscraft;

import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
        List<EntityOrganism> deadOrgs = new ArrayList<EntityOrganism>();
        Iterator<EntityOrganism> iterator = ChaosCraft.organisims.iterator();

        while(iterator.hasNext()){
            EntityOrganism organism = iterator.next();
            if(
                    organism.getOrganism() == null
                //organism.isDead
            ){
                organism.setDead();
                deadOrgs.add(organism);
                ChaosCraft.logger.info("Setting Dead: " + organism.getName() + " - Has no `Organism` record");
            }
        }


        if(
            ChaosCraft.orgsToSpawn != null &&
            ChaosCraft.orgsToSpawn.size() > 0
        ){
            ChaosCraft.spawnOrgs(ChaosCraft.orgsToSpawn);
            ChaosCraft.orgsToSpawn.clear();
        }
        //ChaosCraft.organisims.removeIf((org)-> org.isDead);


    }

}
