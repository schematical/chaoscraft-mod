package com.schematical.chaoscraft.server;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user1a on 4/8/19.
 */
public class ChaosCraftServer {
    public ArrayList<ChaosCraftServerAction> pendingActions = new ArrayList<ChaosCraftServerAction>();
    public static List<EntityOrganism> organisims = new ArrayList<EntityOrganism>();
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
                if(action.action.equals(ChaosCraftServerAction.Action.Spawn)){
                    orgsToSpawn.add(action.message.getOrganism());
                }
            }
            if(orgsToSpawn.size() > 0) {
               this.spawnOrgs(orgsToSpawn);
            }
            //ChaosCraft.orgsToSpawn.clear();
        }




    }
    public List<EntityOrganism> spawnOrgs() {
        return spawnOrgs(null);
    }
    public List<EntityOrganism> spawnOrgs(List<Organism> organismList){
        if(world.isRemote) {
            throw new ChaosNetException("Can't call this client side. Something is really wrong");
        }
        List<EntityOrganism> spawnedEntityOrganisms = new ArrayList<EntityOrganism>();




        if(organismList != null && ChaosCraft.lastResponse != null) {
            for (int i = 0; i < organismList.size(); i++) {
                Organism organism = organismList.get(i);



                boolean orgIsAlreadyAlive = false;
                for (int ii = 0; ii < organisims.size(); ii++) {
                    EntityOrganism entityOrganism = organisims.get(ii);
                    if (entityOrganism.getCCNamespace().equals(organism.getNamespace())) {
                        orgIsAlreadyAlive = true;
                    }
                }


                if (!orgIsAlreadyAlive) {
                    NNetRaw nNetRaw = null;
                    String nNetRawStr = organism.getNNetRaw();
                    if (nNetRawStr != null) {
                        nNetRaw = new NNetRaw();
                        nNetRaw.setNNetRaw(nNetRawStr);
                    } else {
                        GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest request = new GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetRequest();
                        request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
                        request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
                        request.setOrganism(organism.getNamespace());
                        GetUsernameTrainingroomsTrainingroomOrganismsOrganismNnetResult result = ChaosCraft.sdk.getUsernameTrainingroomsTrainingroomOrganismsOrganismNnet(request);
                        nNetRaw = result.getNNetRaw();
                    }

                    EntityOrganism entityOrganism = new EntityOrganism(world, organism.getNamespace());
                    entityOrganism.attachOrganism(organism);
                    entityOrganism.attachNNetRaw(nNetRaw);
                    entityOrganism.observableAttributeManager = new CCObservableAttributeManager(organism);
                    if(!organism.getName().equals("adam")){
                        TaxonomicRank taxonomicRank = null;
                        if (ChaosCraft.lastResponse == null) {
                            throw new ChaosNetException("`ChaosCraft.lastResponse` is `null`");
                        }
                        for (TaxonomicRank _taxonomicRank : ChaosCraft.lastResponse.getSpecies()) {
                            //ChaosCraft.logger.info(_taxonomicRank.getNamespace() + " == " + organism.getSpeciesNamespace());
                            if (_taxonomicRank.getNamespace().equals(organism.getSpeciesNamespace())) {
                                taxonomicRank = _taxonomicRank;
                            }
                        }
                        if (taxonomicRank == null) {
                            throw new ChaosNetException("Could not find species: " + organism.getSpeciesNamespace());
                        }
                        String observedAttributesRaw = taxonomicRank.getObservedAttributesRaw();
                        if (
                                observedAttributesRaw == null ||
                                        observedAttributesRaw.length() == 0
                                ) {

                        } else {
                            try {
                                JSONParser parser = new JSONParser();
                                JSONObject jsonObject = (JSONObject) parser.parse(
                                        taxonomicRank.getObservedAttributesRaw()
                                );
                                entityOrganism.observableAttributeManager.parseData(jsonObject);
                            } catch (Exception exeception) {
                                throw new ChaosNetException("Invalid `species.observedAttributes` JSON Found: " + taxonomicRank.getObservedAttributesRaw() + "\n\n-- " + exeception.getMessage());
                            }
                        }
                    }

                    entityOrganism.setCustomNameTag(organism.getName() + " - " + organism.getGeneration());

                    BlockPos pos = world.getSpawnPoint();
                    if(organism.getName().equals("adam")) {

                        entityOrganism.setPosition(
                                pos.getX(),
                                pos.getY() + 2,
                                pos.getZ()
                        );

                    }else{
                        placeEntity(pos, entityOrganism);


                    }

                    entityOrganism.setSpawnHash(ChaosCraft.spawnHash);
                    organisims.add(entityOrganism);
                    spawnedEntityOrganisms.add(entityOrganism);
                    world.spawnEntity(entityOrganism);
                }
            }
        }



        return spawnedEntityOrganisms;
    }
    public static void placeEntity(BlockPos pos, EntityOrganism entityOrganism){
        int range = 30;
        int minRange = 5;
        int yRange = 20;
        Vec3d rndPos = null;
        int saftyCatch = 0;
        int saftyMax = 10;
        while (
                rndPos == null &&
                        saftyCatch < saftyMax
                ) {
            saftyCatch++;
            double xPos = Math.floor((Math.random() * (range) * 2) - range);
            if (xPos > 0) {
                xPos += minRange;
            } else {
                xPos -= minRange;
            }
            double zPos = Math.floor((Math.random() * (range) * 2) - range);
            if (zPos > 0) {
                zPos += minRange;
            } else {
                zPos -= minRange;
            }
            rndPos = new Vec3d(
                    pos.getX() + xPos,
                    pos.getY() + Math.floor((Math.random() * yRange)),
                    pos.getZ() + zPos
            );
            if (!entityOrganism.getCanSpawnHere()) {
                rndPos = null;
            } else {
                BlockPos blockPos = new BlockPos(rndPos);

                IBlockState state = entityOrganism.world.getBlockState(blockPos);
                Material material = state.getMaterial();
                if (
                        !(
                                material == Material.WATER ||
                                        material == Material.AIR
                        )
                        ) {
                    rndPos = null;
                }else{
                    int saftyCatch2 = 0;
                    Vec3d lastPos = null;
                    boolean foundBottom = false;
                    int saftyMax2 = 40;
                    while(
                            saftyCatch2 < saftyMax2 &&
                                    !foundBottom
                            ){
                        saftyCatch2 += 1;
                        lastPos = rndPos;
                        rndPos = new Vec3d(lastPos.x, lastPos.y - 1, lastPos.z);
                        blockPos = new BlockPos(rndPos);

                        state = entityOrganism.world.getBlockState(blockPos);
                        material = state.getMaterial();
                        if (material != Material.AIR){
                            rndPos = lastPos;
                            foundBottom = true;
                        }
                    }
                    if(saftyCatch2 >= saftyMax2){
                        rndPos = null;
                        //throw new ChaosNetException("Could not find good spawn pos after " + saftyCatch2 + " attempts");
                    }
                }
            }
            if(rndPos != null){
                entityOrganism.setPositionAndRotation(
                        rndPos.x,
                        rndPos.y,
                        rndPos.z,
                        (float) Math.random() * 360,
                        (float)Math.random() * 360
                );

                if(entityOrganism.isEntityInsideOpaqueBlock()){
                    rndPos = null;
                }
            }


        }
        if(saftyCatch == saftyMax){
            throw new ChaosNetException("Could not find good spawn pos after " + saftyCatch + " attempts");
        }
        if(rndPos == null){
            throw new ChaosNetException("This should be impossible");
        }

    }
    public static EntityOrganism getEntityOrganismByName(String name){
        Iterator<EntityOrganism> iterator = organisims.iterator();
        while(iterator.hasNext()){
            EntityOrganism org = iterator.next();
            if(org.getName().equals(name)){
                return org;
            }
        }
        return null;
    }
    public static EntityOrganism getEntityOrganismById(String id){
        for(EntityOrganism org : organisims){
            if(org.getCCNamespace() == id){
                return org;
            }
        }
        return null;
    }
}
