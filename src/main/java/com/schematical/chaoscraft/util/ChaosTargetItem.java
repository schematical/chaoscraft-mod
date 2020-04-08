package com.schematical.chaoscraft.util;

import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
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
            return "inventorySlot@" + inventorySlot;
        }else if(recipe != null){
            return "recipe@" + recipe.getId().toString();
        }else{
            return "null";
        }

    }
    public static ChaosTargetItem deserialize(World world, String payload) {
        ChaosTargetItem chaosTargetItem = null;
        String[] parts = payload.split("@");
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
                if(chaosTargetItem == null){
                    throw new ChaosNetException("Could not `deserialize` Invalid Recipe `" + parts[1] + "`");
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
    public String toString(){

        String message = "";
        if(getInventorySlot() != null){
            message += getInventorySlot();
        }else if(getRecipe() != null){
            message += getRecipe().getId().toString();
        }else{
            message += "null";
        }

        return message;
    }
    public String toString(OrgEntity orgEntity){

        String message = "";
        if(getInventorySlot() != null){
            message += getInventorySlot() + " - "  + orgEntity.getItemHandler().getStackInSlot(getInventorySlot()).getItem().getRegistryName().toString();
        }else if(getRecipe() != null){
            message += getRecipe().getId().toString();
        }else{
            message += "null";
        }

        return message;
    }
}
