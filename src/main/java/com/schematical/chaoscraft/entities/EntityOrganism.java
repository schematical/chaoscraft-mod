package com.schematical.chaoscraft.entities;

/**
 * Created by user1a on 12/3/18.
 */


import com.mojang.authlib.GameProfile;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.AreaOfFocus;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.fitness.EntityFitnessManager;
import com.schematical.chaoscraft.gui.CCOrgDetailView;
import com.schematical.chaoscraft.inventory.InventoryOrganism;
import com.schematical.chaoscraft.proxies.CCIMessage;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.NNetRaw;
import com.schematical.chaosnet.model.Organism;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.annotation.Nonnull;
import java.util.*;

public class EntityOrganism extends EntityLiving {
    public final double REACH_DISTANCE = 5.0D;
    private final long spawnTime;
    protected CCPlayerEntityWrapper playerWrapper;

    public EntityFitnessManager entityFitnessManager;
    protected Organism organism;
    protected NeuralNet nNet;

    protected ItemStackHandler itemHandler = new ItemStackHandler();
    protected BlockPos lastMinePos = BlockPos.ORIGIN.down();
    int rightClickDelay = 0;

    protected int miningTicks = 0;
    protected int selectedItemIndex = 0;
    protected float maxLifeSeconds = 10;
    protected int ticksSinceObservationHack = 0;

    public boolean hasAttemptedReport = false;
    public boolean hasFinishedReport = false;
    protected int spawnHash;

    protected double desiredPitch;
    protected double desiredYaw;
    protected boolean debug = false;
    public boolean refreshRender = false;
    public int equippedSlot;
    protected String skin = "chaoscraft:morty.png";
    public InventoryOrganism inventory;
    public CCObservableAttributeManager observableAttributeManager;
    public HashMap<String, BiologyBase> inputs = new HashMap<String, BiologyBase>();
    public List<OrgEvent> events = new ArrayList<OrgEvent>();
    public List<AlteredBlockInfo> alteredBlocks = new ArrayList<AlteredBlockInfo>();
    public EntityPlayerMP observingPlayer;
    public Vec3d spawnPos;
    private boolean hasTraveled = false;
    public int ticksWithouUpdate = 0;

    public ForgeChunkManager.Ticket chunkTicket;

    public EntityOrganism(World worldIn) {
        this(worldIn, "EntityOrganism");
    }

    public EntityOrganism(World worldIn, String name) {
        super(worldIn);

        this.tasks.taskEntries.clear();

        if(!world.isRemote) {
            chunkTicket = ForgeChunkManager.requestTicket(ChaosCraft.INSTANCE, world, ForgeChunkManager.Type.ENTITY);
            chunkTicket.bindEntity(this);
        }

        //this.tasks.addTask(1, new EntityAISwimming(this));

        this.entityFitnessManager = new EntityFitnessManager(this);

        spawnTime = world.getWorldTime();
        this.refreshRender = true;
        this.itemHandler.setSize(64);
        /*this.inventory = new InventoryOrganism(this);
        ItemStack stack1 = new ItemStack(Items.STICK, 2);
        this.itemHandler.setStackInSlot(0, stack1);

        ItemStack stack2 = new ItemStack(Blocks.PLANKS, 3);
        this.itemHandler.setStackInSlot(1, stack2);*/
        this.enablePersistence();
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
     public CCPlayerEntityWrapper getPlayerWrapper(){
         if(playerWrapper == null){
             GameProfile gameProfile = new GameProfile(null, this.getCCNamespace());
             playerWrapper = new CCPlayerEntityWrapper(world, gameProfile);
             playerWrapper.entityOrganism = this;
         }
         playerWrapper.prevRotationPitch = this.prevRotationPitch;
         playerWrapper.rotationPitch  = this.rotationPitch;
         playerWrapper.prevRotationYaw  = this.prevRotationYaw;
         playerWrapper.rotationYaw = this.rotationYaw;
         playerWrapper.prevPosX  = this.prevPosX;
         playerWrapper.prevPosY  = this.prevPosY;
         playerWrapper.prevPosZ  = this.prevPosZ;
         playerWrapper.posX = this.posX;
         playerWrapper.posY = this.posY;
         playerWrapper.posZ = this.posZ;
         playerWrapper.onGround = this.onGround;
         playerWrapper.setHeldItem(EnumHand.MAIN_HAND, getHeldItemMainhand());
         return playerWrapper;
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
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(!world.isRemote){
            CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.HEALTH_CHANGE);
            worldEvent.entity = this;
            worldEvent.amount = -1 * (amount/this.getMaxHealth());
            entityFitnessManager.test(worldEvent);
            events.add(new OrgEvent(worldEvent, OrgEvent.DEFAULT_TTL));
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void onUpdate(){

        if(this.firstUpdate) {
            if(!this.world.isRemote) {
                ForgeChunkManager.forceChunk(chunkTicket, new ChunkPos(this.getPosition()));
            }
        }

        if(this.isDead || this.dead) {
            if(chunkTicket!=null) {
                ForgeChunkManager.releaseTicket(chunkTicket);
                chunkTicket = null;
            }
        }
        ticksWithouUpdate = 0;

        if(getDebug()){

        }

        if(!world.isRemote){

            //Tick neural net
            if(
                this.nNet != null &&
                this.nNet.ready
            ) {
                if(!this.hasTraveled) {
                    if (this.spawnPos != null) {
                        if (this.spawnPos.distanceTo(this.getPositionVector()) > 5) {
                            CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.HAS_TRAVELED);
                            entityFitnessManager.test(worldEvent);
                        }
                    } else {
                        this.spawnPos = this.getPositionVector();
                    }
                }

                List<OutputNeuron> outputs = this.nNet.evaluate();
                Iterator<OutputNeuron> iterator = outputs.iterator();
                JSONObject jsonObject = null;
                JSONArray outputValues = null;
                if(observingPlayer != null) {
                    if(observingPlayer.getSpectatingEntity().equals(this)) {
                        jsonObject = new JSONObject();
                        outputValues = new JSONArray();
                    }else{
                        observingPlayer = null;
                    }
                }
                while (iterator.hasNext()) {

                    OutputNeuron outputNeuron = iterator.next();
                    outputNeuron.execute();

                    if(observingPlayer != null){
                        JSONObject neuronOutput = new JSONObject();
                        neuronOutput.put("summary", outputNeuron.toLongString());
                        neuronOutput.put("_lastValue", outputNeuron._lastValue);
                        outputValues.add( neuronOutput);

                    }


                }
                if(observingPlayer != null) {
                    jsonObject.put("namespace", this.organism.getNamespace());
                    jsonObject.put("score", this.entityFitnessManager.totalScore());
                    jsonObject.put("age", this.getAgeSeconds());
                    jsonObject.put("maxAge", this.maxLifeSeconds);
                    jsonObject.put("outputs", outputValues);
                    AreaOfFocus areaOfFocus = (AreaOfFocus)nNet.getBiology("AreaOfFocus_0");
                    JSONObject areaOfFocusJSON = new JSONObject();
                    areaOfFocusJSON.put("x", areaOfFocus.getFocusPoint().x);
                    areaOfFocusJSON.put("y", areaOfFocus.getFocusPoint().y);
                    areaOfFocusJSON.put("z", areaOfFocus.getFocusPoint().z);
                    areaOfFocusJSON.put("range", areaOfFocus.viewRange);
                    jsonObject.put("areaOfFocus", areaOfFocusJSON);
                    ChaosCraft.networkWrapper.sendTo(new CCIMessage(jsonObject), (EntityPlayerMP) observingPlayer);

               }

                List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(2.0D, 1.0D, 2.0D));

                for (EntityItem item : items) {
                    pickupItem(item);
                }


                double yOffset = Math.sin(Math.toRadians(desiredPitch));
                double zOffset = Math.cos(Math.toRadians(this.desiredYaw)) * Math.cos(Math.toRadians(desiredPitch));
                double xOffset = Math.sin(Math.toRadians(this.desiredYaw)) * Math.cos(Math.toRadians(desiredPitch));
                this.getLookHelper().setLookPosition(posX + xOffset, posY + this.getEyeHeight() + yOffset, posZ + zOffset, 360, 360);
                this.renderYawOffset = 0;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.observationHack();
                Iterator<OrgEvent> eventIterator = events.iterator();

                while(eventIterator.hasNext()){
                    OrgEvent event = eventIterator.next();
                    int eventTTL = event.tick();
                    if(eventTTL <= 0){
                        eventIterator.remove();
                    }
                    //Check to see if there is a reward prediction
                }
            }

        }
        super.onUpdate();
        if(!world.isRemote) {

            checkStatus();
        }
    }
    public boolean checkStatus(){
        if (
            //this.organism == null ||
                getAgeSeconds() > maxLifeSeconds ||
                        this.spawnHash != ChaosCraft.spawnHash
                ) {
            //this.dropInventory();
            this.setDead();
            return true;
        }
        return false;
    }
    public void manualUpdateCheck(){
        ticksWithouUpdate += 1;
        if(ticksWithouUpdate > 5){
            checkStatus();
        }
    }

    private void observationHack() {
        this.ticksSinceObservationHack += 1;
        if(this.ticksSinceObservationHack < 100){
            return;
        }
        this.ticksSinceObservationHack = 0;

        //Find blocks
        Vec3d vec3d = this.getPositionVector();
        int RANGE = 3;
        for(double x = vec3d.x - RANGE; x < vec3d.x + RANGE; x++){
            for(double y = vec3d.y - RANGE; y < vec3d.y + RANGE; y++){
                for(double z = vec3d.z - RANGE; z < vec3d.z + RANGE; z++){
                    IBlockState blockState = world.getBlockState(
                        new BlockPos(x, y, z)
                    );
                    Block block = blockState.getBlock();
                    observableAttributeManager.Observe(block);
                }
            }
        }

        //Find entities
        List<Entity> entities = world.getEntitiesWithinAABB(
            Entity.class,
            this.getEntityBoundingBox().grow(RANGE)
        );
        for(Entity entity: entities){
            observableAttributeManager.Observe(entity);
        }
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

    public boolean canCraft(IRecipe recipe) {
        //Check to see if they have the items in inventory for that


        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        if(ingredients.size() == 0){
            return false;
        }
        RecipeItemHelper recipeItemHelper = getRecipeItemHelper();

        boolean result = recipeItemHelper.canCraft(recipe, null);

        return result;

    }
    public RecipeItemHelper getRecipeItemHelper(){

        RecipeItemHelper recipeItemHelper = new RecipeItemHelper();
        int slots = itemHandler.getSlots();

        for(int i = 0; i < slots; i++) {
            ItemStack itemStack = itemHandler.getStackInSlot(i);

            if(!itemStack.isEmpty()){
                recipeItemHelper.accountStack(itemStack);
            }
        }
        return recipeItemHelper;


    }


    public ItemStack craft(IRecipe recipe) {
        NonNullList<Ingredient> recipeItems = null;
        if(recipe instanceof ShapedRecipes) {
            recipeItems = ((ShapedRecipes) recipe).recipeItems;
        }else if(recipe instanceof ShapelessRecipes) {
            recipeItems = ((ShapelessRecipes) recipe).recipeItems;
        }else{
            throw new ChaosNetException("Found a recipe unaccounted for: " + recipe.getRegistryName().toString() + "Class Name: " +  recipe.getClass().getName());
        }
        //Check to see if they have the items in inventory for that
        RecipeItemHelper recipeItemHelper = new RecipeItemHelper();
        int slots = itemHandler.getSlots();
        int emptySlot = -1;

        List<Integer> usedSlots = new ArrayList<Integer>();
        for(Ingredient ingredient: recipeItems) {

            for (int i = 0; i < slots; i++) {
                ItemStack itemStack = itemHandler.getStackInSlot(i);
                if(itemStack.isEmpty()) {
                    emptySlot = i;
                }else{
                    int packedItem = RecipeItemHelper.pack(itemStack);
                    IntList ingredientItemIds = ingredient.getValidItemStacksPacked();
                    if (ingredientItemIds.contains(packedItem)) {
                        //int amountTaken = recipeItemHelper.tryTake(packedItem, 1);
                        if (itemHandler.getStackInSlot(i).getCount() < 1) {
                            throw new ChaosNetException("Cannot get any more of these");
                        }
                        itemHandler.extractItem(i, 1, false);
                    }
                }


            }

        }

        ItemStack outputStack = recipe.getRecipeOutput().copy();
        //ChaosCraft.logger.info(this.getCCNamespace() + " - Crafted: " + outputStack.getDisplayName());
        if(emptySlot != -1) {
            itemHandler.insertItem(emptySlot, outputStack, false);
            observableAttributeManager.ObserveCraftableRecipes(this);
        }else{
            dropItem(outputStack.getItem(), outputStack.getCount());
            outputStack.setCount(0);
        }

        return outputStack;
    }
    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(@Nonnull DamageSource cause)
    {
        super.onDeath(cause);
        if (!this.world.isRemote)
        {
            ForgeChunkManager.releaseTicket(chunkTicket);
            chunkTicket = null;
            dropInventory();
            if (world.getMinecraftServer() != null) {
                world.getMinecraftServer().getPlayerList().sendMessage(cause.getDeathMessage(this));
            }
            replaceAlteredBlocks();

        }

    }
    public void dropInventory(){
        if (!this.world.isRemote)
        {

            int slots = this.itemHandler.getSlots();
            for(int i = 0; i < slots; i++) {
                ItemStack itemStack = this.itemHandler.getStackInSlot(i);
                if(!itemStack.isEmpty()){
                    this.dropItem(itemStack.getItem(), itemStack.getCount());
                    this.itemHandler.extractItem(i, itemStack.getCount(), false);
                }
            }
        }
    }
    public void replaceAlteredBlocks(){
        ChaosCraft.logger.info(this.getCCNamespace() + " - Trying to replace blocks - Count: " + alteredBlocks.size());
        for (AlteredBlockInfo alteredBlock : alteredBlocks) {
            boolean bool = world.setBlockState(alteredBlock.blockPos, alteredBlock.state, world.isRemote ? 11 : 3);
            String debugText = this.getCCNamespace() + " - Replacing: " + alteredBlock.state.getBlock().getLocalizedName();
            if (bool) {
                ChaosCraft.logger.info(debugText + " - Success");
            }else{
                ChaosCraft.logger.info(debugText + " - Fail");
            }
        }
        alteredBlocks.clear();
    }
    public int getEmptyInventorySlot(){
        int emptySlot = -1;
        List<Integer> usedSlots = new ArrayList<Integer>();
        ItemStackHandler itemStackHandler = getItemStack();
        int slots = itemStackHandler.getSlots();
        for(int i = 0; i < slots; i++) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(i);
            if(itemStack.isEmpty()){
                emptySlot = i;
            }
        }

        if(usedSlots.size() == 0){

            return -1;
        }
        return emptySlot;

    }

    public ItemStack equip(String resourceId) {

        ItemStackHandler itemStackHandler = getItemStack();
        int slots = itemStackHandler.getSlots();
        for(int i = 0; i < slots; i++) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(i);
            if(!itemStack.isEmpty()){
                CCObserviableAttributeCollection observiableAttributeCollection = observableAttributeManager.Observe(itemStack.getItem());
                if(
                    observiableAttributeCollection != null &&
                    observiableAttributeCollection.resourceId.equals(resourceId)
                ){
                    this.setHeldItem(EnumHand.MAIN_HAND, itemStack);
                    equippedSlot = i;
                    return itemStack;
                }
            }
        }
        return null;
    }

    public ItemStack tossEquippedStack() {
        ItemStack itemStack = getHeldItem(EnumHand.MAIN_HAND);
        if(
            itemStack == null ||
            itemStack.isEmpty()
        ){
            return null;
        }
        Vec3d itemVec3d = null;
        //ChaosCraft.logger.info(this.getCCNamespace() + " - Tossing: " + itemStack.getItem().getRegistryName());



        Vec3d vec3d = this.getPositionEyes(1);
        Vec3d vec3d1 = this.getLook(1);
        itemVec3d = vec3d.add(
            new Vec3d(
                vec3d1.x * 5d,
                vec3d1.y * 5d,
                vec3d1.z * 5d
            )
        );

        itemHandler.extractItem(equippedSlot, itemStack.getCount(), false);
        equippedSlot = -1;
        this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        EntityItem entityItem = new EntityItem(world, itemVec3d.x, itemVec3d.y, itemVec3d.z);
        entityItem.setItem(itemStack);
        world.spawnEntity(entityItem);


        return itemStack;
    }

    public ItemStack getItemStackFromInventory(String resourceId) {
        int slots = itemHandler.getSlots();
        for(int i = 0; i < slots; i++) {
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if(!itemStack.isEmpty()){
                CCObserviableAttributeCollection observiableAttributeCollection = observableAttributeManager.Observe(itemStack.getItem());
                if(
                    observiableAttributeCollection != null &&
                    observiableAttributeCollection.resourceId.equals(resourceId)
                ){
                    return itemStack;
                }
            }
        }
        return null;
    }

    public void setObserving(EntityPlayerMP observingPlayer) {
        this.observingPlayer = observingPlayer;
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
    @SideOnly(Side.CLIENT)
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        CCOrgDetailView view = new CCOrgDetailView(this);
        Minecraft.getMinecraft().displayGuiScreen(view);
        return true;
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

        this.world.sendBlockBreakProgress(this.getEntityId(), pos, (int) (state.getPlayerRelativeBlockHardness(this.getPlayerWrapper(), world, pos) * miningTicks * 10.0F) - 1);


        boolean harvest = state.getBlock().canHarvestBlock(world, pos, this.getPlayerWrapper());

        ItemStack stack = getHeldItemMainhand();
        //String tool = state.getBlock().getHarvestTool(state);


        //Check if block has been broken
        if (state.getPlayerRelativeBlockHardness(this.getPlayerWrapper(), world, pos) * miningTicks > 1.0f) {
            //Broken
            miningTicks = 0;

            world.playEvent(2001, pos, Block.getStateId(state));




//!!!!
            alteredBlocks.add(
                new AlteredBlockInfo(
                    pos,
                    state
                )
            );
            boolean bool = world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
            if (bool) {
                state.getBlock().onPlayerDestroy(world, pos, state);
            } else {
                harvest = false;
            }
            //ChaosCraft.logger.info(this.getName() + " Mining: " + state.getBlock().getRegistryName().toString() +  " Held Stack: " + stack.getItem().getRegistryName().toString() + "  Harvest: "  + harvest);

            if (harvest) {
                state.getBlock().harvestBlock(world, this.getPlayerWrapper(), pos, state, world.getTileEntity(pos), stack);
                CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.BLOCK_MINED);
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
        //ChaosCraft.logger.info(this.getCCNamespace() + " - Picked up: " + item.getItem().getItem().getRegistryName());
        ItemStack stack = item.getItem();

        Item worldEventItem = stack.getItem();
        //inventory.addItemStackToInventory(stack);
        for (int i = 0; i < this.itemHandler.getSlots() && !stack.isEmpty(); i++) {
            if(getHeldItemMainhand().getItem() == Item.getItemById(0)) {
                this.setHeldItem(EnumHand.MAIN_HAND, stack);
                equippedSlot = i;
            }

            stack = this.itemHandler.insertItem(i, stack, false);



            this.selectedItemIndex = i;

            //PacketHandler.INSTANCE.sendToAllTracking(new SyncHandsMessage(this.itemHandler.getStackInSlot(i), getEntityId(), i, selectedItemIndex), this);
        }
        if (stack.isEmpty()) {
            item.setDead();
        }

        if(observableAttributeManager != null) {
            observableAttributeManager.Observe(worldEventItem);
        }
        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.ITEM_COLLECTED);
        worldEvent.item = worldEventItem;
        entityFitnessManager.test(worldEvent);
        //TODO: Recheck what you can craft
        nNet.entity.observableAttributeManager.ObserveCraftableRecipes(this);


    }
    public void rightClick(RayTraceResult result) {
        this.rightClickDelay = 4;
        for (EnumHand hand : EnumHand.values()) {

            switch (result.typeOfHit) {
                case BLOCK:
                    BlockPos blockpos = result.getBlockPos();
                    IBlockState state = this.world.getBlockState(blockpos);
                    if (state.getMaterial() != Material.AIR) {

                        EnumActionResult enumactionresult = rightClickBlock(blockpos, result.sideHit, result.hitVec, hand);


                        if (enumactionresult == EnumActionResult.SUCCESS) {
                            this.swingArm(hand);

                            CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.BLOCK_PLACED);
                            worldEvent.block = state.getBlock();
                            entityFitnessManager.test(worldEvent);
                            return;
                        }
                    }

                    List<UUID> uuids = new ArrayList<>();

                    break;

                default:
                    break;
            }

            ItemStack itemstack = getHeldItem(hand);

            if (!itemstack.isEmpty() && itemRightClick(hand) == EnumActionResult.SUCCESS) {
                //                this.entityRenderer.itemRenderer.resetEquippedProgress(enumhand);
                return;
            }
        }
    }

    private EnumActionResult itemRightClick(EnumHand hand) {
        ItemStack itemstack = getHeldItem(hand);

        if (this.getPlayerWrapper().getCooldownTracker().hasCooldown(itemstack.getItem())) {
            return EnumActionResult.PASS;


        } else {
            int i = itemstack.getCount();
            ActionResult<ItemStack> actionresult = itemstack.useItemRightClick(world, this.getPlayerWrapper(), hand);
            ItemStack itemstack1 = actionresult.getResult();

            if (itemstack1 != itemstack || itemstack1.getCount() != i) {
                this.setHeldItem(hand, itemstack1);


                if (itemstack1.isEmpty()) {
                    net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(this.getPlayerWrapper(), itemstack, hand);
                }
            }

            return actionresult.getType();
        }
    }

    private EnumActionResult rightClickBlock(BlockPos pos, EnumFacing direction, Vec3d vec, EnumHand hand) {
        ItemStack itemstack = getHeldItem(hand);

        if (itemstack.isEmpty()) return EnumActionResult.PASS;

        float f = (float) (vec.x - (double) pos.getX());
        float f1 = (float) (vec.y - (double) pos.getY());
        float f2 = (float) (vec.z - (double) pos.getZ());
        boolean flag = false;

        if (!world.getWorldBorder().contains(pos)) {
            return EnumActionResult.FAIL;
        } else {

            EnumActionResult ret = itemstack.onItemUseFirst(this.getPlayerWrapper(), world, pos, hand, direction, f, f1, f2);
            if (ret != EnumActionResult.PASS) {
                return ret;
            }

            IBlockState iblockstate = world.getBlockState(pos);
            boolean bypass = getHeldItemMainhand().doesSneakBypassUse(world, pos, this.getPlayerWrapper()) && getHeldItemOffhand().doesSneakBypassUse(world, pos, this.getPlayerWrapper());

            if ((!this.isSneaking() || bypass)) {
                flag = iblockstate.getBlock().onBlockActivated(world, pos, iblockstate, this.getPlayerWrapper(), hand, direction, f, f1, f2);
            }

            if (!flag && itemstack.getItem() instanceof ItemBlock) {
                ItemBlock itemblock = (ItemBlock) itemstack.getItem();

                if (!itemblock.canPlaceBlockOnSide(world, pos, direction, this.getPlayerWrapper(), itemstack)) {
                    return EnumActionResult.FAIL;
                } else {



                }
            }

            if (!flag) {
                if (itemstack.isEmpty()) {
                    return EnumActionResult.PASS;
                } else if (this.getPlayerWrapper().getCooldownTracker().hasCooldown(itemstack.getItem())) {
                    return EnumActionResult.PASS;
                } else {
                    EnumActionResult result = itemstack.onItemUse(this.getPlayerWrapper(), world, pos, hand, direction, f, f1, f2);
                    if (result == EnumActionResult.SUCCESS) {
                    }
                    return result;
                }
            } else {
                return EnumActionResult.SUCCESS;
            }
        }
    }

    public int hasInInventory(Item item){
        //Check if it is in their inventory
        ItemStack stack = null;
        int slot = -1;
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            ItemStack checkStack = this.itemHandler.getStackInSlot(i);
            Item _item = checkStack.getItem();

            if(_item.equals(item)){
                slot = i;
            }

        }
        if (stack == null  ||stack.isEmpty()) {
            return -1;
        }
        return slot;
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

            CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.ENTITY_ATTACKED);
            worldEvent.entity = entityIn;
            entityFitnessManager.test(worldEvent);
        }

        return flag;
    }

    @Override
    public void setDead() {
        super.setDead();

        if(!world.isRemote) {
            ForgeChunkManager.releaseTicket(chunkTicket);
            chunkTicket = null;
        }
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();

        if(!world.isRemote) {
            replaceAlteredBlocks();
            if (chunkTicket != null) {
                ForgeChunkManager.releaseTicket(chunkTicket);
                chunkTicket = null;
            }
        }
    }
}
