package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class CraftAction extends NavigateToAction{

    protected String recipeId;
    public void setRecipe(IRecipe recipe){
        this.recipeId = recipe.getId().toString();
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
        stopWalking();
        tickArrived();
        //When looking at stuff do stuff.
        RecipeManager recipeManager = this.getActionBuffer().getOrgManager().getEntity().world.getRecipeManager();;
        IRecipe recipe = null;
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

    }
    public void encode(PacketBuffer buf){
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
        if (!craftAction.recipeId.equals(craftAction.recipeId)) {
            return false;
        }
        return true;
    }


    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {

        IRecipe recipe = orgEntity.getClientOrgManager().getScanManager().getRecipeScanInstance().getHighScoreRecipe();
        if (!recipe.canFit(2, 2)) {
            BlockPos blockPos = chaosTarget.getTargetBlockPos();
            if(blockPos == null){
                return false;
            }
            if(!(orgEntity.world.getBlockState(blockPos).getBlock() instanceof CraftingTableBlock)){
                return false;
            }
        }
        return true;
    }
}
