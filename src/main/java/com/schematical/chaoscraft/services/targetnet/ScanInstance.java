package com.schematical.chaoscraft.services.targetnet;

import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScanInstance {
    private int maxLayerRange = 15;
    private int batchSize = 500;
    private ScanEdgeNode startNode;
    private ScanState scanState = ScanState.Finished;
    private int index = 0;
    private int layer = 0;

    private HashMap<String, Integer> counts = new HashMap<>();
    private ArrayList<ScanEdgeNode> previousLayer = new ArrayList<>();
    private ArrayList<ScanEdgeNode> currentLayer = new ArrayList<>();
    private ArrayList<BlockPos> edgePositions = new ArrayList<>();
    private ClientOrgManager clientOrgManager;
    private ArrayList<BlockPos> alreadyScannedBlockPositions = new ArrayList<>();

    public ScanInstance(ClientOrgManager clientOrgManager, BlockPos startPos){
        this.clientOrgManager = clientOrgManager;
        this.startNode = new ScanEdgeNode(
                startPos,
                null
        );
        this.currentLayer.add(startNode);
        this.scanState = ScanState.Ticking;

    }

    public ScanState getScanState(){
        return scanState;
    }
    public int getRange(){
        return maxLayerRange;
    }
    public  ArrayList<ScanEntry> tick(){
        if(!scanState.equals(ScanState.Ticking)){
            throw new ChaosNetException("Invalid `scanState`: " + scanState.toString());
        }
        ArrayList<ScanEntry> entries = new ArrayList<ScanEntry>();
        OrgEntity orgEntity = this.clientOrgManager.getEntity();
        BlockPos entityPosition = orgEntity.getPosition();
        int batchCount = 0;
        while(
            layer < maxLayerRange  &&
            batchCount < batchSize
        ) {
            //Later 0: Add a block for each Directions
            if (this.index >= this.currentLayer.size()) {
                //change layers
                this.iterateLayer();
                if(scanState.equals(ScanState.Finished)){
                    entries.addAll(scanEntities());
                    return entries;
                }
            }

            ScanEdgeNode scanEdgeNode = this.currentLayer.get(index);
            ScanEntry scanEntry = new ScanEntry();
            //BlockState blockState = orgEntity.getBlockState();
            //int light = orgEntity.world.getNeighborAwareLightSubtracted(blockPos, 0);
            //if(light > 0) {
            scanEntry.blockPos = scanEdgeNode.blockPos;
            scanEntry.atts = orgEntity.observableAttributeManager.Observe(scanEdgeNode.blockPos, orgEntity.world);
            if (!counts.containsKey(scanEntry.atts.resourceId)) {
                counts.put(scanEntry.atts.resourceId, 0);
            }
            counts.put(scanEntry.atts.resourceId, counts.get(scanEntry.atts.resourceId) + 1);
            entries.add(scanEntry);

            index += 1;
            batchCount += 1;

        }
        return entries;
    }
    private ArrayList<ScanEntry> scanEntities(){
        ArrayList<ScanEntry> entries = new ArrayList<ScanEntry>();
        AxisAlignedBB startBox = new AxisAlignedBB(startNode.blockPos);
        int range = (int)Math.round(maxLayerRange/2);
        AxisAlignedBB grownBox = startBox.grow(range, range, range);
        List<Entity> entities = getClientOrgManager().getEntity().world.getEntitiesWithinAABB(LivingEntity.class,  grownBox);
        entities.addAll(getClientOrgManager().getEntity().world.getEntitiesWithinAABB(ItemEntity.class,  grownBox));

        for (Entity entity : entities) {

            ScanEntry scanEntry = new ScanEntry();
            scanEntry.entity = entity;
            scanEntry.atts = getClientOrgManager().getEntity().observableAttributeManager.Observe(entity);
            if(!counts.containsKey(scanEntry.atts.resourceId)){
                counts.put(scanEntry.atts.resourceId, 0);
            }
            counts.put(scanEntry.atts.resourceId, counts.get(scanEntry.atts.resourceId) + 1);
            entries.add(scanEntry);

        }
        return entries;
    }
    private void iterateLayer() {
        this.layer += 1;
        if(this.layer >= maxLayerRange){
            for (ScanEdgeNode scanEdgeNode : this.currentLayer) {
                edgePositions.add(scanEdgeNode.blockPos);
            }

            scanState = ScanState.Finished;
            return;
        }
        this.previousLayer = (ArrayList<ScanEdgeNode>)this.currentLayer.clone();
        this.currentLayer.clear();

        for (ScanEdgeNode scanEdgeNode : this.previousLayer) {

            ArrayList<ScanEdgeNode> newNodes = scanEdgeNode.expand();
            if(newNodes == null){
                edgePositions.add(scanEdgeNode.blockPos);
            }else {
                for (ScanEdgeNode newNode : newNodes) {
                    BlockPos newBlockPos = newNode.blockPos;
                    if (!alreadyScannedBlockPositions.contains(newBlockPos)) {
                        alreadyScannedBlockPositions.add(newBlockPos);
                        this.currentLayer.add(
                                newNode
                        );
                    }
                }
            }
        }
        this.index = 0;//batchSize - addedBlockPositions.size();
        if(this.currentLayer.size() == 0){
            scanState = ScanState.Finished;
        }
    }

    public ArrayList<Direction> getDirections() {
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.DOWN);
        directions.add(Direction.UP);
        directions.add(Direction.NORTH);
        directions.add(Direction.SOUTH);
        directions.add(Direction.WEST);
        directions.add(Direction.EAST);
        return directions;
    }

    public ClientOrgManager getClientOrgManager() {
        return clientOrgManager;
    }

    public int getCount(String resourceId) {
        return counts.get(resourceId);
    }

    public void forceScanInterrupt() {
        scanState = ScanState.Finished;//TODO: Interupt?
    }

    public class ScanEdgeNode{

        protected BlockPos blockPos;
        private Direction direction;

        public ScanEdgeNode(BlockPos blockPos, Direction direction) {
            this.blockPos = blockPos;
            this.direction = direction;
        }

        public ArrayList<ScanEdgeNode> expand(){
            ArrayList<ScanEdgeNode> nodes = new ArrayList<ScanEdgeNode>();
            if(clientOrgManager.getEntity().world.getBlockState(blockPos).isSolid()){
                return null;
            }
            ArrayList<Direction> scanDirections = getDirections();
            if(direction != null) {
                scanDirections.remove(direction.getOpposite());
            }
            for (Direction _direction : scanDirections) {
                nodes.add(new ScanEdgeNode(
                        blockPos.offset(_direction),
                        _direction
                ));
            }
            return nodes;
        }

    }
    public ArrayList<BlockPos> getEdgePositions(){
        return edgePositions;
    }
    public enum ScanState{
        //Reset,
        Ticking,
        Finished
    }

}
