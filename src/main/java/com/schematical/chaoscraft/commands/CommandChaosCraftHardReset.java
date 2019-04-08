package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.PostUsernameTrainingroomsTrainingroomSessionsStartRequest;
import com.schematical.chaosnet.model.PostUsernameTrainingroomsTrainingroomSessionsStartResult;
import com.schematical.chaosnet.model.TraningRoomSessionStartRequest;
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

/**
 * Created by user1a on 12/4/18.
 */
public class CommandChaosCraftHardReset extends CommandBase {

  public CommandChaosCraftHardReset() {
  }

  public String getName() {
    return "chaoscraft-hard-reset";
  }

   /* public int getRequiredPermissionLevel() {
        return 2;
    } */

  public String getUsage(ICommandSender p_getUsage_1_) {
    return "commands.chaoscraft-hard-reset.usage";
  }

  public void execute(MinecraftServer p_execute_1_, ICommandSender p_execute_2_, String[] args)
      throws CommandException {
    try {
      if (args.length == 0 || !args[0].equals("true")) {
        p_execute_2_.sendMessage(
            new TextComponentString("This requires an argument of `true` to work")
        );
        return;
      }
      PostUsernameTrainingroomsTrainingroomSessionsStartRequest request = new PostUsernameTrainingroomsTrainingroomSessionsStartRequest();
      request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
      request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
      TraningRoomSessionStartRequest traningRoomSessionStartRequest = new TraningRoomSessionStartRequest();

      traningRoomSessionStartRequest.setReset(true);
      request.setTraningRoomSessionStartRequest(traningRoomSessionStartRequest);
      PostUsernameTrainingroomsTrainingroomSessionsStartResult response = ChaosCraft.sdk
          .postUsernameTrainingroomsTrainingroomSessionsStart(request);

    } catch (ChaosNetException exception) {

      p_execute_2_.sendMessage(
          new TextComponentString(
              "Error Status: " + exception.sdkHttpMetadata().httpStatusCode() + " - " + exception
                  .sdkHttpMetadata().responseContent().toString())
      );
      return;
    }

    p_execute_2_.sendMessage(
        new TextComponentString("Hard Reset attempt complete. You may want to restart.")
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
