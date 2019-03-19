package com.schematical.chaoscraft.ai.outputs;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.AreaOfFocus;
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
public class FocusDistanceOutput extends OutputNeuron {

    protected AreaOfFocus areaOfFocus;
    @Override
    public void execute() {

        if(areaOfFocus == null){
            areaOfFocus = (AreaOfFocus)nNet.getBiology("AreaOfFocus_0");
        }
        areaOfFocus.setDistance((float)(this._lastValue * areaOfFocus.maxFocusDistance));


    }

}