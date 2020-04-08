package com.schematical.chaoscraft.commands;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.PostUsernameTrainingroomsTrainingroomSessionsStartRequest;
import com.schematical.chaosnet.model.PostUsernameTrainingroomsTrainingroomSessionsStartResult;
import com.schematical.chaosnet.model.TraningRoomSessionStartRequest;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class CCBlockResetCommand {

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
                Commands.literal("ccblockreset").requires((source) -> source.hasPermissionLevel(2))
                       .executes(CCBlockResetCommand::execute)
                );


    }

    private static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ChaosCraft.getServer().replaceAllAlteredBlocks();

        context.getSource().sendFeedback(
                new TranslationTextComponent("Finished..."),
                true
        );
        return 1;
    }
}
