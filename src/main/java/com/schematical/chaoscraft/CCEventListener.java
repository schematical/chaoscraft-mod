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
        List<EntityOrganism> deadOrgs = new ArrayList<EntityOrganism>();
        Iterator<EntityOrganism> iterator = ChaosCraft.organisims.iterator();
        int index = 0;
        while(iterator.hasNext()){
            EntityOrganism organism = iterator.next();
            if(organism.isDead){
                deadOrgs.add(organism);
                ChaosCraft.logger.info("Removing: " + organism.getName() + " - Org Size Before" + ChaosCraft.organisims.size());
                //iterator.remove();

                ChaosCraft.logger.info("Dead Orgs: " + deadOrgs.size() + " / " + ChaosCraft.organisims.size());
            }
            index ++;
        }
        ChaosCraft.organisims.removeIf((org)-> org.isDead);


        //TODO: Add to reporting...


    }

}
