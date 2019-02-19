package com.schematical.chaoscraft.entities;

/**
 * Created by user1a on 12/3/18.
 */


import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.*;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.CCWorldEventType;
import com.schematical.chaoscraft.fitness.EntityFitnessManager;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.NNetRaw;
import com.schematical.chaosnet.model.Organism;
import com.schematical.chaosnet.model.NNet;
import jdk.nashorn.internal.parser.JSONParser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
    protected float maxLifeSeconds = 30;

    public boolean hasAttemptedReport = false;
    public boolean hasFinishedReport = false;
    protected int spawnHash;

    protected double desiredPitch;
    protected double desiredYaw;
    protected boolean debug = false;
    public boolean refreshRender = false;
    protected String skin = "chaoscraft:morty.png";

    public EntityOrganism(World worldIn) {
        this(worldIn, "EntityOrganism");
    }

    public EntityOrganism(World worldIn, String name) {
        super(worldIn);

        this.tasks.taskEntries.clear();


        this.tasks.addTask(1, new EntityAISwimming(this));

        this.entityFitnessManager = new EntityFitnessManager(this);

        spawnTime = world.getWorldTime();
        this.refreshRender = true;
        this.itemHandler.setSize(8);
     }
     public String getCCNamespace(){
        if(this.organism == null){
            return null;
        }
        return this.organism.getNamespace();
     }
     public ItemStackHandler getItemStack(){
        return this.itemHandler;
     }
     public void setDesiredPitch(double _desiredPitch){
         this.desiredPitch = _desiredPitch;
     }
     public void setDesiredYaw(double _desiredYaw){
        this.desiredYaw = _desiredYaw;
    }
     public Organism getOrganism(){
         return this.organism;
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
             nNet.attachEntity(this);
             nNet.parseData(obj);

             //this.tasks.addTask(2, new AIFindExistingOrganisims(this, EntityOrganism.class));

         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     public void attachOrganism(Organism _organism){
         organism = _organism;
     }
    public boolean getDebug(){
         return this.debug;
    }
    public void setDebug(boolean _debug){
        this.debug = _debug;
    }
    public float getAgeSeconds(){
        return (this.world.getWorldTime() - spawnTime)  / 20;
    }
    @Override
    public void onUpdate(){


        List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(2.0D, 1.0D, 2.0D));

        for (EntityItem item : items) {
            pickupItem(item);
        }
        if(!world.isRemote){
            //Tick neural net
            if(this.nNet != null) {
                List<OutputNeuron> outputs = this.nNet.evaluate();
                Iterator<OutputNeuron> iterator = outputs.iterator();
                while (iterator.hasNext()) {
                    OutputNeuron outputNeuron = iterator.next();
                    outputNeuron.execute();

                }
            }

            double yOffset = Math.sin(Math.toRadians(desiredPitch));
            double zOffset = Math.cos(Math.toRadians(this.desiredYaw)) * Math.cos(Math.toRadians(desiredPitch));
            double xOffset = Math.sin(Math.toRadians(this.desiredYaw)) * Math.cos(Math.toRadians(desiredPitch));
            this.getLookHelper().setLookPosition(posX + xOffset, posY + this.getEyeHeight() + yOffset, posZ + zOffset, 360, 360);
            this.renderYawOffset = 0;
            this.setRotation(this.rotationYaw, this.rotationPitch);

        }
        super.onUpdate();
        if(!world.isRemote) {
            if (
                //this.organism == null ||
                getAgeSeconds() > maxLifeSeconds ||
                this.spawnHash != ChaosCraft.spawnHash
            ) {
                //ChaosCraft.logger.info("Killing: " + this.getName() + " - AGE: " + age + " - maxLifeSeconds: " + maxLifeSeconds + " - Score: " + this.entityFitnessManager.totalScore());
                this.setDead();
                return;
            }




        }
    }

    @Override
    public void onLivingUpdate()
    {
        if (!this.world.isRemote)
        {

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

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        //this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    public float getMaxLife(){
        return maxLifeSeconds;
    }
    public void adjustMaxLife(int life) {
        maxLifeSeconds += life;
    }

    public void setSpawnHash(int _spawnHash) {
        this.spawnHash = _spawnHash;
    }
    public int getSpawnHash() {
        return this.spawnHash;
    }

    public void setSkin(String _skin) {
        this.skin = _skin;
        this.refreshRender = true;
    }

    public static class EntityOrganismRenderer extends RenderLiving<EntityOrganism> {

        public EntityOrganismRenderer(RenderManager rendermanagerIn) {
            super(rendermanagerIn, new ModelPlayer(.5f, false), 0.5f);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityOrganism entity) {
            if(entity.refreshRender){
                EntityOrganism realOrg = ChaosCraft.getEntityOrganismByName(entity.getName());
                if(realOrg != null){
                    entity.setSkin(realOrg.getSkin());
                    entity.refreshRender = false;
                }
            }
            return new ResourceLocation(entity.getSkin());
        }

    }

    private String getSkin() {
        return this.skin;
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

        return this.world.rayTraceBlocks(vec3d, vec3d2, false, false, false);
    }
    public RayTraceResult isEntityInLineOfSight(Entity target, double blockReachDistance) {
        Vec3d vec3d = this.getPositionEyes(1);
        Vec3d vec3d1 = this.getLook(1);
        Vec3d vec3d2 = vec3d.add(
                new Vec3d(
                        vec3d1.x * blockReachDistance,
                        vec3d1.y * blockReachDistance,
                        vec3d1.z * blockReachDistance
                )
        );

        return target.getEntityBoundingBox().calculateIntercept(vec3d, vec3d2);
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

        Material material = state.getMaterial();
        if(
            material == Material.WATER ||
            material == Material.AIR ||
            material == Material.LAVA
        ){
            return;
        }
        float hardness = state.getBlockHardness(world, pos);

        this.world.sendBlockBreakProgress(this.getEntityId(), pos, (int) (hardness * miningTicks * 10.0F) - 1);

        boolean harvest = true;//state.getBlock().canHarvestBlock(world, pos, fakePlayer);

        ItemStack stack = getHeldItemMainhand();
        String tool = state.getBlock().getHarvestTool(state);

        if(hardness != 0) {


            if (material.isToolNotRequired()) {
                hardness /= 100F;
            } else {
                if (
                    tool != null &&
                    (
                        stack.isEmpty() ||
                        stack.getItem().equals(tool)
                    )
                ) {
                    harvest = false;
                }
                hardness /= 300F;

            }

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

            }
            CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEventType.BLOCK_MINED);
            worldEvent.block = state.getBlock();
            entityFitnessManager.test(worldEvent);
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
            this.setHeldItem(EnumHand.MAIN_HAND, stack);
            stack = this.itemHandler.insertItem(i, stack, false);
            this.selectedItemIndex = i;
            //ItemStack itemStack = this.itemHandler.getStackInSlot(this.selectedItemIndex);


            //PacketHandler.INSTANCE.sendToAllTracking(new SyncHandsMessage(this.itemHandler.getStackInSlot(i), getEntityId(), i, selectedItemIndex), this);
        }
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
    public boolean attackEntityAsMob(Entity entityIn)
    {
        IAttributeInstance attributeInstance = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        float f = (float) attributeInstance.getAttributeValue();
        int i = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0 && entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }



}