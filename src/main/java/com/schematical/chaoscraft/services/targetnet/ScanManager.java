package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.CCObserviableAttributeCollection;
import com.schematical.chaoscraft.ai.NeuralNet;
import com.schematical.chaoscraft.ai.OutputNeuron;
import com.schematical.chaoscraft.ai.biology.TargetSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.CCWorldEvent;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCClientActionPacket;
import com.schematical.chaoscraft.network.packets.CCClientOutputNeuronActionPacket;
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
    private ClientOrgManager clientOrgManager;
    private ArrayList<ScanEntry> entries = new ArrayList<ScanEntry>();
    public static final int range = 10;
    private int index = 0;
    private int BATCH_SIZE = 500;
    private int MAX_RANGE_INDEX;

    private HashMap<String, Integer> counts = new HashMap<>();
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
            //ChaosCraft.LOGGER.info(this.clientOrgManager.getCCNamespace() + "  " + x + ", " + y + ", " + z + " - " + index);
        /*for(int x = entityPosition.getX() - range; x < entityPosition.getX() + range; x++){
            for(int y = entityPosition.getY() - range; y < entityPosition.getY() + range; y++){
                for(int z = entityPosition.getZ() - range; z < entityPosition.getZ() + range; z++){*/
            BlockPos blockPos = new BlockPos(x, y, z);
            ScanEntry scanEntry = new ScanEntry();
            scanEntry.blockPos = blockPos;
            scanEntry.atts = orgEntity.observableAttributeManager.Observe(blockPos, orgEntity.world);
            if (!counts.containsKey(scanEntry.atts.resourceId)) {
                counts.put(scanEntry.atts.resourceId, 0);
            }
            counts.put(scanEntry.atts.resourceId, counts.get(scanEntry.atts.resourceId) + 1);
           /*if(scanEntry.atts.resourceId.contains("waypoint")){
                ChaosCraft.LOGGER.info(
                        orgEntity.getCCNamespace() + " Scanned: Waypoint"
                );
            }*/
            entries.add(scanEntry);
            batchCount += 1;
            index += 1;

    /*            }
            }
        }*/
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
        HashMap<String, ScanEntry> highestResults = new HashMap<String, ScanEntry>();
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
            CCClientActionPacket clientActionPacket = new CCClientActionPacket(orgEntity.getCCNamespace(), CCClientActionPacket.Action.SET_TARGET);
            clientActionPacket.setBiology(targetSlot);
            if(scanEntry.entity != null) {
                ChaosCraft.LOGGER.info(
                        orgEntity.getCCNamespace() + " targeted: " +
                                scanEntry.entity.getType().getRegistryName().toString()
                );
                if(scanEntry.entity.getType().getRegistryName().toString().equals("minecraft:bee")){
                    ChaosCraft.LOGGER.info(
                            orgEntity.getCCNamespace() + " targeted: Bee"
                    );
                }
                targetSlot.setTarget(scanEntry.entity);
                clientActionPacket.setEntity(scanEntry.entity);
                ChaosNetworkManager.sendToServer(clientActionPacket);
             /*
                CCWorldEvent worldEvent = new CCWorldEvent(CCWorldEvent.Type.TARGET_SELECTED);
                worldEvent.entity = scanEntry.entity;
                orgEntity.entityFitnessManager.test(worldEvent);*/
            }else  if(scanEntry.blockPos != null){
                String name = orgEntity.world.getBlockState(scanEntry.blockPos).getBlock().getRegistryName().toString();
                if(name.equals("chaoscraft:waypoint")){
                    ChaosCraft.LOGGER.info(
                            orgEntity.getCCNamespace() + " targeted: Waypoint"
                    );
                }
                if(
                    (
                        !name.equals("minecraft:void_air") &&
                        !name.equals("minecraft:air")
                    )
                ){
                    ChaosCraft.LOGGER.info(
                            orgEntity.getCCNamespace() + " targeted: " +
                            orgEntity.world.getBlockState(scanEntry.blockPos).getBlock().getRegistryName().toString()
                    );
                    clientActionPacket.setBlockPos(scanEntry.blockPos);
                    targetSlot.setTarget(scanEntry.blockPos);
                    ChaosNetworkManager.sendToServer(clientActionPacket);
                }

            }else{
                throw new ChaosNetException("Invalid ScanEntry: No blockPos nor Entity");
            }



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
    public float getRange() {
        return range;
    }
    public ScanState getState(){
        return scanState;
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
    }

}
