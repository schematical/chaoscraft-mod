package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ai.OutputNeuron;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
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
            String key = resourceLocation.getResourceDomain() + ":" + resourceLocation.getResourcePath();
            if(recipeId == key){
                recipe = irecipe;
            }


        }

    }
    @Override
    public void execute() {
        //Check to see if they have the items in inventory for that


    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        recipeId = jsonObject.get("recipeId").toString();


    }
}
