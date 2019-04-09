package com.schematical.chaoscraft.server;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.NNet;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 4/8/19.
 */
public class ChaosCraftServer {
    public ArrayList<ChaosCraftServerAction> pendingActions = new ArrayList<ChaosCraftServerAction>();
    protected World world;
    public ChaosCraftServer(World world) {
        this.world = world;
    }

    public void worldTick(){

        if(
            pendingActions != null &&
            pendingActions.size() > 0
        ){

            Iterator<ChaosCraftServerAction> iterator = pendingActions.iterator();
            List<Organism> orgsToSpawn = new ArrayList<Organism>();
            while (iterator.hasNext()) {
                ChaosCraftServerAction action = iterator.next();
                switch(action.action){
                    case(ChaosCraftServerAction.Action.Spawn):
                        Organism organism = new Organism();
                        organism.setGeneration(action.jsonObject.get("generation"));


                }
            }
            ChaosCraft.spawnOrgs(ChaosCraft.orgsToSpawn);
            ChaosCraft.orgsToSpawn.clear();
        }




    }
}
