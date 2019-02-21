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
        //Check to see if they have the items in inventory for that
        ItemStackHandler itemStackHandler = nNet.entity.getItemStack();
        int slots = itemStackHandler.getSlots();
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        int emptySlot = -1;
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        List<Integer> usedSlots = new ArrayList<Integer>();
        for(Ingredient ingredient : ingredients){
            boolean containsItem = false;
            for(int i = 0; i < slots; i++) {
                ItemStack itemStack = itemStackHandler.getStackInSlot(i);
                if(itemStack.isEmpty()){
                    emptySlot = i;
                }else {
                    boolean itWorks = ingredient.apply(itemStack);
                    if (itWorks) {
                        containsItem = true;
                        usedSlots.add(i);
                    }
                }
            }
            if(!containsItem){
                return;
            }
        }
        if(usedSlots.size() == 0){
            //Looks like a fake recipe kinda
            return;
        }
        if(emptySlot == -1){
            //if(itemStackHandler.getSlots() > slots){
            emptySlot = slots + 1;
            //}
        }
        //I guess we have the ingredients
        ItemStack outputStack = recipe.getRecipeOutput();
        if(nNet.entity.getDebug()) {
            ChaosCraft.logger.info(nNet.entity.getCCNamespace() + " Crafted: " + outputStack.getDisplayName());
        }
        for(Integer slot: usedSlots){
            itemStackHandler.extractItem(slot, 1, false);
        }
        if(emptySlot != -1) {
            itemStackHandler.insertItem(emptySlot, outputStack, false);
        }else{
            throw new ChaosNetException("TODO: Code how to drop this when stack is full");
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
}
