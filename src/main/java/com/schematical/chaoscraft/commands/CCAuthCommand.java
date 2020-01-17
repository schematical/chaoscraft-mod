package com.schematical.chaoscraft.commands;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaosnet.model.AuthLogin;
import com.schematical.chaosnet.model.AuthLoginResponse;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.PostAuthLoginRequest;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntitySummonArgument;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class CCAuthCommand {
    private static final SimpleCommandExceptionType SUMMON_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.failed", new Object[0]));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
                Commands.literal("ccauth").requires((source) -> source.hasPermissionLevel(2)).executes(CCAuthCommand::auth)
                /*Commands.literal("ccauth").requires((source) -> source.hasPermissionLevel(2))
                        .then(
                                Commands.argument("username", new StringArgument())
                                        .then(
                                            Commands.argument("password", new StringArgument()
                                        )
                                .executes(CCAuthCommand::auth)
                        )
                )*/
        );
    }
    private static int auth(CommandContext<CommandSource> context) {
        try{
            ChaosCraft.config.accessToken = null;
            ChaosCraft.getClient().init();
            return 1;
        }catch(Exception exception){
            context.getSource().sendFeedback(
                    new TranslationTextComponent("chaoscraft.commands.auth.failed", exception.getMessage()),
                    true
            );
            return 0;
        }

        /*try{
            PostAuthLoginRequest postAuthLoginRequest = new PostAuthLoginRequest();


            AuthLogin authLogin = new AuthLogin();
            authLogin.setUsername(context.getArgument("username", String.class));
            authLogin.setPassword(context.getArgument("password", String.class));

            postAuthLoginRequest.authLogin(
                    authLogin
            );
            ChaosCraft.LOGGER.info("TRYING `ccauth`");
            AuthLoginResponse authLoginResponse = ChaosCraft.sdk.postAuthLogin(postAuthLoginRequest).getAuthLoginResponse();

            ChaosCraft.LOGGER.info("Access Token >> {}", authLoginResponse.getAccessToken());
            ChaosCraft.config.username = authLogin.getUsername();
            ChaosCraft.config.idToken =  authLoginResponse.getIdToken();
            ChaosCraft.config.refreshToken =  authLoginResponse.getRefreshToken();
            ChaosCraft.config.refreshToken =  authLoginResponse.getRefreshToken();
            ChaosCraft.config.accessToken =  authLoginResponse.getAccessToken();
            ChaosCraft.config.save();

            context.getSource().sendFeedback(
                    new TranslationTextComponent("chaoscraft.commands.auth.success", ChaosCraft.config.username),
                    true
            );
            return 1;
        }catch(ChaosNetException exception){

            context.getSource().sendFeedback(
                    new TranslationTextComponent("chaoscraft.commands.auth.fail", exception.getMessage()),
                    true
            );

            return 0;
        }*/

    }

    static class StringArgument implements ArgumentType<String> {

        @Override
        public String parse(StringReader reader) throws CommandSyntaxException {
            return reader.readString();
        }
    }
}
