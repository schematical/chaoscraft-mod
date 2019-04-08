package com.schematical.chaoscraft;

import com.google.common.base.Predicate;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.entities.EntityRick;
import com.schematical.chaoscraft.gui.CCKeyBinding;
import com.schematical.chaoscraft.gui.CCOrgListView;
import com.schematical.chaoscraft.gui.CCSpeciesListView;
import com.schematical.chaoscraft.gui.ChaosCraftGUI;
import com.schematical.chaoscraft.proxies.ClientProxy;
import com.schematical.chaosnet.model.ChaosNetException;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by user1a on 1/6/19.
 */
public class CCEventListener {

  @SubscribeEvent
  public static void onWorldTickEvent(TickEvent.WorldTickEvent worldTickEvent) {
    ChaosCraft.ticksSinceLastSpawn += 1;

    if (ChaosCraft.rick != null && ChaosCraft.rick.isDead) {
      ChaosCraft.rick = null;
      ChaosCraft.spawnRick(worldTickEvent.world, ChaosCraft.rickPos);
    }

    Iterator<EntityOrganism> iterator = ChaosCraft.organisims.iterator();

    while (iterator.hasNext()) {
      EntityOrganism organism = iterator.next();
      organism.manualUpdateCheck();
      if (
          organism.getOrganism() == null ||
              organism.getSpawnHash() != ChaosCraft.spawnHash
      ) {
        organism.setDead();
        iterator.remove();
        //ChaosCraft.logger.info("Setting Dead: " + organism.getName() + " - Has no `Organism` record");
      }
    }

    if (
        ChaosCraft.orgsToSpawn != null &&
            ChaosCraft.orgsToSpawn.size() > 0
    ) {
      ChaosCraft.spawnOrgs(ChaosCraft.orgsToSpawn);
      ChaosCraft.orgsToSpawn.clear();
    }
    if (ChaosCraft.consecutiveErrorCount > 5) {
      throw new ChaosNetException("ChaosCraft.consecutiveErrorCount > 5");
    }

    if (ChaosCraft.organisims.size() > 0) {
      for (EntityPlayerMP observingPlayer : ChaosCraft.observingPlayers) {
        Entity entity = observingPlayer.getSpectatingEntity();

        if (
            entity.equals(observingPlayer) ||
                entity == null ||
                entity.isDead
        ) {
          if (
              entity != null &&
                  entity instanceof EntityOrganism
          ) {
            ((EntityOrganism) entity).setObserving(null);
          }
          int index = (int) Math.floor(ChaosCraft.organisims.size() * Math.random());
          EntityOrganism orgToObserve = ChaosCraft.organisims.get(index);
          orgToObserve.setObserving(observingPlayer);
          observingPlayer.setSpectatingEntity(orgToObserve);
        }
      }
    }


  }


  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void render(RenderGameOverlayEvent.Post event) {
    if (ChaosCraft.fontRenderer == null) {
      ChaosCraft.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }
    if (ChaosCraft.topLeftMessage != null) {
      String[] parts = ChaosCraft.topLeftMessage.split("\n");
      int placement = 5;
      for (String part : parts) {
        ChaosCraft.fontRenderer.drawStringWithShadow(part, 5, placement, 0xFFFFFF);
        placement += 10;
      }
    }

  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void debugRenderer(RenderWorldLastEvent event) {
    EntityPlayer player = Minecraft.getMinecraft().player;
    if (player == null) {
      return;
    }
       /* ChaosCraftGUI.drawDebugBox(
                new Vec3d(
                        axisAlignedBB.minX,
                        axisAlignedBB.minY,
                        axisAlignedBB.minZ
                        //-256, 64, 266
                ),
                new Vec3d(
                        axisAlignedBB.maxX,
                        axisAlignedBB.maxY,
                        axisAlignedBB.maxZ
                        //-300,80,300
                ),
                Color.GREEN
        );

        ChaosCraftGUI.drawDebugLine(
                new BlockPos(
                        -256, 64, 266
                ),
                new BlockPos(
                        -300,80,300
                ),
                new Color(255, 155, 61, 255)
        );*/

//       for(ChunkPos pos : ForgeChunkManager.getPersistentChunksFor())

    ChaosCraftGUI.render(event);

  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
  public static void onEvent(InputEvent.KeyInputEvent event) {

    for (KeyBinding keyBinding : ClientProxy.keyBindings) {
      // check each enumerated key binding type for pressed and take appropriate action
      if (keyBinding.isPressed()) {
        // DEBUG
        switch (keyBinding.getKeyDescription()) {
          case (CCKeyBinding.SHOW_ORG_LIST):
            CCOrgListView view = new CCOrgListView();

            Minecraft.getMinecraft().displayGuiScreen(view);
            break;
          case (CCKeyBinding.SHOW_SPECIES_LIST):
            CCSpeciesListView view2 = new CCSpeciesListView();

            Minecraft.getMinecraft().displayGuiScreen(view2);
            break;
          case (CCKeyBinding.OBSERVER_MODE):
            List<EntityPlayerMP> players = Minecraft
                .getMinecraft().world.<EntityPlayerMP>getPlayers(EntityPlayerMP.class,
                    new Predicate<EntityPlayerMP>() {
                      @Override
                      public boolean apply(@Nullable EntityPlayerMP input) {
                        return true;
                      }
                    });
            for (EntityPlayerMP player : players) {
              ChaosCraft.toggleObservingPlayer(player);
            }
            break;
        }

        // do stuff for this key binding here
        // remember you may need to send packet to server

      }
    }


  }

  @SubscribeEvent
  public static void spawnEvent(EntityJoinWorldEvent event) {
       /* if (event.getEntity() instanceof EntityMob) {
            EntityMob mob = (EntityMob) event.getEntity();

            if (!(mob instanceof EntityPigZombie)) {
                if (mob instanceof EntityEnderman) {

                } else {
                    mob.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(mob, EntityOrganism.class, true));
                }
            }
        }*/
  }

  @SubscribeEvent
  public static void entityEnterChunkEvent(EntityEvent.EnteringChunk event) {
    if (event.getEntity().world.isRemote) {
      return;
    }

    if (event.getEntity() instanceof EntityOrganism || event.getEntity() instanceof EntityRick) {
      Chunk oldChunk = event.getEntity().world.getChunk(event.getOldChunkX(), event.getOldChunkZ());
      ForgeChunkManager.Ticket chunkTicket;

      if (event.getEntity() instanceof EntityOrganism) {
        chunkTicket = ((EntityOrganism) event.getEntity()).chunkTicket;
      } else {
        chunkTicket = ((EntityRick) event.getEntity()).chunkTicket;
      }

      if (chunkTicket == null) {
        System.out.println("WTF WHY HOW???");
        return;
      }

      ForgeChunkManager.unforceChunk(chunkTicket, oldChunk.getPos());

      ForgeChunkManager
          .forceChunk(chunkTicket, new ChunkPos(event.getOldChunkX(), event.getNewChunkZ()));

      if (chunkTicket.getChunkList().size() >= chunkTicket.getMaxChunkListDepth()) {
        ChaosCraft.logger.error("Passed max amount of chunks loaded!");
      }
    }
  }

}
