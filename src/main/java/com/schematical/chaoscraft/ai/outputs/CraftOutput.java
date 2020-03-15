package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;


/**
 * Created by user1a on 12/10/18.
 */
public class CraftOutput extends OutputNeuron {
    private String recipeId;
    protected IRecipe recipe;
    public void init(){
        RecipeManager recipeManager = this.nNet.entity.world.getRecipeManager();;
       /* if(this.nNet.entity.world.isRemote){
            t
        }else {
            recipeManager = this.nNet.entity.getServer().getRecipeManager();
        }*/
        for (IRecipe irecipe : recipeManager.getRecipes())
        {

            String key = irecipe.getId().getNamespace() + ":" + irecipe.getId().getPath();
            if(recipeId.equals(key)){
                recipe = irecipe;
            }


        }
        if(recipe == null){
            throw new ChaosNetException("Cant find Recipe: " + recipeId);
        }

    }



    @Override
    public float evaluate(){
        if(getHasBeenEvaluated()){
            return getCurrentValue();
        }
       /* if(nNet.entity.getDebug()) {
            ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Checking to see if they can Craft: " + recipe.getRegistryName() + " - " + recipe.getRecipeOutput().getDisplayName());
        }*/
        if(!nNet.entity.canCraft(recipe)){
            return getCurrentValue();
        }
        return super.evaluate();
    }

    @Override
    public void execute() {
        if(this.getCurrentValue() <= .5f){
            return;
        }
       /* if(nNet.entity.getDebug()) {
            ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Attempting to Craft: " + recipe.getRegistryName() + " - " + recipe.getRecipeOutput().getDisplayName());
        }*/

       //ChaosCraft.logger.info("Attempting to Craft: " + recipe.getRegistryName().toString());
        ItemStack outputStack = null;

        outputStack = nNet.entity.craft(recipe);

        if(outputStack == null){
            throw new ChaosNetException("Something went wrong crafting: " + recipe.getType().toString() + " this should not be possible with the `evaluate` check above");
        }

        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.ITEM_CRAFTED);
        worldEvent.item = outputStack.getItem();
        nNet.entity.entityFitnessManager.test(worldEvent);
        //TODO: Move this to a GUI thing.
        //String message = nNet.entity.getCCNamespace() +" Crafted Recipe: " + recipe.getType().toString() + " - Item: " + worldEvent.item.getRegistryName();

    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        //recipeId = jsonObject.get("attributeId").toString();
        recipeId = jsonObject.get("attributeValue").toString();
        init();
    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.recipeId;
        return response;

    }
}
