package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Created by user1a on 1/3/19.
 */
public class CommandChaosCraftTP extends CommandBase{

     /*
     * Gets the name of the command
     */
    public String getName()
    {
        return "chaoscraft-tp";
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
        return "commands.chaoscraft-tp.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (!sender.getEntityWorld().isRemote) {
            return;
        }
        if (args.length < 1)
        {
            sender.sendMessage(
                    new TextComponentString("Add some args...")
            );
            //throw new WrongUsageException("commands.chaoscraft-tp.usage", new Object[0]);
        }
        else
        {
            String name = args[0];
            int i = 0;
            Entity entity = (Entity)sender;//getCommandSenderAsPlayer(sender);
            Entity entity1 =null;
            if(name.equals("rick")) {
                entity1 = ChaosCraft.rick;
            }else{
                for (EntityOrganism organism : ChaosCraft.organisims) {
                    String orgName = organism.getOrganism().getName();
                    ChaosCraft.logger.info(orgName + " == " + name);
                    if (orgName.equals(name)) {
                        entity1 = organism;
                    }
                }
            }
            if(entity1 == null){
                sender.sendMessage(
                    new TextComponentString("Error: No entity with this name: "+ name)
                );
                return;
            }


            entity.dismountRidingEntity();

            if (entity instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP)entity).connection.setPlayerLocation(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
            }
            else
            {
                entity.setLocationAndAngles(entity1.posX, entity1.posY, entity1.posZ, entity1.rotationYaw, entity1.rotationPitch);
            }

            sender.sendMessage(
                    new TextComponentString("You there...")
            );

        }
    }

    /**
     * Get a list of options for when the user presses the TAB key
     */
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length==0 ? Collections.emptyList() : getListOfStringsMatchingLastWord(args,getOrgNames());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }

    private String[] getOrgNames(){
        List<String> names = new ArrayList<>(ChaosCraft.organisims.size());

        names.add("rick");

        for (EntityOrganism organism: ChaosCraft.organisims) {
            if(organism.isEntityAlive() && organism.getNNet() != null){
                names.add(organism.getOrganism().getName());
            }
        }

        return names.toArray(new String[0]);
    }

}
