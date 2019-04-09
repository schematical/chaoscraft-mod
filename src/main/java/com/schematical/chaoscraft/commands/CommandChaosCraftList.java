package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.*;


/**
 * Created by user1a on 1/3/19.
 */
public class CommandChaosCraftList extends CommandBase{
     /*
     * Gets the name of the command
     */
    public String getName()
    {
        return "chaoscraft-list";
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
        return "commands.chaoscraft-list.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (!sender.getEntityWorld().isRemote) {
            return;
        }
        if (args.length > 0)
        {
            sender.sendMessage(
                    new TextComponentString("Too many args?")
            );
            return;
            //throw new WrongUsageException("commands.chaoscraft-tp.usage", new Object[0]);
        }

        String message = "";
        int liveCount = 0;

        for(EntityOrganism entityOrganism : ChaosCraft.server.organisims){
            message += entityOrganism.getCCNamespace() + "  ";
            message += "MaxLife: " + entityOrganism.getAgeSeconds() + " / " + entityOrganism.getMaxLife() + "  ";
            message += "Score: " + entityOrganism.entityFitnessManager.totalScore() + "  ";
            if(entityOrganism.isDead) {
                message += "D";
            }else{
                message += "A";
                liveCount += 1;
            }
            message += "\n";
        }
        message += "------------------------------\n";
        message += "Live: " + liveCount + " - Total: " + ChaosCraft.server.organisims.size();
        sender.sendMessage(
            new TextComponentString(message)
        );



    }



    /**
     * Get a list of options for when the user presses the TAB key
     */
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length != 1 && args.length != 2 ? Collections.emptyList() : getListOfStringsMatchingLastWord(args,getOrgNames());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
    public String[] getOrgNames(){
        List<EntityOrganism> orgs = new ArrayList<EntityOrganism>();
        for (EntityOrganism organism: ChaosCraft.server.organisims) {
            if(organism.isEntityAlive() && organism.getNNet() != null){
                orgs.add(organism);
            }
        }
        String[] astring = new String[orgs.size() + 1];
        int i = 0;
        astring[i] = "rick";
        i ++;
        for (EntityOrganism organism: orgs) {
            astring[i] = organism.getName();
            i ++;
        }

        return astring;
    }

}
