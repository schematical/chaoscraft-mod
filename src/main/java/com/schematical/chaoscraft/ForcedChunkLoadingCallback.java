package com.schematical.chaoscraft;

import java.util.List;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

public class ForcedChunkLoadingCallback implements ForgeChunkManager.LoadingCallback {

  @Override
  public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
    for (ForgeChunkManager.Ticket ticket : tickets) {

      //Give all tickets back to forge
      ForgeChunkManager.releaseTicket(ticket);
    }
  }
}
