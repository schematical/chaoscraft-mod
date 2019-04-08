package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;


/**
 * Created by user1a on 1/3/19.
 */
public class CommandChaosCraftObserve extends CommandBase {

  /*
   * Gets the name of the command
   */
  public String getName() {
    return "chaoscraft-observe";
  }

  /**
   * Return the required permission level for this command.
   */
  public int getRequiredPermissionLevel() {
    return 2;
  }

  /**
   * Gets the usage string for the command.
   */
  public String getUsage(ICommandSender sender) {
    return "commands.chaoscraft-observe.usage";
  }

  /**
   * Callback for when the command is executed
   */

  public void execute(MinecraftServer server, ICommandSender sender, String[] args)
      throws CommandException {

    EntityPlayerMP player = null;
    if (sender instanceof EntityPlayerMP) {
      player = (EntityPlayerMP) sender;
    } else {
      throw new PlayerNotFoundException("commands.generic.player.unspecified");
    }

    ChaosCraft.toggleObservingPlayer(player);

  }


}
