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
    public static final double MAX_DISTANCE = 3d;
    protected AreaOfFocus areaOfFocus;
    @Override
    public void execute() {
        float reversedValue = Math.abs((this._lastValue * 2)-1);
        if(reversedValue < ChaosCraft.activationThreshold){
            return;
        }
        if(areaOfFocus == null){
            areaOfFocus = (AreaOfFocus)nNet.getBiology("AreaOfFocus1");
        }
        areaOfFocus.setDistance((float)(reversedValue * MAX_DISTANCE));


    }

}