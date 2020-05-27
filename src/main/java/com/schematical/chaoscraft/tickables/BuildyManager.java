package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.BaseOrgManager;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.ai.action.PlaceBlockAction;
import com.schematical.chaoscraft.ai.memory.BlockStateMemoryBufferSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.client.gui.CCGUIHelper;
import com.schematical.chaoscraft.client.iRenderWorldLastEvent;
import com.schematical.chaoscraft.entities.AlteredBlockInfo;
import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BuildyManager extends BaseChaosEventListener implements iRenderWorldLastEvent {
    public int myScore = 0;//SERVER SIDE

    private ClientOrgManager clientOrgManager;
    public ArrayList<ArrayList<BlockPos>> blockClusters = new ArrayList<>();
    public HashMap<Integer, Color> colorMap = new HashMap<Integer, Color>();
    private Color color;

    public void onClientTick(ClientOrgManager baseOrgManager) {
        if (clientOrgManager == null) {
            clientOrgManager = baseOrgManager;
            ChaosCraft.getClient().addRenderListener(this);
            this.color =  new Color(
                            (int) Math.round(Math.random() * 255),
                            (int) Math.round(Math.random() * 255),
                            (int) Math.round(Math.random() * 255)

                    );
        }
    }
    @Override
    public void onServerActionComplete(ServerOrgManager serverOrgManager, ActionBase actionBase) {
        if (!(actionBase instanceof PlaceBlockAction)) {
            return;
        }

        ArrayList<BlockPos> myBlocks = new ArrayList<>();
        ArrayList<String> blockStates = new ArrayList<>();
        for (AlteredBlockInfo alteredBlockInfo : ChaosCraft.getServer().alteredBlocks.values()) {
            if (alteredBlockInfo.serverOrgManager.equals(serverOrgManager)) {

                BlockState blockState = serverOrgManager.getEntity().world.getBlockState(alteredBlockInfo.blockPos);
                if(blockState.isSolid()) {
                    myBlocks.add(alteredBlockInfo.blockPos);
                    String blockType = blockState.getBlock().getRegistryName().toString();
                    if (!blockStates.contains(blockType)) {
                        blockStates.add(blockType);
                    }
                }
            }
        }
        ArrayList<ArrayList<BlockPos>> blockClusters = clusterBlocks(myBlocks, serverOrgManager);
        int newTotalScore = 0;
        for (ArrayList<BlockPos> blockCluster : blockClusters) {
            int i = 0;
            int clusterScore = 0;
            for (BlockPos blockPos : blockCluster) {
                i += 1;
                clusterScore += i;
            }
            newTotalScore += clusterScore;
        }
        newTotalScore = newTotalScore  * blockStates.size();
        if(blockStates.size() > 1){
            ChaosCraft.LOGGER.info("blockStates.size(): " + blockStates.size());
        }
        int scoreToSend = newTotalScore - myScore;
        myScore = newTotalScore;
        int newLife =  newTotalScore;
        /*if(serverOrgManager.getMaxLife() + newLife > 120){
            newLife =
        }
        int ageLeftToLive = (serverOrgManager.getMaxLife() - serverOrgManager.getAgeSeconds());
        serverOrgManager.adjustMaxLife();*/

        CCServerScoreEventPacket serverScoreEventPacket = new CCServerScoreEventPacket(
                serverOrgManager.getCCNamespace(),
                scoreToSend,
                scoreToSend,
                "buildy",
                1,
                (int) (serverOrgManager.getEntity().world.getGameTime() + ((serverOrgManager.getMaxLife() - serverOrgManager.getAgeSeconds()) * 20)),
                0
        );
        serverOrgManager.getEntity().addOrgEvent(new OrgEvent(new EntityFitnessScoreEvent(null, myBlocks.size(), null)));


        ChaosNetworkManager.sendTo(serverScoreEventPacket, serverOrgManager.getServerPlayerEntity());

    }
    public ArrayList<ArrayList<BlockPos>> clusterBlocks(ArrayList<BlockPos> myBlocks, BaseOrgManager orgManager){
        //blockClusters.clear();
        ArrayList<BlockPos> searchMe = new ArrayList<BlockPos>();

        //Get all placed blocks
        int connectionBonus = 1;
        for (BlockPos _blockPos : myBlocks) {


            searchMe.add(_blockPos);


            int safteyCatch = 0;
            ArrayList<BlockPos> selectdBlockCluster = null;
            ArrayList<BlockPos> alreadySearched = new ArrayList<BlockPos>();
            while(
                 searchMe.size() > 0
            ) {
                safteyCatch += 1;
                if(safteyCatch > 100){
                    throw new ChaosNetException("Safty Catch Hit BuildyManager");
                }
                ArrayList<BlockPos> searchNext = new ArrayList<>();
                Iterator<BlockPos> iterator = searchMe.iterator();
                while(iterator.hasNext()) {
                    BlockPos targetBlockPos = iterator.next();

                   if(selectdBlockCluster == null) {
                       Iterator<ArrayList<BlockPos>> iterator1 = blockClusters.iterator();
                       while(iterator1.hasNext()){
                           ArrayList<BlockPos> blockCluster  = iterator1.next();

                           if (
                               !blockCluster.equals(selectdBlockCluster) &&
                               blockCluster.contains(targetBlockPos)
                           ) {
                               selectdBlockCluster = blockCluster;
                           }

                       }
                       if (selectdBlockCluster == null) {
                           selectdBlockCluster = new ArrayList<>();
                           blockClusters.add(selectdBlockCluster);
                       }
                   }
                   if(!selectdBlockCluster.contains(targetBlockPos)){
                       selectdBlockCluster.add(targetBlockPos);
                   }
                    for (Direction direction : com.schematical.chaoscraft.Enum.getDirections()) {
                        BlockPos newTargetBlockPos = targetBlockPos.offset(direction);
                        if(
                            myBlocks.contains(newTargetBlockPos) &&
                            !alreadySearched.contains(newTargetBlockPos)
                        ) {
                            alreadySearched.add(newTargetBlockPos);
                            searchNext.add(newTargetBlockPos);
                            connectionBonus += 1;
                        }
                    }

                }

                searchMe = searchNext;
            }
        }
        ArrayList<ArrayList<BlockPos>> debugBlockClusters = (ArrayList<ArrayList<BlockPos>>)blockClusters.clone();
        ArrayList<ArrayList<BlockPos>> removeMe = new ArrayList<>();
        Iterator<ArrayList<BlockPos>> iterator1 = blockClusters.iterator();
        while (iterator1.hasNext()) {
            ArrayList<BlockPos> blockCluster = iterator1.next();
            Iterator<ArrayList<BlockPos>> iterator2 = blockClusters.iterator();
            while (iterator2.hasNext()) {
                ArrayList<BlockPos> blockCluster2 = iterator2.next();
                if(
                    !blockCluster2.equals(blockCluster) &&
                    !removeMe.contains(blockCluster2) &&
                    !removeMe.contains(blockCluster)
                ) {
                    boolean blnMerge = false;
                    for (BlockPos blockPos : blockCluster2) {
                        if (blockCluster.contains(blockPos)) {
                            blnMerge = true;
                        }
                    }
                    if (blnMerge) {
                        for (BlockPos blockPos : blockCluster2) {
                            if (!blockCluster.contains(blockPos)) {
                                blockCluster.add(blockPos);
                            }
                        }
                        removeMe.add(blockCluster2);
                    }
                }

            }

        }
        for (ArrayList<BlockPos> blockCluster : removeMe) {
            blockClusters.remove(blockCluster);
        }
      /*  if(
            blockClusters.size() > 1 &&
            debugBlockClusters.size() != blockClusters.size()
        ){
            ChaosCraft.LOGGER.info("X12345");
        }*/
       return blockClusters;
    }



    @Override
    public boolean onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if(clientOrgManager == null){
            return true;
        }
        if(!clientOrgManager.getEntity().isAlive()){
            return false;
        }
        ArrayList<BlockPos> myBlocks = new ArrayList<>();
        for (BlockStateMemoryBufferSlot blockStateMemoryBufferSlot : clientOrgManager.getBlockStateMemory().values()) {
            myBlocks.add(blockStateMemoryBufferSlot.blockPos);
          /*  CCGUIHelper.drawAABB(
                    event.getMatrixStack(),
                    new AxisAlignedBB(blockStateMemoryBufferSlot.blockPos),
                    Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                    .03D,
                    color,
                    .5f
            );*/
        }
        ArrayList<ArrayList<BlockPos>> blockClusters = clusterBlocks(myBlocks, clientOrgManager);
        for (int i = 0; i < blockClusters.size(); i++) {
            if(!colorMap.containsKey(i)){
                colorMap.put(
                        i,
                        new Color(
                            (int) Math.round(Math.random() * 255),
                            (int) Math.round(Math.random() * 255),
                            (int) Math.round(Math.random() * 255)
                        )
                );
            }
            drawBlockCluster(event, blockClusters.get(i), colorMap.get(i));
        }
        /*for (BlockStateMemoryBufferSlot myBlock : clientOrgManager.getBlockStateMemory().values()) {
            Color color = Color.GREEN;
            if(
                myBlock.debugBlockPos != null &&
                !myBlock.debugBlockPos.equals(myBlock.blockPos)
            ){
                CCGUIHelper.drawAABB(
                        event.getMatrixStack(),
                        new AxisAlignedBB(myBlock.debugBlockPos),
                        Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                        .002D,
                        Color.PINK,
                        .5f
                );
                color = Color.RED;
            }
            CCGUIHelper.drawAABB(
                    event.getMatrixStack(),
                    new AxisAlignedBB(myBlock.blockPos),
                    Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                    .002D,
                    color,
                    .5f
            );
        }*/
        return true;

    }

    private void drawBlockCluster(RenderWorldLastEvent event, ArrayList<BlockPos> blockCluster, Color color) {

        for (BlockPos blockPos : blockCluster) {


            CCGUIHelper.drawAABB(
                    event.getMatrixStack(),
                    new AxisAlignedBB(blockPos),
                    Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView(),
                    .03D,
                    color,
                    .5f
            );
        }
    }
}

