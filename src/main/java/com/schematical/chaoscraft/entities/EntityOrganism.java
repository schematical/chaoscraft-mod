package com.schematical.chaoscraft.entities;

/**
 * Created by user1a on 12/3/18.
 */


import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.*;
import com.schematical.chaosnet.model.NNetRaw;
import com.schematical.chaosnet.model.Organism;
import com.schematical.chaosnet.model.NNet;
import jdk.nashorn.internal.parser.JSONParser;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

public class EntityOrganism extends EntityLiving {
    public final double REACH_DISTANCE = 5.0D;
    protected Organism organism;
    protected NeuralNet nNet;
    protected float digSpeed = 1;
    protected BlockPos lastMinePos = BlockPos.ORIGIN.down();

    protected int miningTicks = 0;

    public EntityOrganism(World worldIn) {
        this(worldIn, "EntityOrganism");
    }

    public EntityOrganism(World worldIn, String name) {
        super(worldIn);

        this.tasks.taskEntries.clear();


        this.tasks.addTask(1, new EntityAISwimming(this));
        ChaosCraft.organisims.add(this);

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
    public void onLivingUpdate()
    {
        if (!this.world.isRemote)
        {
            //Tick neural net
            if(this.nNet != null) {
                List<OutputNeuron> outputs = this.nNet.evaluate();
                Iterator<OutputNeuron> iterator = outputs.iterator();
                while (iterator.hasNext()) {
                    OutputNeuron outputNeuron = iterator.next();
                    outputNeuron.execute();

                }
            }
        }

        super.onLivingUpdate();

    }
    public void jump(){
        if (this.onGround)
        {
            super.jump();
        }
         /*if(!this.isJumping) {
             //super.jump();
             super.getJumpHelper().setJumping();
         }*/
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






    public RayTraceResult rayTraceBlocks(double blockReachDistance) {
        Vec3d vec3d = this.getPositionEyes(1);
        Vec3d vec3d1 = this.getLook(1);
        Vec3d vec3d2 = vec3d.add(
            new Vec3d(
                vec3d1.x * blockReachDistance,
                vec3d1.y * blockReachDistance,
                vec3d1.z * blockReachDistance
            )
        );
        return this.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
    }



    public void dig(BlockPos pos) {

        if (!this.world.getWorldBorder().contains(pos) || pos.distanceSq(getPosition()) > REACH_DISTANCE * REACH_DISTANCE) {
            resetMining();
            return;
        }

        if (!lastMinePos.equals(pos)) {
            resetMining();
        }

        lastMinePos = pos;

        miningTicks++;

        IBlockState state = world.getBlockState(pos);
        float hardness = state.getBlockHardness(world, pos);

        this.world.sendBlockBreakProgress(this.getEntityId(), pos, (int) (hardness * miningTicks * 10.0F) - 1);

        //Check if block has been broken
        if (hardness * miningTicks > 1.0f) {
            //Broken
            miningTicks = 0;

            world.playEvent(2001, pos, Block.getStateId(state));

            ItemStack itemstack = this.getActiveItemStack();



            boolean harvest = true;//state.getBlock().canHarvestBlock(world, pos, fakePlayer);

            boolean bool = world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
            if (bool) {
                state.getBlock().onBlockDestroyedByPlayer(world, pos, state);
            } else {
                harvest = false;
            }

            if (harvest) {
                //state.getBlock().harvestBlock(world, fakePlayer, pos, state, world.getTileEntity(pos), itemstack);
                state.getBlock().dropBlockAsItem(world, pos, state, 0);
            }
        }
    }

    private void resetMining() {
        miningTicks = 0;
        this.world.sendBlockBreakProgress(this.getEntityId(), lastMinePos, -1);
        this.lastMinePos.down(255);
    }


}