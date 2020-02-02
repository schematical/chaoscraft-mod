package com.schematical.chaoscraft.server.spawnproviders;

import com.schematical.chaoscraft.server.ServerOrgManager;
import net.minecraft.util.math.BlockPos;

public interface iServerSpawnProvider {
    public BlockPos getSpawnPos(ServerOrgManager serverOrgManager);
}
