package com.schematical.chaoscraft.tickables;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.ai.action.ActionBase;
import com.schematical.chaoscraft.ai.action.PlaceBlockAction;
import com.schematical.chaoscraft.ai.memory.BlockStateMemoryBuffer;
import com.schematical.chaoscraft.ai.memory.BlockStateMemoryBufferSlot;
import com.schematical.chaoscraft.client.ClientOrgManager;
import com.schematical.chaoscraft.client.gui.CCGUIHelper;
import com.schematical.chaoscraft.client.iRenderWorldLastEvent;
import com.schematical.chaoscraft.entities.AlteredBlockInfo;
import com.schematical.chaoscraft.entities.OrgEntity;
import com.schematical.chaoscraft.events.EntityFitnessScoreEvent;
import com.schematical.chaoscraft.events.OrgEvent;
import com.schematical.chaoscraft.network.ChaosNetworkManager;
import com.schematical.chaoscraft.network.packets.CCServerScoreEventPacket;
import com.schematical.chaoscraft.server.ServerOrgManager;
import com.schematical.chaoscraft.services.targetnet.ScanInstance;
import com.schematical.chaosnet.model.ChaosNetException;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BuildyManager extends BaseChaosEventListener implements iRenderWorldLastEvent {
    private ClientOrgManager clientOrgManager;
    public ArrayList<ArrayList<BlockPos>> blockClusters = new ArrayList<>();
    private ArrayList<BlockPos> myBlocks = new ArrayList<>();
    public void onClientTick(ClientOrgManager baseOrgManager) {
        if (clientOrgManager == null) {
            clientOrgManager = baseOrgManager;
            ChaosCraft.getClient().addRenderListener(this);
        }
    }
    @Override
    public void onServerActionComplete(ServerOrgManager serverOrgManager, ActionBase actionBase) {
        if (!(actionBase instanceof PlaceBlockAction)) {
            return;
        }
        //blockClusters.clear();
        ArrayList<BlockPos> searchMe = new ArrayList<BlockPos>();
        ArrayList<BlockPos> alreadySearched = new ArrayList<BlockPos>();
        //Get all placed blocks
        int connectionBonus = 1;
        for (AlteredBlockInfo alteredBlockInfo : ChaosCraft.getServer().alteredBlocks.values()) {
            if (alteredBlockInfo.serverOrgManager.equals(serverOrgManager)) {
                myBlocks.add(alteredBlockInfo.blockPos);
                //alreadySearched.add(alteredBlockInfo.blockPos);
                if(!alreadySearched.contains(alteredBlockInfo.blockPos)) {
                    searchMe.add(alteredBlockInfo.blockPos);


                }
            }
            int safteyCatch = 0;
            ArrayList<BlockPos> selectdBlockCluster = null;
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

                    if(!alreadySearched.contains(targetBlockPos)){
                        alreadySearched.add(targetBlockPos);
                        if(
                            serverOrgManager.getEntity().world.getBlockState(targetBlockPos).isSolid() &&
                            myBlocks.contains(targetBlockPos)
                        ){
                           if(selectdBlockCluster == null) {
                               Iterator<ArrayList<BlockPos>> iterator1 = blockClusters.iterator();
                               while(iterator1.hasNext()){
                                   ArrayList<BlockPos> blockCluster  = iterator1.next();
                                   if (blockCluster.contains(targetBlockPos)) {
                                       if (
                                           selectdBlockCluster == null
                                       ) {
                                           selectdBlockCluster = blockCluster;
                                       }else{
                                           if(  !blockCluster.equals(selectdBlockCluster)) {
                                               for (BlockPos blockPos : blockCluster) {
                                                    if(!selectdBlockCluster.contains(blockPos)){
                                                        selectdBlockCluster.add(blockPos);
                                                    }
                                               }

                                               iterator1.remove();
                                           }
                                           // new ChaosNetException("TODO Merge the two");
                                       }
                                   }
                               }
                               if (selectdBlockCluster == null) {
                                   selectdBlockCluster = new ArrayList<>();
                                   blockClusters.add(selectdBlockCluster);

                               }
                           }
                           if(!selectdBlockCluster.contains(targetBlockPos)){
                               selectdBlockCluster.add(targetBlockPos);
                               for (Direction direction : com.schematical.chaoscraft.Enum.getDirections()) {
                                   BlockPos newTargetBlockPos = targetBlockPos.offset(direction);
                                   if(
                                       !alreadySearched.contains(newTargetBlockPos) &&
                                       !selectdBlockCluster.contains(newTargetBlockPos)
                                   ) {
                                       searchNext.add(newTargetBlockPos);
                                       connectionBonus += 1;

                                   }
                               }
                           }



                        }
                    }else{
                        //ChaosCraft.LOGGER.error("Block pos already searched");
                    }
                }

                searchMe = searchNext;
            }
        }
        for (ArrayList<BlockPos> blockCluster : blockClusters) {


        }

        CCServerScoreEventPacket serverScoreEventPacket = new CCServerScoreEventPacket(
            serverOrgManager.getCCNamespace(),
            myBlocks.size(),
            myBlocks.size(),
            "buildy",
            connectionBonus,
            (int) (serverOrgManager.getEntity().world.getGameTime() + ((serverOrgManager.getMaxLife() - serverOrgManager.getAgeSeconds()) * 20)),
            0
        );
        serverOrgManager.getEntity().addOrgEvent(new OrgEvent(new EntityFitnessScoreEvent(null, myBlocks.size(), null)));


        ChaosNetworkManager.sendTo(serverScoreEventPacket, serverOrgManager.getServerPlayerEntity());
    }



    @Override
    public boolean onRenderWorldLastEvent(RenderWorldLastEvent event) {
        if(clientOrgManager == null){
            return true;
        }
        if(!clientOrgManager.getEntity().isAlive()){
            return false;
        }

        for (BlockStateMemoryBufferSlot myBlock : clientOrgManager.getBlockStateMemory().values()) {
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
        }

        return true;

    }
}

