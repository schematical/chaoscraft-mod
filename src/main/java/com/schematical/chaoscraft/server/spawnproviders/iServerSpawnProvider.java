package com.schematical.chaoscraft.server.spawnproviders;

import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.util.math.BlockPos;

public interface iServerSpawnProvider {
    BlockPos getSpawnPos(ServerOrgManager serverOrgManager);

}
