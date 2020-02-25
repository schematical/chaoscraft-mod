package com.schematical.chaoscraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.blocks.ChaosBlocks;
import com.schematical.chaoscraft.tileentity.BuildAreaMarkerTileEntity;
import com.schematical.chaoscraft.util.BuildArea;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.client.Minecraft.getInstance;

public class CCClearBuildAreaCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
                Commands.literal("ccclear").requires((source) -> source.hasPermissionLevel(2))
                        .executes(CCClearBuildAreaCommand::clearBlocks)

        );
    }

    private static int clearBlocks(CommandContext<CommandSource> commandSourceCommandContext) throws CommandSyntaxException{
        for (int i = 0; i < ChaosCraft.buildAreas.size(); i++) {
            BuildAreaMarkerTileEntity.resetBuildArea(ChaosBlocks.markerBlocks.get(i), getWorld(i));
        }
        return 1;
    }

    private static World getWorld(int i){
        return ChaosCraft.buildAreaMarkers.get(i).getWorld();
    }
}
