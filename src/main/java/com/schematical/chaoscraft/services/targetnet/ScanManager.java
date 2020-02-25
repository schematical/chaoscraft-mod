package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScanManager {
    private ScanEntry focusedScanEntry;
    private ClientOrgManager clientOrgManager;
    private ArrayList<ScanEntry> entries = new ArrayList<ScanEntry>();
    private int range = 40;

    private HashMap<String, Integer> counts = new HashMap<>();
    private NeuralNet getNNet(){
        return clientOrgManager.getEntity().getNNet();//TODO: Maybe get this from some type of NNet Collection
    }
    public ScanManager(ClientOrgManager clientOrgManager){
        this.clientOrgManager = clientOrgManager;
        //TODO: get range from biology

    }
    public void scan(){
        entries.clear();
        OrgEntity orgEntity = this.clientOrgManager.getEntity();
        BlockPos entityPosition = orgEntity.getPosition();
        //Iterate through all blocks in bounds
        for(int x = entityPosition.getX() - range; x < entityPosition.getX() + range; x++){
            for(int y = entityPosition.getY() - range; y < entityPosition.getY() + range; y++){
                for(int z = entityPosition.getZ() - range; z < entityPosition.getZ() + range; z++){
                    BlockPos blockPos = new BlockPos(x, y, z);
                    ScanEntry scanEntry = new ScanEntry();
                    scanEntry.blockPos = blockPos;
                    scanEntry.atts = orgEntity.observableAttributeManager.Observe(blockPos, orgEntity.world);
                    if(!counts.containsKey(scanEntry.atts.resourceId)){
                        counts.put(scanEntry.atts.resourceId, 0);
                    }
                    counts.put(scanEntry.atts.resourceId, counts.get(scanEntry.atts.resourceId) + 1);

                    entries.add(scanEntry);
                }
            }
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
        HashMap<String, ScanEntry> highestResults = new HashMap<String, ScanEntry>();
        for (ScanEntry entry : entries) {
            focusedScanEntry = entry;
            getNNet().evaluate(NeuralNet.EvalGroup.TARGET);//Ideally the output neurons will set the score

            //Sort them each by the score of their output neurons
            HashMap<String, Float> scores = focusedScanEntry.getScores();
            for (String targetSlotId : scores.keySet()) {
                if(!highestResults.containsKey(targetSlotId)){
                    highestResults.put(targetSlotId, focusedScanEntry);
                }else{
                    ScanEntry highScoreScanEntry = highestResults.get(targetSlotId);
                    if(highScoreScanEntry.getScore(targetSlotId) < scores.get(targetSlotId)){
                        highestResults.put(targetSlotId, focusedScanEntry);
                    }
                }
            }
        }

        //TODO: Iterate through the biology of the main NNet and set the targets

        for (String targetSlotId : highestResults.keySet()) {
            TargetSlot targetSlot = (TargetSlot )orgEntity.getNNet().getBiology(targetSlotId);
            ScanEntry scanEntry = highestResults.get(targetSlotId);
            if(scanEntry.entity != null) {
                targetSlot.setTarget(scanEntry.entity);
            }else  if(scanEntry.blockPos != null){
                targetSlot.setTarget(new Vec3d(
                        scanEntry.blockPos.getX(),
                        scanEntry.blockPos.getY(),
                        scanEntry.blockPos.getZ()
                ));
            }

        }

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
    public float getRange() {
        return range;
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
    }

}
