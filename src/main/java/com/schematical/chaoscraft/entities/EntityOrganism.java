package com.schematical.chaoscraft.entities;

/**
 * Created by user1a on 12/3/18.
 */


import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.AIFindExistingOrganisims;
import com.schematical.chaoscraft.ai.AISpawnOrganisim;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaosnet.model.NNetRaw;
import com.schematical.chaosnet.model.Organism;
import com.schematical.chaosnet.model.NNet;
import jdk.nashorn.internal.parser.JSONParser;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;

public class EntityOrganism extends EntityCreature {
    protected Organism organism;
    protected NeuralNet nNet;

    public EntityOrganism(World worldIn) {
        this(worldIn, "EntityOrganism");
    }

    public EntityOrganism(World worldIn, String name) {
        super(worldIn);

        this.tasks.taskEntries.clear();


        this.tasks.addTask(1, new EntityAISwimming(this));

     }
     public void attachNNetRaw(NNetRaw nNetRaw){
        String nNetString = nNetRaw.getNNetRaw();
         JSONObject obj = null;


         try {

             org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
             obj = (JSONObject) parser.parse(
                     nNetString
             );
             nNet = new NeuralNet();
             nNet.parseData(obj);
             nNet.attachEntity(this);
             //this.tasks.addTask(2, new AIFindExistingOrganisims(this, EntityOrganism.class));

         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     public void attachOrganism(Organism _organism){
         organism = _organism;
     }




    @Override
    public void onUpdate()
    {
        if (!this.world.isRemote)
        {
            //Tick neural net
            this.nNet.evaluate();

        }
        super.onUpdate();

    }
    public static class EntityOrganismRenderer extends RenderLiving<EntityOrganism> {
        public EntityOrganismRenderer(RenderManager rendermanagerIn) {
            super(rendermanagerIn, new ModelPlayer(.5f, false), 0.5f);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityOrganism entity) {
            return new ResourceLocation("chaoscraft:morty.png");
        }

    }
    public void onOrganisimDeath(EntityCreature creature){
        if(!world.isRemote) {
            if(ChaosCraft.organisims.contains(creature)) {
                ChaosCraft.organisims.remove(creature);
            }
        }
    }


}