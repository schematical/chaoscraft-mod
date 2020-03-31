package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.ActionTargetSlot;
import com.schematical.chaoscraft.ai.biology.BiologyBase;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.util.ChaosTarget;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ScanManager {
    private ScanState scanState = ScanState.Finished;
    private ScanEntry focusedScanEntry;
    private ActionTargetSlot focusedAction;
    private float focusedActionScore = -9999;
    private ClientOrgManager clientOrgManager;
    private ArrayList<ScanEntry> entries = new ArrayList<ScanEntry>();
    public static final int range = 10;
    private int index = 0;
    private int BATCH_SIZE = 100;
    private int MAX_RANGE_INDEX;

    private HashMap<String, Integer> counts = new HashMap<>();
    public void setFocusedActionScore(float score){
        focusedActionScore = score;
    }
    private NeuralNet getNNet(){
        return clientOrgManager.getEntity().getNNet();//TODO: Maybe get this from some type of NNet Collection
    }
    public ScanManager(ClientOrgManager clientOrgManager){
        this.clientOrgManager = clientOrgManager;
        //TODO: get range from biology
        MAX_RANGE_INDEX = (int)Math.pow(range * 2, 3);
    }
    public void resetScan() {
        if(!scanState.equals(ScanState.Finished)){
            throw new ChaosNetException("Invalid `scanState`: " + scanState.toString());
        }
        entries.clear();
        index = 0;
        scanState = ScanState.Ticking;

    }
    public int getIndex(){
        return index;
    }
    public int getMaxRangeIndex(){
        return MAX_RANGE_INDEX;
    }
    public ScanState tickScan(){
        if(!scanState.equals(ScanState.Ticking)){
            throw new ChaosNetException("Invalid `scanState`: " + scanState.toString());
        }
        OrgEntity orgEntity = this.clientOrgManager.getEntity();
        BlockPos entityPosition = orgEntity.getPosition();
        //Iterate through all blocks in bounds
        int dividend = range * 2;
        int batchCount = 0;
        while(
            index < MAX_RANGE_INDEX  &&
            batchCount < BATCH_SIZE
        ) {

            int x = entityPosition.getX() - range + index % dividend;
            int z = entityPosition.getZ() - range + (int) Math.floor(index / dividend) % dividend;
            int y = entityPosition.getY() - range + (int) Math.floor(index / Math.pow(dividend, 2)) % dividend;

            BlockPos blockPos = new BlockPos(x, y, z);
            ScanEntry scanEntry = new ScanEntry();
            //BlockState blockState = orgEntity.getBlockState();
            //int light = orgEntity.world.getNeighborAwareLightSubtracted(blockPos, 0);
            //if(light > 0) {
                scanEntry.blockPos = blockPos;
                scanEntry.atts = orgEntity.observableAttributeManager.Observe(blockPos, orgEntity.world);
                if (!counts.containsKey(scanEntry.atts.resourceId)) {
                    counts.put(scanEntry.atts.resourceId, 0);
                }
                counts.put(scanEntry.atts.resourceId, counts.get(scanEntry.atts.resourceId) + 1);
                entries.add(scanEntry);

            //}
            batchCount += 1;
            index += 1;

        }
        if(index < MAX_RANGE_INDEX){
            return scanState;
        }
        //iterate through all entities in those bounds
        AxisAlignedBB grownBox = orgEntity.getBoundingBox().grow(range, range, range);
        List<Entity> entities =  orgEntity.world.getEntitiesWithinAABB(LivingEntity.class,  grownBox);
        entities.addAll(orgEntity.world.getEntitiesWithinAABB(ItemEntity.class,  grownBox));

        for (Entity entity : entities) {

            ScanEntry scanEntry = new ScanEntry();
            scanEntry.entity = entity;
            scanEntry.atts = orgEntity.observableAttributeManager.Observe(entity);
            if(!counts.containsKey(scanEntry.atts.resourceId)){
                counts.put(scanEntry.atts.resourceId, 0);
            }
            counts.put(scanEntry.atts.resourceId, counts.get(scanEntry.atts.resourceId) + 1);
            entries.add(scanEntry);

        }
        //HashMap<String, ScanEntry> highestResults = new HashMap<String, ScanEntry>();
        HashMap<String, ScanResult> highestResults = new HashMap<>();
        for (ScanEntry entry : entries) {
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
                    highestResults.put(targetSlotId, new ScanResult(targetSlotId, (biologyBase instanceof  ActionTargetSlot)));
                }
                ScanResult scanResult = highestResults.get(targetSlotId);
                scanResult.test(entry);

            }
        }

        //TODO: Iterate through the biology of the main NNet and set the targets
        float highestATSScore = -1000;
        ActionTargetSlot highestActionTargetSlot = null;
        for (String targetSlotId : highestResults.keySet()) {
            TargetSlot targetSlot = (TargetSlot )orgEntity.getNNet().getBiology(targetSlotId);
            ScanResult scanResult = highestResults.get(targetSlotId);



            //CCClientActionPacket clientActionPacket = new CCClientActionPacket(orgEntity.getCCNamespace(), CCClientActionPacket.Action.SET_TARGET);
            //clientActionPacket.setBiology(targetSlot);



            if(targetSlot instanceof  ActionTargetSlot){
                ActionTargetSlot actionTargetSlot = (ActionTargetSlot) targetSlot;

                for (ScanEntry topEntity : scanResult.getTopEntities()) {

                    if(topEntity.entity != null) {

                        targetSlot.setTarget(new ChaosTarget(topEntity.entity));

                        //clientActionPacket.setEntity(scanEntry.entity);
                        //ChaosNetworkManager.sendToServer(clientActionPacket);

                    }else if(topEntity.blockPos != null){
                        targetSlot.setTarget(new ChaosTarget(topEntity.blockPos));

                        //clientActionPacket.setBlockPos(scanEntry.blockPos);
                        //ChaosNetworkManager.sendToServer(clientActionPacket);

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
                if(scanResult.getTopEntities().size() > 0){
                    throw new ChaosNetException("`scanResult.getTopEntities().size()` should be zero in this scenerio");
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

        scanState = ScanState.Finished;
        return scanState;

    }
    public ScanEntry getFocusedScanEntry(){
        return focusedScanEntry;
    }
    public int getCountOfFocusedScanEntity(){
        if(focusedScanEntry == null){
            return -1;
        }
        return counts.get(focusedScanEntry.atts.resourceId);
    }
    public int getCountOfScanEntry(String resourceId){
        return counts.get(resourceId);
    }
    public float getRange() {
        return range;
    }
    public ScanState getState(){
        return scanState;
    }

    public void forceEndScan() {
       index = MAX_RANGE_INDEX;
    }

    public enum ScanState{
        //Reset,
        Ticking,
        Finished
    }

    public class ScanEntry {
        public Entity entity;
        public BlockPos blockPos;
        public CCObserviableAttributeCollection atts;
        private HashMap<String, Float> scores = new HashMap<String, Float>();
        public void setScore(String targetSlotId, float score){
            this.scores.put(targetSlotId, score);
        }
        public float getScore(String targetSlotId){
            return this.scores.get(targetSlotId);
        }
        public HashMap<String, Float> getScores(){
            return this.scores;
        }

        public Vec3d getPosition() {
            if(entity != null){
                return entity.getPositionVec();
            }
            if(blockPos != null) {
                return new Vec3d(
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ()
                );
            }
            return null;
        }
        public BlockPos getTargetBlockPos() {
            if(entity != null){
                return new BlockPos(
                    Math.round(entity.getEyePosition(1).getX()),
                    Math.round(entity.getEyePosition(1).getY()),
                    Math.round(entity.getEyePosition(1).getZ())
                );
            }
            if(blockPos != null) {
                return blockPos;
            }
            return null;
        }
    }
    public class ScanResult{
        private int max = 5;
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
