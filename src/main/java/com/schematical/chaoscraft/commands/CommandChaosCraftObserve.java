package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.Organism;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by user1a on 1/3/19.
 */
public class CommandChaosCraftObserve extends CommandBase{
     /*
     * Gets the name of the command
     */
    public String getName()
    {
        return "chaoscraft-observe";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "commands.chaoscraft-observe.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {

        if (!sender.getEntityWorld().isRemote) {
            return;
        }
        EntityPlayerMP player = null;
        if (sender instanceof EntityPlayerMP)
        {
            player =  (EntityPlayerMP)sender;
        }
        else
        {
            throw new PlayerNotFoundException("commands.generic.player.unspecified");
        }


        player.setGameType(GameType.SPECTATOR);
        int index = (int)Math.floor(ChaosCraft.organisims.size() * Math.random());
        EntityOrganism orgToObserve = ChaosCraft.organisims.get(index);
        ChaosCraft.chat("Observing: " + orgToObserve);
        player.setSpectatingEntity(orgToObserve);

    }





}
