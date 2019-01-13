package com.schematical.chaoscraft.entities;

/**
 * Created by user1a on 12/3/18.
 */


import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.*;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.CCWorldEventType;
import com.schematical.chaoscraft.fitness.EntityFitnessManager;
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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

public class EntityOrganism extends EntityLiving {
    public final double REACH_DISTANCE = 5.0D;
    private final long spawnTime;


    public EntityFitnessManager entityFitnessManager;
    protected Organism organism;
    protected NeuralNet nNet;
    protected float digSpeed = 1;
    protected ItemStackHandler itemHandler = new ItemStackHandler();
    protected BlockPos lastMinePos = BlockPos.ORIGIN.down();

    protected int miningTicks = 0;
    protected int selectedItemIndex = 0;
    protected float maxLifeSeconds = 10;

    public EntityOrganism(World worldIn) {
        this(worldIn, "EntityOrganism");
    }

    public EntityOrganism(World worldIn, String name) {
        super(worldIn);

        this.tasks.taskEntries.clear();


        this.tasks.addTask(1, new EntityAISwimming(this));

        this.entityFitnessManager = new EntityFitnessManager(this);

        spawnTime = world.getWorldTime();

     }
     public String getCCNamespace(){
        if(this.organism == null){
            return null;
        }
        return this.organism.getNamespace();
     }
     public NeuralNet getNNet(){
        return nNet;
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
    public void onUpdate(){
        long age = this.world.getWorldTime() - spawnTime;

        List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(1.0D, 0.0D, 1.0D));

        for (EntityItem item : items) {
            pickupItem(item);
        }

        super.onUpdate();
        if(
            this.organism == null ||
            age / 20 > maxLifeSeconds
        ){
            ChaosCraft.logger.info("Killing: " + this.getName());
            this.setDead();
            return;
        }
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

        boolean harvest = true;//state.getBlock().canHarvestBlock(world, pos, fakePlayer);

        ItemStack stack = getHeldItemMainhand();
        String tool = state.getBlock().getHarvestTool(state);
        if (stack.isEmpty() || tool == null)
        {
            if(state.getMaterial().isToolNotRequired()){
                hardness /= 30F;
            }else {
                hardness /= 100F;
            }
            harvest = false;
        }
        //ChaosCraft.logger.info(this.getName() + " Mining: " + state.getBlock().getLocalizedName() + " Tool:" + tool + " Held Stack: " + stack.getDisplayName() + "  Hardness: " + hardness + " - " + miningTicks + " - " + harvest + " => " + (hardness * miningTicks > 1.0f));
        //Check if block has been broken
        if (hardness * miningTicks > 1.0f) {
            //Broken
            miningTicks = 0;

            world.playEvent(2001, pos, Block.getStateId(state));





            boolean bool = world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
            if (bool) {
                state.getBlock().onBlockDestroyedByPlayer(world, pos, state);
            } else {
                harvest = false;
            }

            if (harvest) {
                //state.getBlock().harvestBlock(world, fakePlayer, pos, state, world.getTileEntity(pos), itemstack);
                state.getBlock().dropBlockAsItem(world, pos, state, 0);
                CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEventType.BLOCK_MINED);
                worldEvent.block = state.getBlock();
                entityFitnessManager.test(worldEvent);
            }
        }
    }

    private void resetMining() {
        miningTicks = 0;
        this.world.sendBlockBreakProgress(this.getEntityId(), lastMinePos, -1);
        this.lastMinePos.down(255);
    }

    private void pickupItem(EntityItem item) {
        if (item.cannotPickup()) return;

        ItemStack stack = item.getItem();

        for (int i = 0; i < this.itemHandler.getSlots() && !stack.isEmpty(); i++) {
            stack = this.itemHandler.insertItem(i, stack, false);

            //PacketHandler.INSTANCE.sendToAllTracking(new SyncHandsMessage(this.itemHandler.getStackInSlot(i), getEntityId(), i, selectedItemIndex), this);
        }

        this.setHeldItem(EnumHand.MAIN_HAND, this.itemHandler.getStackInSlot(this.selectedItemIndex));

        if (stack.isEmpty()) {
            item.setDead();
        }
        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEventType.ITEM_COLLECTED);
        worldEvent.item = stack.getItem();
        entityFitnessManager.test(worldEvent);
    }
    public void placeBlock(BlockPos blockPos, Block block){
        //ChaosCraft.logger.info(getName() + " Placing Block: " + block.getLocalizedName() + " - " + blockPos.toString());
        IBlockState blockState = this.nNet.entity.world.getBlockState(blockPos);

        //Block replaceBlock = blockState.getBlock();

        if(!blockState.getMaterial().isReplaceable()){
            return;
        }

        if(this.getEntityBoundingBox().grow(.5d).intersects(blockState.getBoundingBox(this.world, blockPos).grow(.5d))){
            return;//This will kill us
        }

        //Check if it is in their inventory
        ItemStack stack = null;
        int slot = -1;
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            ItemStack checkStack = this.itemHandler.getStackInSlot(i);
            Item item = checkStack.getItem();
            if(item instanceof ItemBlock){
                ItemBlock itemBlock = (ItemBlock) item;
                //itemBlock.placeBlockAt()
                if(itemBlock.getBlock() == block){
                    stack = checkStack;
                    slot = i;
                }
            }
            //PacketHandler.INSTANCE.sendToAllTracking(new SyncHandsMessage(this.itemHandler.getStackInSlot(i), getEntityId(), i, selectedItemIndex), this);
        }
        if (stack == null  ||stack.isEmpty()) {
            return;
        }


        this.itemHandler.extractItem(slot, 1, false);



        world.setBlockState(blockPos, block.getDefaultState());
        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEventType.BLOCK_PLACED);
        worldEvent.block = block;
        entityFitnessManager.test(worldEvent);

    }


}