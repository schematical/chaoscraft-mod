package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityEvilRabbit;
import com.schematical.chaosnet.model.AuthLogin;
import com.schematical.chaosnet.model.AuthLoginResponse;
import com.schematical.chaosnet.model.PostAuthLoginRequest;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import scala.collection.concurrent.Debug;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by user1a on 12/4/18.
 */
public class CommandChaosCraftSetConfig extends CommandBase {
    public CommandChaosCraftSetConfig() {
    }

    public String getName() {
        return "chaoscraft-set";
    }

   /* public int getRequiredPermissionLevel() {
        return 2;
    } */

    public String getUsage(ICommandSender p_getUsage_1_) {
        return "commands.chaoscraft-set.usage";
    }

    public void execute(MinecraftServer p_execute_1_, ICommandSender p_execute_2_, String[] p_execute_3_) throws CommandException {

        //authLogin.setPassword(p_execute_3_[1]);
        String key = p_execute_3_[0];
        String value = p_execute_3_[1];
        switch(key){
            case("maxBotCount"):
                ChaosCraft.config.maxBotCount = Integer.parseInt(value);
                ChaosCraft.config.save();
            break;
            default:
                p_execute_2_.sendMessage(
                        new TextComponentString("Invalid Config Key: " + key)
                );
        }

    }

    public List<String> getTabCompletions(MinecraftServer p_getTabCompletions_1_, ICommandSender p_getTabCompletions_2_, String[] p_getTabCompletions_3_, @Nullable BlockPos p_getTabCompletions_4_) {
        return p_getTabCompletions_3_.length == 1?getListOfStringsMatchingLastWord(p_getTabCompletions_3_, EntityList.getEntityNameList()):(p_getTabCompletions_3_.length > 1 && p_getTabCompletions_3_.length <= 4?getTabCompletionCoordinate(p_getTabCompletions_3_, 1, p_getTabCompletions_4_): Collections.emptyList());
    }
}
