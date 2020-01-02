package com.schematical.chaoscraft.entities;

import com.mojang.authlib.GameProfile;
import com.schematical.chaoscraft.ai.CCObservableAttributeManager;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.fitness.EntityFitnessManager;
import com.schematical.chaosnet.model.ChaosNetException;
import it.unimi.dsi.fastutil.ints.IntList;
import jdk.nashorn.internal.codegen.Compiler;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

public class OrgEntity extends LivingEntity {

    @ObjectHolder("chaoscraft:org_entity")
    public static final EntityType<OrgEntity> ORGANISM_TYPE = null;

    public final NonNullList<ItemStack> orgInventory = NonNullList.withSize(36, ItemStack.EMPTY);
    public EntityFitnessManager entityFitnessManager;

    protected CCPlayerEntityWrapper playerWrapper;
    public CCObservableAttributeManager observableAttributeManager;
    public List<OrgEvent> events = new ArrayList<OrgEvent>();

    //The current selected item
    private int currentItem = 0;

    protected double desiredPitch;
    protected double desiredYaw;
    //Whether the bot has tried left clicking last tick.
    private boolean lastTickLeftClicked;

    //Mining related variables
    private float hardness = 0;
    private BlockPos lastMinePos = BlockPos.ZERO;
    private int blockSoundTimer;

    public OrgEntity(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return new ArrayList<ItemStack>();
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {


        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override
    public HandSide getPrimaryHand() {
        return null;
    }


    public String getCCNamespace() {
        return "...TODO: Stuff";
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

        return target.getCollisionBoundingBox().rayTrace(vec3d, vec3d2).isPresent();
    }
    public void setDesiredPitch(double _desiredPitch){
        this.desiredPitch = _desiredPitch;
    }
    public void updatePitchYaw(){
        double yOffset = Math.sin(Math.toRadians(desiredPitch));
        double zOffset = Math.cos(Math.toRadians(this.desiredYaw)) * Math.cos(Math.toRadians(desiredPitch));
        double xOffset = Math.sin(Math.toRadians(this.desiredYaw)) * Math.cos(Math.toRadians(desiredPitch));
        this.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(getPositionVec().x + xOffset, getPositionVec().y + this.getEyeHeight() + yOffset, getPositionVec().z + zOffset));
        this.renderYawOffset = 0;
        this.setRotation(this.rotationYaw, this.rotationPitch);

    }
    @Override
    public void livingTick() {
        updatePitchYaw();
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
        //playerWrapper.setHeldItem(EnumHand.MAIN_HAND, getHeldItemMainhand());
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
}