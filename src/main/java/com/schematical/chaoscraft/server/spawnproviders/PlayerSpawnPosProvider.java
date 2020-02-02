package com.schematical.chaoscraft.server.spawnproviders;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

public class PlayerSpawnPosProvider implements iServerSpawnProvider {

    public BlockPos getSpawnPos(ServerOrgManager serverOrgManager){
        ServerWorld serverWorld = ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD);
        PlayerEntity playerEntity = serverWorld.getPlayers().get(0);
        BlockPos pos = null;
        int saftyCatch = 0;
        while(pos == null){
            saftyCatch ++;
            if (saftyCatch > 20) {
                ChaosCraft.LOGGER.error("Safty Catch Hit: " + saftyCatch);
                return null;
            }
            BlockPos pos1 = serverWorld.getSpawnPoint();
            int range = 32;
            pos1 = pos1.add(
                    new Vec3i(
                            Math.floor(Math.random() *  range * 2) - range,
                            5,
                            Math.floor(Math.random() *  range * 2) - range
                    )
            );
            int y = pos1.getY();
            boolean blnFound = false;
            int saftyCatch2 = 0;
            while(!blnFound && saftyCatch2 < 80) {
                saftyCatch2 ++;
                y--;
                BlockPos blockPos2 = new BlockPos(
                        pos1.getX(),
                        y,
                        pos1.getZ()
                );
                BlockState blockState = ChaosCraft.getServer().server.getWorld(DimensionType.OVERWORLD).getBlockState(blockPos2);
                //ChaosCraft.LOGGER.info("blockState: " + blockState.toString() + "  --? " + blockState.getBlock().toString());
                if(!blockState.getBlock().equals(Blocks.AIR)){
                    y += 2;
                    blnFound = true;
                    pos = new BlockPos(
                            blockPos2.getX(),
                            y,
                            blockPos2.getZ()
                    );
                }

            }


        }
;
        return pos;
    }
}
