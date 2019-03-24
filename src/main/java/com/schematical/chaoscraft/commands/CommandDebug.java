package com.schematical.chaoscraft.commands;

import com.google.common.collect.ImmutableSetMultimap;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaoscraft.entities.EntityRick;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeChunkManager;

public class CommandDebug extends CommandBase {

    @Override
    public String getName() {
        return "command_debug";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return getName();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ImmutableSetMultimap<ChunkPos, ForgeChunkManager.Ticket> chunks = ForgeChunkManager.getPersistentChunksFor(sender.getEntityWorld());

        sender.sendMessage(new TextComponentString(chunks.keys().elementSet().size() + " chunks loaded"));

        for(ChunkPos pos : chunks.keys().elementSet()) {
            sender.sendMessage(new TextComponentString("     " + pos.toString()));
        }

        int ticketsAvailable = ForgeChunkManager.ticketCountAvailableFor(ChaosCraft.INSTANCE, sender.getEntityWorld());
        sender.sendMessage(new TextComponentString(ticketsAvailable + " tickets available"));

        int organismsInWorld = 0;
        int ricksInWorld = 0;
        for(Entity e : sender.getEntityWorld().loadedEntityList) {
            if(e instanceof EntityOrganism) {
                organismsInWorld++;
            } else if (e instanceof EntityRick) {
                ricksInWorld++;
            }
        }

        sender.sendMessage(new TextComponentString(organismsInWorld + " organisms in world"));
        sender.sendMessage(new TextComponentString(ricksInWorld + " ricks in world"));

        sender.sendMessage(new TextComponentString((ticketsAvailable+organismsInWorld) + " tickets available after organisms die"));
        sender.sendMessage(new TextComponentString((ticketsAvailable+organismsInWorld+ricksInWorld) + " tickets in total"));

        if(ticketsAvailable+organismsInWorld+ricksInWorld<ForgeChunkManager.getMaxTicketLengthFor(ChaosCraft.MODID)) {
            TextComponentString textComponentWarning = new TextComponentString("Tickets have been lost! I repeat tickets have been lost!");
            textComponentWarning.getStyle().setColor(TextFormatting.RED);
            TextComponentString textComponentInformation = new TextComponentString("Expected total of " + ForgeChunkManager.getMaxTicketLengthFor(ChaosCraft.MODID) + " but got " + (ticketsAvailable+organismsInWorld+ricksInWorld));
            textComponentInformation.getStyle().setColor(TextFormatting.RED);
            sender.sendMessage(textComponentWarning);
            sender.sendMessage(textComponentInformation);
        }
    }
}
