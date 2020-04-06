package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.ai.action.CraftAction;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ScanManager {

    private ScanEntry focusedScanEntry;
    private ActionTargetSlot focusedActionTargetSlot;
    private float focusedActionScore = -9999;
    private ClientOrgManager clientOrgManager;
    private HashMap<String, ScanResult> highestResults = new HashMap<>();
    private ScanInstance scanInstance = null;
    private  ScanRecipeInstance scanRecipeInstance = null;
    private ScanItemInstance scanItemInstance;

    public void setFocusedActionScore(float score){
        focusedActionScore = score;
    }

    public ScanManager(ClientOrgManager clientOrgManager){
        this.clientOrgManager = clientOrgManager;
        //TODO: get range from biology

    }
    public void resetScan() {
        scanItemInstance = new ScanItemInstance(clientOrgManager);
        scanRecipeInstance = new ScanRecipeInstance(clientOrgManager);
        scanInstance = new ScanInstance(clientOrgManager, clientOrgManager.getEntity().getPosition());
        highestResults.clear();
    }

    public ScanInstance.ScanState tickScan(){

        if(!scanInstance.getScanState().equals(ScanInstance.ScanState.Ticking)){
            throw new ChaosNetException("Invalid `scanState`: " + scanInstance.getScanState().toString());
        }
        if(scanRecipeInstance.getState().equals(ScanRecipeInstance.State.Pending)){
            scanRecipeInstance.tickScanRecipes();
        }
        if(scanItemInstance.getState().equals(ScanRecipeInstance.State.Pending)){
            scanItemInstance.tickScanItems();
        }
        ArrayList<ScanEntry> newEntries = scanInstance.tick();
        OrgEntity orgEntity = this.clientOrgManager.getEntity();



        for (ScanEntry scanEntry : newEntries) {
            focusedScanEntry = scanEntry;

            List<OutputNeuron> outputs = orgEntity.getNNet().evaluate(NeuralNet.EvalGroup.TARGET);//Ideally the output neurons will set the score

            Iterator<OutputNeuron> iterator = outputs.iterator();


            while (iterator.hasNext()) {
                OutputNeuron outputNeuron = iterator.next();
                outputNeuron.execute();
            }
            focusedScanEntry = null;
            //Sort them each by the score of their output neurons
            HashMap<String, Float> scores = scanEntry.getScores();
            for (String targetSlotId : scores.keySet()) {
                BiologyBase biologyBase = orgEntity.getNNet().biology.get(targetSlotId);
                if(!highestResults.containsKey(targetSlotId)){
                    //BiologyBase biologyBase = getNNet().biology.get(targetSlotId);

                    highestResults.put(targetSlotId, new ScanResult(targetSlotId, !(biologyBase instanceof  ActionTargetSlot)));
                }
                if(biologyBase instanceof ActionTargetSlot){
                    ActionTargetSlot actionTargetSlot = (ActionTargetSlot) biologyBase;
                    float currScore = scanEntry.getScore(actionTargetSlot.id);
                    if(
                        currScore != -1f &&
                        !actionTargetSlot.validatePotentialTarget(orgEntity, scanEntry.getChaosTarget())
                    ) {
                        throw new ChaosNetException("Invalid `scanEntry` made it to the top with score of: " + currScore + " - ActionTargetSlot: " + actionTargetSlot.id);
                    }
                }
                ScanResult scanResult = highestResults.get(targetSlotId);
                scanResult.test(scanEntry);

            }
        }
        if(scanInstance.getScanState().equals(ScanInstance.ScanState.Ticking)){
            return scanInstance.getScanState();
        }


        //TODO: Iterate through the biology of the main NNet and set the targets
        float highestATSScore = -1000;
        ActionTargetSlot highestActionTargetSlot = null;
        ScanEntry highestActionScanEntry = null;
        for (String targetSlotId : highestResults.keySet()) {

            TargetSlot targetSlot = (TargetSlot)orgEntity.getNNet().getBiology(targetSlotId);
            ScanResult scanResult = highestResults.get(targetSlotId);

            if(targetSlot instanceof  ActionTargetSlot){
                ActionTargetSlot actionTargetSlot = (ActionTargetSlot) targetSlot;
                ChaosTarget originalTarget = actionTargetSlot.getTarget();
                for (ScanEntry topScanEntity : scanResult.getTopEntities()) {




                    if(!actionTargetSlot.validatePotentialTarget(orgEntity, topScanEntity.getChaosTarget())) {
                        /*throw new ChaosNetException*/
                        ChaosCraft.LOGGER.error(orgEntity.getCCNamespace() + " - Invalid `topScanEntity` made it to the top with score of: " + topScanEntity.getScore(actionTargetSlot.id) + " - ActionTargetSlot: " + actionTargetSlot.id);
                    }else {
                        focusedActionScore = -9999;
                        this.focusedActionTargetSlot = actionTargetSlot;
                        this.focusedActionTargetSlot.setTarget(topScanEntity.getChaosTarget());

                        if(!clientOrgManager.getActionBuffer().hasExecutedRecently(this.focusedActionTargetSlot.createAction(), 5)) {

                            List<OutputNeuron> outputs = orgEntity.getNNet().evaluate(NeuralNet.EvalGroup.ACTION);//Ideally the output neurons will set the score

                            Iterator<OutputNeuron> iterator = outputs.iterator();

                            while (iterator.hasNext()) {
                                OutputNeuron outputNeuron = iterator.next();
                                outputNeuron.execute();
                            }

                            if (focusedActionScore > highestATSScore) {
                                highestATSScore = focusedActionScore;
                                highestActionTargetSlot = actionTargetSlot;
                                highestActionScanEntry = topScanEntity;
                            }
                        }
                    }


                }
                actionTargetSlot.setTarget(originalTarget);
            }else{
                if(scanResult.getTopEntities().size() > 1){
                    throw new ChaosNetException("`scanResult.getTopEntities().size()` should be zero in this scenario: " + scanResult.getTopEntities().size());
                } else if (scanResult.getTopEntities().size() == 1) {

                    ScanEntry scanEntry = scanResult.getTopEntities().get(0);
                    float currScore = scanEntry.getScore(targetSlotId);
                    targetSlot.setTargetScore(currScore);
                    targetSlot.setTarget(scanEntry.getChaosTarget());
                }else{
                    //No valid targets were found
                }

            }

        }
        //Add an action locally





        if(highestActionTargetSlot != null) {
            highestActionTargetSlot.setTarget(highestActionScanEntry.getChaosTarget());
            if(!highestActionTargetSlot.isValid()){
                throw new ChaosNetException("Something went really really wrong. Is `highestActionScanEntry` being modified in the wrong spot");
            }
            ActionBase actionBase = highestActionTargetSlot.createAction();
            if(actionBase instanceof CraftAction){
                ((CraftAction) actionBase).setRecipe(scanRecipeInstance.getHighScoreRecipe());
            }
            this.clientOrgManager.getActionBuffer().addAction(
                    actionBase
            ).sync();
        }


        return scanInstance.getScanState();

    }
    public ScanEntry getFocusedScanEntry(){
        return focusedScanEntry;
    }
    public int getCountOfFocusedScanEntity(){
        if(focusedScanEntry == null){
            return -1;
        }
        return scanInstance.getCount(focusedScanEntry.atts.resourceId);
    }
    public int getCountOfScanEntry(String resourceId){
        return scanInstance.getCount(resourceId);
    }


    public void forceEndScan() {
        scanInstance.forceScanInterrupt();
    }

    public ActionTargetSlot getFocusedActionTargetSlot() {
        return focusedActionTargetSlot;
    }

    public ScanInstance getScanInstance() {
        return scanInstance;
    }

    public ScanRecipeInstance getRecipeScanInstance() {
        return scanRecipeInstance;
    }

    public ScanItemInstance getScanItemInstance() {
        return scanItemInstance;
    }





}
