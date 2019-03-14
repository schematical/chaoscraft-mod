package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;


/**
 * Created by user1a on 12/10/18.
 */
public class CraftOutput extends OutputNeuron {
    private String recipeId;
    protected IRecipe recipe;
    public void init(){

        for (IRecipe irecipe : CraftingManager.REGISTRY)
        {

            ResourceLocation resourceLocation = irecipe.getRegistryName();
            String key = resourceLocation.getNamespace() + ":" + resourceLocation.getPath();
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
        if(hasBeenEvaluated){
            return _lastValue;
        }
        if(nNet.entity.getDebug()) {
            ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Checking to see if they can Craft: " + recipe.getRegistryName() + " - " + recipe.getRecipeOutput().getDisplayName());
        }
        if(!nNet.entity.canCraft(recipe)){
            return _lastValue;
        }
        return super.evaluate();
    }
    @Override
    public void execute() {
        if(this._lastValue <= .5){
            return;
        }
        if(nNet.entity.getDebug()) {
            ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Attempting to Craft: " + recipe.getRegistryName() + " - " + recipe.getRecipeOutput().getDisplayName());
        }

       ChaosCraft.logger.info("Attempting to Craft: " + recipe.getRegistryName().toString());
        ItemStack outputStack = null;
        if(recipe instanceof ShapedRecipes) {
            outputStack = nNet.entity.craft((ShapedRecipes) recipe);
        }else{
            ChaosCraft.logger.info("Found a non-shaped recipe: " + recipe.getRegistryName().toString());
        }
        if(outputStack == null){
            throw new ChaosNetException("Something went wrong crafting: " + recipe.getRegistryName().toString() + " this should not be possible with the `evaluate` check above");
        }

        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.CRAFT);
        worldEvent.item = outputStack.getItem();
        nNet.entity.entityFitnessManager.test(worldEvent);
        //TODO: Move this to a GUI thing.
        String message = nNet.entity.getCCNamespace() +" Crafted Recipe: " + recipe.getRegistryName().toString() + " - Item: " + worldEvent.item.getRegistryName();
        ChaosCraft.chat(message);
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        recipeId = jsonObject.get("recipeId").toString();

        init();
    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.recipeId;
        return response;

    }
}
