package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.Block;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.math.BlockPos;

public class CraftAction extends NavigateToAction{


    public void setRecipe(IRecipe recipe){
        this.setTargetItem(new ChaosTargetItem(recipe));
    }
    @Override
    protected void _tick() {
       /* if (!getTargetItem().getRecipe().canFit(2, 2)) {
            ChaosCraft.LOGGER.info(getOrgEntity().getCCNamespace() + " TRYING TO  CRAFT BIG ITEM: " + getTargetItem().getRecipe().getId().toString());
        }
        if(
                getTarget().getTargetBlockPos()  != null
        ) {
            Block block = getOrgEntity().world.getBlockState(getTarget().getTargetBlockPos()).getBlock();
            if (block instanceof CraftingTableBlock) {
                ChaosCraft.LOGGER.info(getOrgEntity().getCCNamespace() + " Targeted a crafting table while trying to craft: " + getTargetItem().getRecipe().getId().toString());
            }
        }*/
        tickLook();
        if(
            !getTarget().canEntityTouch(getOrgEntity()) &&
            !getTarget().isEntityLookingAt(getOrgEntity())
        ){
            tickNavigate();
            return;
        }

        tickArrived();
        //When looking at stuff do stuff.
        RecipeManager recipeManager = this.getActionBuffer().getOrgManager().getEntity().world.getRecipeManager();;
        IRecipe recipe = null;
        String recipeId = getTargetItem().getRecipe().getId().toString();
        for (IRecipe irecipe : recipeManager.getRecipes())
        {

            String key = irecipe.getId().getNamespace() + ":" + irecipe.getId().getPath();
            if(recipeId.equals(key)){
                recipe = irecipe;
            }
        }
        if(recipe == null){
            throw new ChaosNetException("No recipe found with id: " + recipeId);
        }
        ItemStack outputStack = null;
        try {
            outputStack = getOrgEntity().craftWith(recipe, getTarget().getTargetBlockPos());
        }catch(ChaosNetException e){
            ChaosCraft.LOGGER.error(e.getMessage() + " - server is slightly out of sync?");
            markFailed();
            return;
        }

        if(outputStack == null){
           // throw new ChaosNetException("Something went wrong crafting: " + recipe.getType().toString() + " this should not be possible with the `evaluate` check above");
        }else {

            CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.ITEM_CRAFTED);
            worldEvent.item = outputStack.getItem();
            getOrgEntity().getServerOrgManager().test(worldEvent);
            markCompleted();
        }
    }
  /*  public void encode(PacketBuffer buf){
        super.encode(buf);

        buf.writeString(recipeId);
    }


    public void decode(PacketBuffer buf){
        super.decode(buf);
        recipeId = buf.readString(32767);
    }
    public boolean match(ActionBase actionBase){
        if(!super.match(actionBase)){
            return false;
        }
        CraftAction craftAction = (CraftAction) actionBase;
        if (!recipeId.equals(craftAction.recipeId)) {
            return false;
        }
        return true;
    }*/


    public static boolean validateTargetAndItem(OrgEntity orgEntity, ChaosTarget chaosTarget, ChaosTargetItem chaosTargetItem) {


       if(!validateTarget(orgEntity, chaosTarget)){
           return false;
       }
        if(!validateTargetItem(orgEntity, chaosTargetItem)){
            return false;
        }
        IRecipe recipe = chaosTargetItem.getRecipe();
        OrgEntity.CanCraftResults results = orgEntity.canCraftWithReturnDetail(recipe,recipe.getType(), chaosTarget.getTargetBlockPos());
        if(!results.equals(OrgEntity.CanCraftResults.Success)){
            return false;
        }

        return true;

    }

    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {

        if(chaosTarget.getTargetBlockPos() == null){
            return false;
        }
        Block block = orgEntity.world.getBlockState(chaosTarget.getTargetBlockPos()).getBlock();
        if (block instanceof CraftingTableBlock) {
            //ChaosCraft.LOGGER.info(orgEntity.getCCNamespace() + "VALIDATE CRAFTING TABLE! ");
        }else{
            return false;//TODO: remvoe this later
        }
        return true;

    }
    public static boolean validateTargetItem(OrgEntity orgEntity, ChaosTargetItem chaosTargetItem) {
        IRecipe recipe = chaosTargetItem.getRecipe();
        if(recipe == null){
            return false;
        }
      /*  if (!recipe.canFit(2, 2)) {
            ChaosCraft.LOGGER.info(orgEntity.getCCNamespace() + " CAN CRAFT BIG ITEM: " + recipe.getId().toString());
        }*/
        OrgEntity.CanCraftResults results = orgEntity.canCraftReturnDetail(recipe, recipe.getType());
        if(!results.equals(OrgEntity.CanCraftResults.Success)){
            if(ChaosCraft.getClient() != null) {
                throw new ChaosNetException("This should not be possible. Might be able to just return false: " + recipe.getId().toString() + " - Result: " + results.toString());
            }
            return false;
        }
        return true;

    }

}
