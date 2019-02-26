package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.events.CCWorldEventType;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.items.ItemStackHandler;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
            String key = resourceLocation.getResourceDomain() + ":" + resourceLocation.getResourcePath();
            if(recipeId.equals(key)){
                recipe = irecipe;
            }


        }
        if(recipe == null){
            throw new ChaosNetException("Cant find Recipe: " + recipeId);
        }

    }
    @Override
    public void execute() {
        if(nNet.entity.getDebug()) {
            //ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Attempting to Craft: " + recipe.getRegistryName() + " - " + recipe.getRecipeOutput().getDisplayName());
        }
        if(!nNet.entity.canCraft(recipe)){
            return;
        }
        int emptySlot = nNet.entity.getEmptyInventorySlot();

        //I guess we have the ingredients
        ItemStack outputStack = recipe.getRecipeOutput();
        if(nNet.entity.getDebug()) {
            ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Crafted: " + outputStack.getDisplayName());
        }

        CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEventType.CRAFT);
        worldEvent.item = outputStack.getItem();
        nNet.entity.entityFitnessManager.test(worldEvent);
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
