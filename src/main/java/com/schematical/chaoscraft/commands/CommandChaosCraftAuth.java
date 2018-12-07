package com.schematical.chaoscraft.commands;

import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaoscraft.entities.EntityEvilRabbit;

import com.schematical.chaosnet.model.AuthLogin;
import com.schematical.chaosnet.model.AuthLoginResponse;
import com.schematical.chaosnet.model.PostAuthLoginRequest;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import org.json.simple.JSONObject;
import scala.collection.concurrent.Debug;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by user1a on 12/4/18.
 */
public class CommandChaosCraftAuth extends CommandBase {
    public CommandChaosCraftAuth() {
    }

    public String getName() {
        return "chaoscraft-auth";
    }

   /* public int getRequiredPermissionLevel() {
        return 2;
    } */

    public String getUsage(ICommandSender p_getUsage_1_) {
        return "commands.chaoscraft-auth.usage";
    }

    public void execute(MinecraftServer p_execute_1_, ICommandSender p_execute_2_, String[] p_execute_3_) throws CommandException {
        Debug.log(p_execute_3_);
        PostAuthLoginRequest postAuthLoginRequest = new PostAuthLoginRequest();


        AuthLogin authLogin = new AuthLogin();
        authLogin.setUsername(p_execute_3_[0]);
        authLogin.setPassword(p_execute_3_[1]);

        postAuthLoginRequest.authLogin(
            authLogin
        );

        AuthLoginResponse authLoginResponse = ChaosCraft.sdk.postAuthLogin(postAuthLoginRequest).getAuthLoginResponse();

        ChaosCraft.logger.info("Access Token >> {}", authLoginResponse.getAccessToken());

        ChaosCraft.config.refreshToken =  authLoginResponse.getRefreshToken();
        ChaosCraft.config.accessToken =  authLoginResponse.getAccessToken();
        ChaosCraft.config.save();

        p_execute_2_.sendMessage(
            new TextComponentString("Auth Credentials pulled from server and saved locally ")
        );

    }

    public List<String> getTabCompletions(MinecraftServer p_getTabCompletions_1_, ICommandSender p_getTabCompletions_2_, String[] p_getTabCompletions_3_, @Nullable BlockPos p_getTabCompletions_4_) {
        return p_getTabCompletions_3_.length == 1?getListOfStringsMatchingLastWord(p_getTabCompletions_3_, EntityList.getEntityNameList()):(p_getTabCompletions_3_.length > 1 && p_getTabCompletions_3_.length <= 4?getTabCompletionCoordinate(p_getTabCompletions_3_, 1, p_getTabCompletions_4_): Collections.emptyList());
    }
}
