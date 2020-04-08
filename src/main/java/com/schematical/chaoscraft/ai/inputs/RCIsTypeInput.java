package com.schematical.chaoscraft.ai.inputs;

import com.schematical.chaoscraft.ai.InputNeuron;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.services.targetnet.ScanEntry;
import com.schematical.chaoscraft.services.targetnet.ScanManager;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.json.simple.JSONObject;

/**
 * Created by user1a on 12/8/18.
 */
public class RCIsTypeInput extends InputNeuron {


    private String recipeId;
    private IRecipe recipe;

    @Override
    public float evaluate(){
        ScanManager scanManager =  ((OrgEntity)this.getEntity()).getClientOrgManager().getScanManager();
        ScanEntry scanEntry = scanManager.getScanItemInstance().getFocusedScanEntry();



        if(
            scanEntry.recipe != null &&
            scanEntry.recipe.equals(recipe)
        ) {
            setCurrentValue(1);
        }


        return getCurrentValue();
    }
    @Override
    public void parseData(JSONObject jsonObject){
        super.parseData(jsonObject);
        //recipeId = jsonObject.get("attributeId").toString();
        this.recipeId = jsonObject.get("attributeValue").toString();
        init();
    }
    public String toLongString(){
        String response = super.toLongString();
        response += " " + this.recipeId;
        return response;

    }
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


}
