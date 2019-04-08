package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by user1a on 12/4/18.
 */
public class CommandChaosCraftRefresh extends CommandBase {

  public CommandChaosCraftRefresh() {
  }

  public String getName() {
    return "chaoscraft-refresh";
  }

   /* public int getRequiredPermissionLevel() {
        return 2;
    } */

  public String getUsage(ICommandSender p_getUsage_1_) {
    return "commands.chaoscraft-refresh.usage";
  }

  public void execute(MinecraftServer p_execute_1_, ICommandSender p_execute_2_,
      String[] p_execute_3_) throws CommandException {
    if (ChaosCraft.rick == null || ChaosCraft.rick.isDead) {
      ChaosCraft.rick = null;
      World world = p_execute_1_.getEntityWorld();
      if (!world.isRemote) {
        p_execute_2_.sendMessage(
            new TextComponentString("Spawning Rick...")
        );
        BlockPos pos = p_execute_2_.getPosition();
        ChaosCraft.spawnRick(world, pos);
        p_execute_2_.sendMessage(
            new TextComponentString("Rick Spawned")
        );
      }
    }
    //Cycle mortys?
    p_execute_2_.sendMessage(
        new TextComponentString("Clearing Organisms")
    );
    List<EntityOrganism> deadOrgs = new ArrayList<EntityOrganism>();
    for (EntityOrganism organsim : ChaosCraft.organisims) {
      if (organsim.getNNet() == null) {
        organsim.setHealth(0);
        organsim.setDead();
        deadOrgs.add(organsim);
        p_execute_2_.sendMessage(
            new TextComponentString("Cleared Dead: " + organsim.getName())
        );
      }
    }
    for (EntityOrganism organisim : deadOrgs) {
      ChaosCraft.organisims.remove(organisim);
    }
    p_execute_2_.sendMessage(
        new TextComponentString("Cleared Dead, spawning orgs")
    );
    List<EntityOrganism> spawnedEntityOrganisms = ChaosCraft.spawnOrgs();
    for (EntityOrganism organism : spawnedEntityOrganisms) {
      p_execute_2_.sendMessage(
          new TextComponentString("Spawned: " + organism.getName())
      );
    }
    p_execute_2_.sendMessage(
        new TextComponentString("Orgs spawned")
    );
  }

  public List<String> getTabCompletions(MinecraftServer p_getTabCompletions_1_,
      ICommandSender p_getTabCompletions_2_, String[] p_getTabCompletions_3_,
      @Nullable BlockPos p_getTabCompletions_4_) {
    return p_getTabCompletions_3_.length == 1 ? getListOfStringsMatchingLastWord(
        p_getTabCompletions_3_, EntityList.getEntityNameList())
        : (p_getTabCompletions_3_.length > 1 && p_getTabCompletions_3_.length <= 4
            ? getTabCompletionCoordinate(p_getTabCompletions_3_, 1, p_getTabCompletions_4_)
            : Collections.emptyList());
  }
}
