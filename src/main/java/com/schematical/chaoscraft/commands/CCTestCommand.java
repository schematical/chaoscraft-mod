package com.schematical.chaoscraft.commands;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.OrgEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;

public class CCTestCommand {

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
                Commands.literal("cctest").requires((source) -> source.hasPermissionLevel(2))
                       .executes(CCTestCommand::summonEntity)

        );
    }

    private static int summonEntity(CommandContext<CommandSource> context) throws CommandSyntaxException {


        context.getSource().sendFeedback(
                new TranslationTextComponent("chaoscraft.commands.cctest", ChaosCraft.spawnBlocks.size()),
                true
        );
        return 1;
    }
}
