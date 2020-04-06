package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScanItemInstance {

    private  float highScore = -9999;

    private ItemStack focusedItemStack = null;
    private float focusedScore = -9999;
    private int highScoreSlot = -1;
    private  State state = State.Pending;
    private ClientOrgManager clientOrgManager;

    public ScanItemInstance(ClientOrgManager clientOrgManager){
        this.clientOrgManager = clientOrgManager;

    }
    public State getState(){
        return state;
    }
    public void setFocusedScore(float score){
        focusedScore = score;
    }
    public ItemStack getFocusedItemStack(){
        return focusedItemStack;
    }

    public void tickScanItems(){
        if(!state.equals(State.Pending)){
            throw new ChaosNetException("Invalid State: " + state);
        }
        ItemStackHandler itemStackHandler = clientOrgManager.getEntity().getItemHandler();
       for(int i = 0; i < itemStackHandler.getSlots(); i++){
            focusedScore = -9999;
            focusedItemStack = itemStackHandler.getStackInSlot(i);
            List<OutputNeuron> outputs = clientOrgManager.getNNet().evaluate(NeuralNet.EvalGroup.EQUIP);

            Iterator<OutputNeuron> iterator = outputs.iterator();

            while (iterator.hasNext()) {
                OutputNeuron outputNeuron = iterator.next();
                outputNeuron.execute();
            }
            if(
                focusedScore > highScore
            ){
                highScoreSlot = i;
                highScore = focusedScore;
            }else if(
                focusedScore == highScore
            ){
                if(Math.random() > 0.5f) {
                    highScoreSlot = i;
                    highScore = focusedScore;
                }
            }
        }
        state = State.Finished;

    }

    public int getHighScoreSlot() {
        return highScoreSlot;
    }
    public enum State{
        Pending,
        Finished
    }
}
