package com.schematical.chaoscraft.util;

import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChaosTargetItem {
    private IRecipe recipe;
    private Integer inventorySlot;

    public ChaosTargetItem(IRecipe recipe) {
        this.recipe = recipe;
    }
    public ChaosTargetItem(Integer inventorySlot) {
        this.inventorySlot = inventorySlot;
    }

    public String getSerializedString() {
        if(inventorySlot != null){
            return "inventorySlot:" + inventorySlot;
        }else if(recipe != null){
            return "recipe:" + recipe.getId().toString();
        }else{
            return "null";
        }

    }
    public static ChaosTargetItem deserialize(World world, String payload) {
        ChaosTargetItem chaosTargetItem = null;
        String[] parts = payload.split(":");
        switch(parts[0]){
            case("inventorySlot"):
                chaosTargetItem =  new ChaosTargetItem(
                        Integer.parseInt(parts[1])
                );
                break;
            case("recipe"):

                for (IRecipe irecipe : world.getRecipeManager().getRecipes())
                {

                    String key = irecipe.getId().getNamespace() + ":" + irecipe.getId().getPath();
                    if(parts[1].equals(key)){
                        chaosTargetItem =  new ChaosTargetItem(
                                irecipe
                        );
                    }
                }
                break;
            case("null"):
                throw new ChaosNetException("TODO: Figure out what to do with this");
            default:
                throw new ChaosNetException("Invalid TargetType: " + parts[0]);
        }
        return chaosTargetItem;

    }
    public boolean equals(Object target){
        if(!(target instanceof  ChaosTargetItem)){
            return false;
        }
        ChaosTargetItem chaosTargetItem = (ChaosTargetItem)target;
        if(
            inventorySlot != null &&
            chaosTargetItem.getInventorySlot() != null
        ) {
            if (this.inventorySlot.equals(chaosTargetItem.getInventorySlot())) {
                return true;
            }
        }else if(
            this.recipe != null &&
            chaosTargetItem.getRecipe() != null
        ) {
            if (this.recipe.equals(chaosTargetItem.getRecipe())) {
                return true;
            }
        }
        return false;
    }

    public IRecipe getRecipe() {
        return recipe;
    }

    public Integer getInventorySlot() {
        return this.inventorySlot;
    }
}
