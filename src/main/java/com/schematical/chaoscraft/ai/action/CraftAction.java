package com.schematical.chaoscraft.ai.action;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaoscraft.services.targetnet.ScanRecipeInstance;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.Tags;

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
        ItemStack outputStack = null;
        try {
            outputStack = getOrgEntity().craft(recipe);
        }catch(ChaosNetException e){
            ChaosCraft.LOGGER.error(e.getMessage() + " - server is slightly out of sync?");
            markFailed();
            return;
        }

        if(outputStack == null){
            throw new ChaosNetException("Something went wrong crafting: " + recipe.getType().toString() + " this should not be possible with the `evaluate` check above");
        }

        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.ITEM_CRAFTED);
        worldEvent.item = outputStack.getItem();
        getOrgEntity().getServerOrgManager().test(worldEvent);
        markCompleted();

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
        if (!recipeId.equals(craftAction.recipeId)) {
            return false;
        }
        return true;
    }


    public static boolean validateTarget(OrgEntity orgEntity, ChaosTarget chaosTarget) {

        ClientOrgManager clientOrgManager = orgEntity.getClientOrgManager();
        ScanManager scanManager = clientOrgManager.getScanManager();
        ScanRecipeInstance scanRecipeInstance =  scanManager.getRecipeScanInstance();

        IRecipe recipe = scanRecipeInstance.getHighScoreRecipe();

        if(recipe == null){
            return false;
        }
        if(recipe.getType().equals(IRecipeType.CRAFTING)){
            if (!recipe.canFit(2, 2)) {
                BlockPos blockPos = chaosTarget.getTargetBlockPos();
                if (blockPos == null) {
                    return false;
                }
                if (!(orgEntity.world.getBlockState(blockPos).getBlock() instanceof CraftingTableBlock)) {
                    return false;
                }
            }
        }else{
            throw new ChaosNetException("TODO: Matt - write this");
        }
        return true;

    }
}
