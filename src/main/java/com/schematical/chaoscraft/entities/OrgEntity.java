package com.schematical.chaoscraft.entities;

import com.mojang.authlib.GameProfile;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.client.gui.ChaosNNetViewOverlayGui;
import com.schematical.chaoscraft.client.gui.ChaosOrgDetailOverlayGui;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.fitness.EntityFitnessManager;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientOutputNeuronActionPacket;
import com.schematical.chaoscraft.server.ChaosCraftServerPlayerInfo;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.Organism;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ObjectHolder;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class OrgEntity extends MobEntity {

    @ObjectHolder("chaoscraft:org_entity")
    public static final EntityType<OrgEntity> ORGANISM_TYPE = null;
    public final double REACH_DISTANCE = 5.0D;

    public final NonNullList<ItemStack> orgInventory = NonNullList.withSize(36, ItemStack.EMPTY);
    public EntityFitnessManager entityFitnessManager;

    protected CCPlayerEntityWrapper playerWrapper;
    public CCObservableAttributeManager observableAttributeManager;
    private List<OrgEvent> events = new ArrayList<OrgEvent>();

    protected ServerOrgManager serverOrgManager = null;
    protected ClientOrgManager clientOrgManager = null;

    //The current selected item
    private int currentItem = 0;
    protected ItemStackHandler itemHandler = new ItemStackHandler();
    protected double desiredPitch;
    protected double desiredYaw;
    protected double desiredHeadYaw;
    //Whether the bot has tried left clicking last tick.
    private boolean lastTickLeftClicked;

    //Mining related variables
    private float hardness = 0;
    private BlockPos lastMinePos = BlockPos.ZERO;
    private int blockSoundTimer;

    private int miningTicks = 0;

    public List<AlteredBlockInfo> alteredBlocks = new ArrayList<AlteredBlockInfo>();
    private int equippedSlot = -1;
    private int rightClickDelay;

    protected NeuralNet nNet;

    private int selectedItemIndex;
    private boolean hasTraveled = false;
    private Vec3d spawnPos;
    private int ticksSinceObservationHack = -1;

    private int spawnHash;


    public OrgEntity(EntityType<? extends MobEntity> type, World world) {
        super((EntityType<? extends MobEntity>) type, world);
        //setHealth(1);
    }
    public void setSpawnHash(int _spawnHash) {
        this.spawnHash = _spawnHash;
    }

    public void setDesiredYaw(double _desiredYaw){
        this.desiredYaw = _desiredYaw;
    }
    public ClientOrgManager getClientOrgManager(){
        return clientOrgManager;
    }
    public ServerOrgManager getServerOrgManager(){ return serverOrgManager; }
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if(flag) {
            CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.ATTACK_SUCCESS);
            worldEvent.entity = entityIn;
            entityFitnessManager.test(worldEvent);
        }
        return flag;

    }
    public void attachNNetRaw(String nNetRaw){
        String nNetString = nNetRaw;//nNetRaw.getNNetRaw();
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
            ChaosCraft.LOGGER.error("Failed To Decode NNet: " + getCCNamespace() + " -- " + nNetString);
            e.printStackTrace();
        }
    }
    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return new ArrayList<ItemStack>();
    }
   /* @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }
    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {


        return ItemStack.EMPTY;
    }*/
    public NeuralNet getNNet(){
        return nNet;
    }


    public ItemStackHandler getItemStack(){
        return this.itemHandler;
    }
    @Override
    public void jump(){
        if(
            !this.isJumping &&
            !this.isAirBorne &&
            this.onGround
        ) {
            super.jump();
        }
    }
    @Override
    public HandSide getPrimaryHand() {
        return null;
    }
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);

    }

    public String getCCNamespace() {
       if(serverOrgManager != null){
           return serverOrgManager.getCCNamespace();
       }
        if(clientOrgManager != null){
            return clientOrgManager.getCCNamespace();
        }
        ChaosCraft.LOGGER.error("No valid CCNamespace found. No `serverOrgManager` or `clientOrgManager`");
        return null;
    }

    public boolean isEntityInLineOfSight(LivingEntity target, double blockReachDistance) {
        Vec3d vec3d = this.getEyePosition(1);
        Vec3d vec3d1 = this.getLook(1);
        Vec3d vec3d2 = vec3d.add(
                new Vec3d(
                        vec3d1.x * blockReachDistance,
                        vec3d1.y * blockReachDistance,
                        vec3d1.z * blockReachDistance
                )
        );

        return target.getBoundingBox().rayTrace(vec3d, vec3d2).isPresent();
    }
    public void setDesiredPitch(double _desiredPitch){
        this.desiredPitch = _desiredPitch;
    }

    public double getDesiredYaw(){
        return this.desiredYaw;
    }
    @Override
    public void livingTick() {

        super.livingTick();

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
        playerWrapper.setPosition(getPositionVec().x, getPositionVec().y, getPositionVec().z);
        playerWrapper.onGround = this.onGround;
        //playerWrapper.setHeldItem(Hand.MAIN_HAND, getHeldItemMainhand());
        return playerWrapper;
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


        for(int i = 0; i < orgInventory.size(); i++) {
            ItemStack itemStack = orgInventory.get(i);

            if(!itemStack.isEmpty()){
                recipeItemHelper.accountStack(itemStack);
            }
        }
        return recipeItemHelper;


    }


    public ItemStack craft(IRecipe recipe) {
        NonNullList<Ingredient> recipeItems = null;
      /*  if(recipe instanceof ShapedRecipes) {
            recipeItems = ((ShapedRecipes) recipe).recipeItems;
        }else if(recipe instanceof ShapelessRecipes) {
            recipeItems = ((ShapelessRecipes) recipe).recipeItems;
        }else{
            throw new ChaosNetException("Found a recipe unaccounted for: " + recipe.getType().toString() + "Class Name: " +  recipe.getClass().getName());
        }*/
        recipeItems = recipe.getIngredients();
        //Check to see if they have the items in inventory for that
        RecipeItemHelper recipeItemHelper = new RecipeItemHelper();
        int slots = orgInventory.size();
        int emptySlot = -1;

        List<Integer> usedSlots = new ArrayList<Integer>();
        for(Ingredient ingredient: recipeItems) {

            for (int i = 0; i < slots; i++) {
                ItemStack itemStack = orgInventory.get(i);
                if(itemStack.isEmpty()) {
                    emptySlot = i;
                }else{
                    int packedItem = RecipeItemHelper.pack(itemStack);
                    IntList ingredientItemIds = ingredient.getValidItemStacksPacked();
                    if (ingredientItemIds.contains(packedItem)) {
                        //int amountTaken = recipeItemHelper.tryTake(packedItem, 1);
                        if (orgInventory.get(i).getCount() < 1) {
                            throw new ChaosNetException("Cannot get any more of these");
                        }
                        orgInventory.remove(i);
                    }
                }


            }

        }

        ItemStack outputStack = recipe.getRecipeOutput().copy();
        //ChaosCraft.logger.info(this.getCCNamespace() + " - Crafted: " + outputStack.getDisplayName());
        if(emptySlot != -1) {
            orgInventory.add(emptySlot, outputStack);
            observableAttributeManager.ObserveCraftableRecipes(this);
        }else{

            entityDropItem(outputStack.getItem(), outputStack.getCount());
            outputStack.setCount(0);
        }

        return outputStack;
    }
    public ItemStack getStackInSlot( EquipmentSlotType slotIn) throws Exception {
        if (slotIn == EquipmentSlotType.MAINHAND) {
            return this.orgInventory.get(currentItem);
        }
        throw new Exception("TODO: Build inventory for :" + slotIn.getName());
    }

    public BlockRayTraceResult rayTraceBlocks(double blockReachDistance) {
        Vec3d vec3d = this.getEyePosition(1);
        Vec3d vec3d1 = this.getLook(1);
        Vec3d vec3d2 = vec3d.add(
                new Vec3d(
                        vec3d1.x * blockReachDistance,
                        vec3d1.y * blockReachDistance,
                        vec3d1.z * blockReachDistance
                )
        );

        return this.world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, this));
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

        BlockState state = world.getBlockState(pos);

        Material material = state.getMaterial();
        if(
                material == Material.WATER ||
                        material == Material.AIR ||
                        material == Material.LAVA
        ){
            return;
        }

        this.world.sendBlockBreakProgress(this.getEntityId(), pos, (int) (state.getPlayerRelativeBlockHardness(this.getPlayerWrapper(), world, pos) * miningTicks * 10.0F) - 1);


        boolean harvest = state.getBlock().canHarvestBlock(state, world, pos, this.getPlayerWrapper());

        ItemStack stack = getHeldItemMainhand();
        //String tool = state.getBlock().getHarvestTool(state);


        //Check if block has been broken
        if (state.getPlayerRelativeBlockHardness(this.getPlayerWrapper(), world, pos) * miningTicks > 1.0f) {
            //Broken
            miningTicks = 0;

            world.playEvent(2001, pos, Block.getStateId(state));





            alteredBlocks.add(
                new AlteredBlockInfo(
                        pos,
                        state
                )
            );
            boolean bool = world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
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

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();

        if(!world.isRemote) {
            replaceAlteredBlocks();
           /* if (chunkTicket != null) {
                ForgeChunkManager.releaseTicket(chunkTicket);
                chunkTicket = null;
            }*/

        }
    }
    public void replaceAlteredBlocks(){
        //ChaosCraft.LOGGER.info(this.getCCNamespace() + " - Trying to replace blocks - Count: " + alteredBlocks.size());
        for (AlteredBlockInfo alteredBlock : alteredBlocks) {
            boolean bool = world.setBlockState(alteredBlock.blockPos, alteredBlock.state, world.isRemote ? 11 : 3);
            String debugText = this.getCCNamespace() + " - Replacing: " + alteredBlock.state.getBlock().getRegistryName();
            if (bool) {
                ChaosCraft.LOGGER.info(debugText + " - Success");
            }else{
                ChaosCraft.LOGGER.info(debugText + " - Fail");
            }
        }
        alteredBlocks.clear();
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
                    this.setHeldItem(Hand.MAIN_HAND, itemStack);
                    equippedSlot = i;
                    return itemStack;
                }
            }
        }
        return null;
    }

    public int hasInInventory(Item item){
        //Check if it is in their inventory
        ItemStack stack = null;
        int slot = -1;
        for (int i = 0; i < this.orgInventory.size(); i++) {
            ItemStack checkStack = this.orgInventory.get(i);
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

    public void rightClick(BlockRayTraceResult result) {
        this.rightClickDelay = 4;
        for (Hand hand : Hand.values()) {


            BlockPos blockpos = result.getPos();
            BlockState state = this.world.getBlockState(blockpos);
            if (state.getMaterial() != Material.AIR) {

                ActionResultType enumactionresult = rightClickBlock(blockpos, hand, result);


                if (enumactionresult == ActionResultType.SUCCESS) {
                    this.swingArm(hand);

                    CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.BLOCK_PLACED);
                    worldEvent.block = state.getBlock();
                    entityFitnessManager.test(worldEvent);
                    return;
                }
            }



            ItemStack itemstack = getHeldItem(hand);

            if (!itemstack.isEmpty() && itemRightClick(hand).equals(ActionResultType.SUCCESS)) {
                //this.entityRenderer.itemRenderer.resetEquippedProgress(enumhand);
                //TODO: Make a fitness event for this
                return;
            }
        }
    }

    private ActionResultType rightClickBlock(BlockPos pos,  Hand hand, BlockRayTraceResult blockRayTraceResult) {
        ItemStack itemstack = getHeldItem(hand);

        if (itemstack.isEmpty()) return ActionResultType.PASS;

        /*float f = (float) (blockRayTraceResult.getHitVec().x - (double) pos.getX());
        float f1 = (float) (blockRayTraceResult.getHitVec().y - (double) pos.getY());
        float f2 = (float) (blockRayTraceResult.getHitVec().z - (double) pos.getZ());*/
        boolean flag = false;

        if (!world.getWorldBorder().contains(pos)) {
            return ActionResultType.FAIL;
        }

        ItemUseContext itemUseContext = new ItemUseContext(
                this.getPlayerWrapper(),
                hand,
                blockRayTraceResult
        );
        ActionResultType ret = itemstack.onItemUseFirst(itemUseContext);
        if (ret != ActionResultType.PASS) {
            return ret;
        }

        BlockState iblockstate = world.getBlockState(pos);
        boolean bypass = getHeldItemMainhand().doesSneakBypassUse(world, pos, this.getPlayerWrapper()) && getHeldItemOffhand().doesSneakBypassUse(world, pos, this.getPlayerWrapper());

       /* if ((*//*!this.isSneaking() ||*//* bypass)) {//BlockState state, World worldIn, BlockPos pos, PlayerEntity player
            flag = iblockstate.getBlock().onBlockClicked(iblockstate, world, pos, this.getPlayerWrapper());
        }

        if (!flag && itemstack.getItem() instanceof BlockItem) {
            BlockItem blockItem = (BlockItem) itemstack.getItem();

            *//*if (!blockItem.canPlaceBlockOnSide(world, pos, direction, this.getPlayerWrapper(), itemstack)) {
                return ActionResultType.FAIL;
            } *//*
        }

        if (!flag) {*/
            if (this.getPlayerWrapper().getCooldownTracker().hasCooldown(itemstack.getItem())) {
                return ActionResultType.PASS;
            } else {
                ItemUseContext itemUseContext1 = new ItemUseContext(
                        this.getPlayerWrapper(),
                        hand,
                        blockRayTraceResult
                );
                ActionResultType result = itemstack.onItemUse(itemUseContext1);
                if (result == ActionResultType.SUCCESS) {

                }
                return result;
            }
      /*  } else {
            return ActionResultType.SUCCESS;
        }*/

    }

    private ActionResultType itemRightClick(Hand hand) {
        ItemStack itemstack = getHeldItem(hand);

        if (this.getPlayerWrapper().getCooldownTracker().hasCooldown(itemstack.getItem())) {
            return ActionResultType.PASS;


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


    public ItemStack tossEquippedStack() {

        ItemStack itemStack = getHeldItem(Hand.MAIN_HAND);
        if(
            itemStack == null ||
            itemStack.isEmpty()
        ){
            return null;
        }
        Vec3d itemVec3d = null;



        Vec3d vec3d = this.getEyePosition(1);
        Vec3d vec3d1 = this.getLook(1);
        itemVec3d = vec3d.add(
            new Vec3d(
                vec3d1.x * 5d,
                vec3d1.y * 5d,
                vec3d1.z * 5d
            )
        );
        equippedSlot = 0;//TODO: fix this
        itemHandler.extractItem(equippedSlot, itemStack.getCount(), false);

        this.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
        ItemStack itemStackCheck = this.getHeldItem(Hand.MAIN_HAND);
        if(!itemStackCheck.equals(ItemStack.EMPTY)){
            throw new ChaosNetException("Hand still had equipped: " + itemStackCheck.getItem().toString() + " - after it was removed");
        }
        ItemEntity entityItem = new ItemEntity(world, itemVec3d.x, itemVec3d.y, itemVec3d.z);
        entityItem.setItem(itemStack);
        world.getServer().getWorld(DimensionType.OVERWORLD).summonEntity(entityItem);
        ChaosCraft.LOGGER.info(this.getCCNamespace() + " - Tossed item: " + itemStack.getItem().getItem().getRegistryName() + " now holding " + itemStackCheck.getItem().getRegistryName());
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


    @Override
    public void baseTick(){




        if(!world.isRemote){

            if(this.serverOrgManager != null) {
                this.checkHasTraveled();
                this.serverOrgManager.tickServer();
                this.updatePitchAndYaw();
                this.checkForItemPickup();
            }else{
                if( ChaosCraft.getServer().spawnHash != this.spawnHash){
                    this.setHealth(-1);
                    ChaosCraft.LOGGER.error("Killing Org that did not match spawn hash: " + this.getDisplayName().getString()  + " - " + ChaosCraft.getServer().spawnHash + " != " + this.spawnHash);
                }
            }

        }else{

            tickClient();

        }
        super.baseTick();
        if(
            !world.isRemote &&
            this.serverOrgManager != null
        ) {

            this.serverOrgManager.checkStatus();
        }
    }

    private void checkHasTraveled() {
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
    }

    private void updatePitchAndYaw(){
        double yOffset = Math.sin(Math.toRadians(desiredPitch));
        double zOffset = Math.cos(Math.toRadians(this.desiredHeadYaw)) * Math.cos(Math.toRadians(desiredPitch));
        double xOffset = Math.sin(Math.toRadians(this.desiredHeadYaw)) * Math.cos(Math.toRadians(desiredPitch));
        Vec3d pos = getEyePosition(1);

        this.getLookController().setLookPosition(pos.getX() + xOffset, pos.getY()/* + this.getEyeHeight() */+ yOffset, pos.getZ() + zOffset, 360, 360);
        this.renderYawOffset = 0;
        this.setRotation((float) this.desiredYaw, 0);
    }

    private void checkForItemPickup(){
        List<ItemEntity> items = this.world.getEntitiesWithinAABB(ItemEntity.class, this.getBoundingBox().grow(2.0D, 1.0D, 2.0D));

        for (ItemEntity item : items) {
            if(item.isAddedToWorld() && item.isAlive()) {
                this.pickupItem(item);
            }
        }
    }
    public boolean checkStatus(){
       /* if(this.spawnHash != ChaosCraft.getServer().spawnHash){
            killWithNoReport();
            return false;
        }
*/
        return false;
    }




    private void tickClient(){
        //Tick neural net
        if(
                this.nNet != null &&
                this.nNet.ready
        ) {

            List<OutputNeuron> outputs = this.nNet.evaluate();


            Iterator<OutputNeuron> iterator = outputs.iterator();

            while (iterator.hasNext()) {
                OutputNeuron outputNeuron = iterator.next();
                if(outputNeuron.executeSide.equals(OutputNeuron.ExecuteSide.Client)){
                    outputNeuron.execute();
                }else {
                    CCClientOutputNeuronActionPacket packet = new CCClientOutputNeuronActionPacket(
                            clientOrgManager.getCCNamespace(),
                            outputNeuron.id,
                            outputNeuron.getCurrentValue()
                    );
                    ChaosNetworkManager.sendToServer(packet);
                }


            }
            if(this.clientOrgManager.getState().equals(ClientOrgManager.State.EntityAttached)){
                this.clientOrgManager.markTicking();
            }

            this.clientOrgManager.fireTickables();


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

    public void pickupItem(ItemEntity item) {
        if (item.cannotPickup()) return;
        ChaosCraft.LOGGER.info(this.getCCNamespace() + " - Picked up: " + item.getItem().getItem().getRegistryName());
        ItemStack stack = item.getItem();

        Item worldEventItem = stack.getItem();
        //inventory.addItemStackToInventory(stack);
        for (int i = 0; i < this.itemHandler.getSlots() && !stack.isEmpty(); i++) {
            if(getHeldItemMainhand().getItem() == Item.getItemById(0)) {
                this.setHeldItem(Hand.MAIN_HAND, stack);
                equippedSlot = i;
            }

            stack = this.itemHandler.insertItem(i, stack, false);



            this.selectedItemIndex = i;

            //PacketHandler.INSTANCE.sendToAllTracking(new SyncHandsMessage(this.itemHandler.getStackInSlot(i), getEntityId(), i, selectedItemIndex), this);
        }
        item.detach();
        if (stack.isEmpty()) {
            item.remove();

        }
        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.ITEM_COLLECTED);
        worldEvent.item = worldEventItem;
        entityFitnessManager.test(worldEvent);

        if(observableAttributeManager != null) {
            observableAttributeManager.Observe(worldEventItem);
            //TODO: Recheck what you can craft
            observableAttributeManager.ObserveCraftableRecipes(this);
        }


    }


    private void observationHack() {
        this.ticksSinceObservationHack += 1;
        if(this.ticksSinceObservationHack < 100){
            return;
        }
        if(this.getBoundingBox() == null){
            return;
        }
        this.ticksSinceObservationHack = 0;

        //Find blocks
        Vec3d vec3d = this.getPositionVector();
        int RANGE = 3;
        for(double x = vec3d.x - RANGE; x < vec3d.x + RANGE; x++){
            for(double y = vec3d.y - RANGE; y < vec3d.y + RANGE; y++){
                for(double z = vec3d.z - RANGE; z < vec3d.z + RANGE; z++){
                    BlockState blockState = world.getBlockState(
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
                this.getBoundingBox().grow(RANGE)
        );
        for(Entity entity: entities){
            observableAttributeManager.Observe(entity);
        }
    }



    @Override
    public boolean processInteract(PlayerEntity player, Hand hand)
    {
        if(
            clientOrgManager == null ||
            clientOrgManager.getOrganism() == null
        ){
            //It is probablly not your org...
            //But try and load it anyway
            ChaosCraft.LOGGER.error("Clicked on an OrgEntity but it has no organism");
            return false;
        }

        if (this.world.isRemote) {
            ChaosCraft.getClient().showOrdDetailOverlay(clientOrgManager);
        }
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(!world.isRemote){
            if(entityFitnessManager != null) {//TODO:Delete me
                CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.HEALTH_CHANGE);
                worldEvent.entity = this;
                worldEvent.amount = -1 * (amount / this.getMaxHealth());
                entityFitnessManager.test(worldEvent);
                events.add(new OrgEvent(worldEvent, OrgEvent.DEFAULT_TTL));
            }
        }
        return super.attackEntityFrom(source, amount);
    }


    public void killWithNoReport() {
        setHealth(-1);
        //TODO: Stop it from reporting...
    }
    public List<OrgEvent> getOrgEvents(){
        return events;
    }
    public void addOrgEvent(OrgEvent orgEvent){
        if(ChaosCraft.getServer() == null){
            ChaosCraft.LOGGER.error("Ths should not get called client side");
            return;
        }

        events.add(orgEvent);
    }

    public void attachClientOrgEntity(ClientOrgManager clientOrgManager) {
        this.clientOrgManager = clientOrgManager;
    }



    public void attachSeverOrgManager(ServerOrgManager serverOrgManager) {
        this.serverOrgManager = serverOrgManager;
    }
    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);

    }

    public int getSpawnHash() {
        return spawnHash;
    }

    public double getDesiredHeadYaw() {
        return desiredHeadYaw;
    }

    public void setDesiredHeadYaw(double desiredHeadYaw) {
        this.desiredHeadYaw = desiredHeadYaw;
    }
}