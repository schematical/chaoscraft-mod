package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScanRecipeInstance {
    private IRecipe highScoreRecipe = null;
    private  float highScore = -9999;
    private IRecipe focusedRecipe = null;
    private float focusedRecipeScore = -9999;
    private  State state = State.Pending;
    private ClientOrgManager clientOrgManager;

    public ScanRecipeInstance(ClientOrgManager clientOrgManager){
        this.clientOrgManager = clientOrgManager;

    }
    public State getState(){
        return state;
    }
    public void setFocusedRecipeScore(float score){
        focusedRecipeScore = score;
    }
    public IRecipe getFocusedRecipe(){
        return focusedRecipe;
    }
    public void tickScanRecipes(){
        if(!state.equals(State.Pending)){
            throw new ChaosNetException("Invalid State: " + state);
        }
        ArrayList<IRecipe> recipes = clientOrgManager.getEntity().getAllCraftableRecipes();
        for (IRecipe recipe : recipes) {
            focusedRecipeScore = -9999;
            focusedRecipe = recipe;
            List<OutputNeuron> outputs = clientOrgManager.getNNet().evaluate(NeuralNet.EvalGroup.CRAFT);//Ideally the output neurons will set the score

            Iterator<OutputNeuron> iterator = outputs.iterator();

            while (iterator.hasNext()) {
                OutputNeuron outputNeuron = iterator.next();
                outputNeuron.execute();
            }
            if(
                highScoreRecipe == null ||
                focusedRecipeScore > highScore
            ){
                highScoreRecipe = focusedRecipe;
                highScore = focusedRecipeScore;
            }
        }
        state = State.Finished;

    }


    public IRecipe getHighScoreRecipe() {
        return highScoreRecipe;
    }
    public enum State{
        Pending,
        Finished
    }
}
