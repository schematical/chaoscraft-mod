package com.schematical.chaoscraft.client;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.network.CCIServerActionMessage;
import com.schematical.chaoscraft.server.ChaosCraftServerAction;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.GameType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 4/9/19.
 */
public class ChaosCraftClient {
    public Thread thread;
    public String topLeftMessage;
    public List<EntityPlayerMP> observingPlayers = new ArrayList<EntityPlayerMP>();
    public EntityOrganism adam = null;
    public int consecutiveErrorCount = 0;
    public List<Organism> orgsToSpawn = new ArrayList<Organism>();
    public HashMap<String, Organism> orgsQueuedToSpawn = new HashMap<String, Organism>();
    public List<EntityOrganism> orgsToReport = new ArrayList<EntityOrganism>();
    public HashMap<String, EntityOrganism> myOrganisims = new HashMap<String, EntityOrganism>();
    public void worldTick() {
        if(orgsToSpawn.size() > 0){
            Iterator<Organism> iterator = orgsToSpawn.iterator();

            while (iterator.hasNext()) {
                Organism organism = iterator.next();
                if(!orgsQueuedToSpawn.containsKey(organism.getNamespace())) {
                    CCIServerActionMessage message = new CCIServerActionMessage();
                    message.setAction(ChaosCraftServerAction.Action.Spawn);
                    message.setOrganism(organism);
                    orgsQueuedToSpawn.put(organism.getNamespace(), organism);
                    ChaosCraft.networkWrapper.sendToServer(message);
                }
            }

        }


        Iterator<EntityOrganism> iterator = myOrganisims.values().iterator();
        int liveOrgCount = 0;
        while (iterator.hasNext()) {
            EntityOrganism organism = iterator.next();
            organism.manualUpdateCheck();
            if (
                organism.getOrganism() == null ||
                organism.getSpawnHash() != ChaosCraft.spawnHash
            ) {
                organism.setDead();
                iterator.remove();
                //ChaosCraft.logger.info("Setting Dead: " + organism.getName() + " - Has no `Organism` record");
            }
            if (!organism.isDead) {
                liveOrgCount += 1;
            }
        }


        if (
            ChaosCraft.ticksSinceLastSpawn < (20 * 20) ||
            liveOrgCount >= ChaosCraft.config.maxBotCount
        ) {

            List<EntityOrganism> deadOrgs = new ArrayList<EntityOrganism>();
            iterator = myOrganisims.values().iterator();

            while (iterator.hasNext()) {
                EntityOrganism organism = iterator.next();
                if (organism.isDead) {
                    if (
                        organism.getCCNamespace() != null &&
                        organism.getSpawnHash() == ChaosCraft.spawnHash &&
                        !organism.getDebug()//Dont report Adam-0
                    ) {
                        deadOrgs.add(organism);
                    }
                    //ChaosCraft.logger.info("Removing: " + organism.getName() + " - Org Size Before" + ChaosCraft.organisims.size());
                    iterator.remove();

                    //ChaosCraft.logger.info("Dead Orgs: " + deadOrgs.size() + " / " + ChaosCraft.organisims.size());
                }
            }

            reportOrgs(deadOrgs);
        }

        if(consecutiveErrorCount > 5){
            throw new ChaosNetException("ChaosCraft.consecutiveErrorCount > 5");
        }

        if(myOrganisims.size() > 0) {
            for (EntityPlayerMP observingPlayer : observingPlayers) {
                Entity entity = observingPlayer.getSpectatingEntity();

                if (
                    entity.equals(observingPlayer) ||
                    entity == null ||
                    entity.isDead
                ) {
                    if(
                        entity != null &&
                        entity instanceof EntityOrganism
                    ){
                        ((EntityOrganism) entity).setObserving(null);
                    }
                    int index = (int) Math.floor(myOrganisims.size() * Math.random());
                    EntityOrganism orgToObserve = myOrganisims.get(index);
                    orgToObserve.setObserving(observingPlayer);
                    observingPlayer.setSpectatingEntity(orgToObserve);
                }
            }
        }
    }
    public void reportOrgs(List<EntityOrganism> _orgsToReport){
        _orgsToReport.forEach((EntityOrganism organism)->{

            ChaosCraft.client.orgsToReport.add(organism);
        });
        if(thread != null){
            return;
        }
        ChaosCraft.ticksSinceLastSpawn = 0;

        thread = new Thread(new ChaosClientThread(), "ChaosClientThread");
        thread.start();
    }


    public void toggleObservingPlayer(EntityPlayerMP player) {
        if(observingPlayers.contains(player)){
            observingPlayers.remove(player);
            player.setGameType(GameType.CREATIVE);
        }else {
            player.setGameType(GameType.SPECTATOR);
            observingPlayers.add(player);
        }
    }
}
