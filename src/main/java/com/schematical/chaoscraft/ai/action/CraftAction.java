package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaoscraft.util.ChaosTargetItem;
import com.schematical.chaosnet.model.ChaosNetException;
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


        IRecipe recipe = chaosTargetItem.getRecipe();
        if(recipe == null){
            return false;
        }
        OrgEntity.CanCraftResults results = orgEntity.canCraftWithReturnDetail(recipe,recipe.getType(), chaosTarget.getTargetBlockPos());
        if(!results.equals(OrgEntity.CanCraftResults.Success)){
            return false;
        }

        return true;

    }

    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget, ChaosTargetItem chaosTargetItem) {


        return true;

    }
    public static boolean validateTargetItem(OrgEntity orgEntity, ChaosTargetItem chaosTargetItem) {
        IRecipe recipe = chaosTargetItem.getRecipe();
        if(recipe == null){
            return false;
        }

        if(!orgEntity.canCraft(recipe)){
            throw new ChaosNetException("This should not be possible. Might be able to just return false");
            //return false;
        }
        return true;

    }

}
