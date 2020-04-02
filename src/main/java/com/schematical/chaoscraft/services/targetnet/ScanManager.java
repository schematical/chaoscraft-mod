package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ScanManager {

    private ScanEntry focusedScanEntry;
    private ActionTargetSlot focusedAction;
    private float focusedActionScore = -9999;
    private ClientOrgManager clientOrgManager;
    private HashMap<String, ScanResult> highestResults = new HashMap<>();
    private ScanInstance scanInstance = null;
    public void setFocusedActionScore(float score){
        focusedActionScore = score;
    }
    private NeuralNet getNNet(){
        return clientOrgManager.getEntity().getNNet();//TODO: Maybe get this from some type of NNet Collection
    }
    public ScanManager(ClientOrgManager clientOrgManager){
        this.clientOrgManager = clientOrgManager;
        //TODO: get range from biology

    }
    public void resetScan() {
      scanInstance = new ScanInstance(clientOrgManager, clientOrgManager.getEntity().getPosition());
        highestResults.clear();
    }

    public ScanInstance.ScanState tickScan(){
        if(!scanInstance.getScanState().equals(ScanInstance.ScanState.Ticking)){
            throw new ChaosNetException("Invalid `scanState`: " + scanInstance.getScanState().toString());
        }
        ArrayList<ScanEntry> newEntries = scanInstance.tick();
        OrgEntity orgEntity = this.clientOrgManager.getEntity();

        //Iterate through all blocks in bounds

        //HashMap<String, ScanEntry> highestResults = new HashMap<String, ScanEntry>();

        for (ScanEntry entry : newEntries) {
            focusedScanEntry = entry;


            List<OutputNeuron> outputs = getNNet().evaluate(NeuralNet.EvalGroup.TARGET);//Ideally the output neurons will set the score

            Iterator<OutputNeuron> iterator = outputs.iterator();

            while (iterator.hasNext()) {
                OutputNeuron outputNeuron = iterator.next();
                outputNeuron.execute();
            }
            //Sort them each by the score of their output neurons
            HashMap<String, Float> scores = focusedScanEntry.getScores();
            for (String targetSlotId : scores.keySet()) {
                if(!highestResults.containsKey(targetSlotId)){
                    BiologyBase biologyBase = getNNet().biology.get(targetSlotId);
                    highestResults.put(targetSlotId, new ScanResult(targetSlotId, !(biologyBase instanceof  ActionTargetSlot)));
                }
                ScanResult scanResult = highestResults.get(targetSlotId);
                scanResult.test(entry);

            }
        }
        if(scanInstance.getScanState().equals(ScanInstance.ScanState.Ticking)){
            return scanInstance.getScanState();
        }

        //TODO: Iterate through the biology of the main NNet and set the targets
        float highestATSScore = -1000;
        ActionTargetSlot highestActionTargetSlot = null;
        for (String targetSlotId : highestResults.keySet()) {
            TargetSlot targetSlot = (TargetSlot )orgEntity.getNNet().getBiology(targetSlotId);
            ScanResult scanResult = highestResults.get(targetSlotId);

            if(targetSlot instanceof  ActionTargetSlot){
                ActionTargetSlot actionTargetSlot = (ActionTargetSlot) targetSlot;

                for (ScanEntry topEntity : scanResult.getTopEntities()) {

                    if(topEntity.entity != null) {
                        targetSlot.setTarget(new ChaosTarget(topEntity.entity));

                    }else if(topEntity.blockPos != null){
                        targetSlot.setTarget(new ChaosTarget(topEntity.blockPos));

                    }else{
                        throw new ChaosNetException("Invalid ScanEntry: No blockPos nor Entity");
                    }
                    if(actionTargetSlot.isValid()) {
                        focusedActionScore = -9999;
                        focusedAction = actionTargetSlot;
                        List<OutputNeuron> outputs = getNNet().evaluate(NeuralNet.EvalGroup.ACTION);//Ideally the output neurons will set the score

                        Iterator<OutputNeuron> iterator = outputs.iterator();

                        while (iterator.hasNext()) {
                            OutputNeuron outputNeuron = iterator.next();
                            outputNeuron.execute();
                        }

                        if(focusedActionScore > highestATSScore) {
                            highestATSScore = focusedActionScore;
                            highestActionTargetSlot = actionTargetSlot;
                        }
                    }

                }
            }else{
                if(scanResult.getTopEntities().size() > 1){
                    throw new ChaosNetException("`scanResult.getTopEntities().size()` should be zero in this scenario: " + scanResult.getTopEntities().size());
                }
                ScanEntry scanEntry = scanResult.getTopEntities().get(0);
                float currScore = scanEntry.getScore(targetSlotId);
                targetSlot.setTargetScore(currScore);
                if(scanEntry.entity != null) {

                    targetSlot.setTarget(new ChaosTarget(scanEntry.entity));

                    //clientActionPacket.setEntity(scanEntry.entity);
                    //ChaosNetworkManager.sendToServer(clientActionPacket);

                }else if(scanEntry.blockPos != null){
                    targetSlot.setTarget(new ChaosTarget(scanEntry.blockPos));

                    //clientActionPacket.setBlockPos(scanEntry.blockPos);
                    //ChaosNetworkManager.sendToServer(clientActionPacket);

                }else{
                    throw new ChaosNetException("Invalid ScanEntry: No blockPos nor Entity");
                }

            }

        }
        //Add an action locally





        if(highestActionTargetSlot != null) {
            this.clientOrgManager.getActionBuffer().addAction(
                    highestActionTargetSlot.createAction()
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

    public ActionTargetSlot getFocusedAction() {
        return focusedAction;
    }

    public ScanInstance getScanInstance() {
        return scanInstance;
    }

    public class ScanResult{
        private int max = 10;
        private String targetSlotId;
        private float lowestScore = 99999;
        private ArrayList<ScanEntry> scanEntries = new ArrayList<>();

        public ScanResult(String targetSlotId){
            this.targetSlotId = targetSlotId;
        }
        public ScanResult(String targetSlotId, boolean single){
            this.targetSlotId = targetSlotId;
            if(single) {
                this.max = 1;
            }
        }
        public void test(ScanEntry scanEntry){


            if(scanEntries.size() < max){
                scanEntries.add(scanEntry);
                if(scanEntry.getScore(targetSlotId) < lowestScore){
                    lowestScore = scanEntry.getScore(targetSlotId);
                }
                return;
            }
            if(max == 1){
                scanEntries.clear();
                scanEntries.add(scanEntry);
                lowestScore = scanEntry.getScore(targetSlotId);
                return;
            }
            if(scanEntry.getScore(targetSlotId) > lowestScore){
                Iterator<ScanEntry> iterator = scanEntries.iterator();
                boolean removed = false;
                while(
                        iterator.hasNext() &&
                        !removed
                ){
                    ScanEntry removeScanEntry = iterator.next();
                    if(removeScanEntry.getScore(targetSlotId) == lowestScore){
                        iterator.remove();
                        removed = true;
                    }
                }
                if(!removed){
                    throw new ChaosNetException("Your math is off ");
                }
                scanEntries.add(scanEntry);
                lowestScore = scanEntry.getScore(targetSlotId);
            }
        }

        public ArrayList<ScanEntry> getTopEntities() {
            return scanEntries;
        }
    }

}
