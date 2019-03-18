package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityOrganism;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
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
                    String orgName = organism.getName();
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
     * Teleports an entity to the specified coordinates
     */
    private static void teleportEntityToCoordinates(Entity teleportingEntity, CommandBase.CoordinateArg argX, CommandBase.CoordinateArg argY, CommandBase.CoordinateArg argZ, CommandBase.CoordinateArg argYaw, CommandBase.CoordinateArg argPitch)
    {
        if (teleportingEntity instanceof EntityPlayerMP)
        {
            Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.<SPacketPlayerPosLook.EnumFlags>noneOf(SPacketPlayerPosLook.EnumFlags.class);

            if (argX.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.X);
            }

            if (argY.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Y);
            }

            if (argZ.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Z);
            }

            if (argPitch.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
            }

            if (argYaw.isRelative())
            {
                set.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
            }

            float f = (float)argYaw.getAmount();

            if (!argYaw.isRelative())
            {
                f = MathHelper.wrapDegrees(f);
            }

            float f1 = (float)argPitch.getAmount();

            if (!argPitch.isRelative())
            {
                f1 = MathHelper.wrapDegrees(f1);
            }

            teleportingEntity.dismountRidingEntity();
            ((EntityPlayerMP)teleportingEntity).connection.setPlayerLocation(argX.getAmount(), argY.getAmount(), argZ.getAmount(), f, f1, set);
            teleportingEntity.setRotationYawHead(f);
        }
        else
        {
            float f2 = (float)MathHelper.wrapDegrees(argYaw.getResult());
            float f3 = (float)MathHelper.wrapDegrees(argPitch.getResult());
            f3 = MathHelper.clamp(f3, -90.0F, 90.0F);
            teleportingEntity.setLocationAndAngles(argX.getResult(), argY.getResult(), argZ.getResult(), f2, f3);
            teleportingEntity.setRotationYawHead(f2);
        }

        if (!(teleportingEntity instanceof EntityLivingBase) || !((EntityLivingBase)teleportingEntity).isElytraFlying())
        {
            teleportingEntity.motionY = 0.0D;
            teleportingEntity.onGround = true;
        }
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
        for (EntityOrganism organism: ChaosCraft.organisims) {
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
