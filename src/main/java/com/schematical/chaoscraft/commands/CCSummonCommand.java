package com.schematical.chaoscraft.commands;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntitySummonArgument;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class CCSummonCommand {
    private static final SimpleCommandExceptionType SUMMON_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.failed", new Object[0]));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ccsummon").requires((p_198740_0_) -> {
            return p_198740_0_.hasPermissionLevel(2);
        })).then(((RequiredArgumentBuilder)Commands.argument("entity", EntitySummonArgument.entitySummon()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes((p_198738_0_) -> {
            return summonEntity((CommandSource)p_198738_0_.getSource(), EntitySummonArgument.getEntityId(p_198738_0_, "entity"), ((CommandSource)p_198738_0_.getSource()).getPos(), new CompoundNBT(), true);
        })).then(((RequiredArgumentBuilder)Commands.argument("pos", Vec3Argument.vec3()).executes((p_198735_0_) -> {
            return summonEntity((CommandSource)p_198735_0_.getSource(), EntitySummonArgument.getEntityId(p_198735_0_, "entity"), Vec3Argument.getVec3(p_198735_0_, "pos"), new CompoundNBT(), true);
        })).then(Commands.argument("nbt", NBTCompoundTagArgument.func_218043_a()).executes((p_198739_0_) -> {
            return summonEntity((CommandSource)p_198739_0_.getSource(), EntitySummonArgument.getEntityId(p_198739_0_, "entity"), Vec3Argument.getVec3(p_198739_0_, "pos"), NBTCompoundTagArgument.func_218042_a(p_198739_0_, "nbt"), false);
        })))));
    }

    private static int summonEntity(CommandSource commandSource, ResourceLocation resourceLocation, Vec3d vec3d, CompoundNBT compoundNBT, boolean p_198737_4_) throws CommandSyntaxException {
        CompoundNBT compoundNBT1 = compoundNBT.copy();
        compoundNBT1.putString("id", resourceLocation.toString());
        if (EntityType.getKey(EntityType.LIGHTNING_BOLT).equals(resourceLocation)) {
            LightningBoltEntity lvt_6_1_ = new LightningBoltEntity(commandSource.getWorld(), vec3d.x, vec3d.y, vec3d.z, false);
            commandSource.getWorld().addLightningBolt(lvt_6_1_);
            commandSource.sendFeedback(new TranslationTextComponent("commands.summon.success", new Object[]{lvt_6_1_.getDisplayName()}), true);
            return 1;
        } else {
            ServerWorld lvt_6_2_ = commandSource.getWorld();
            Entity lvt_7_1_ = EntityType.func_220335_a(compoundNBT1, lvt_6_2_, (p_218914_2_) -> {
                p_218914_2_.setLocationAndAngles(vec3d.x, vec3d.y, vec3d.z, p_218914_2_.rotationYaw, p_218914_2_.rotationPitch);
                return !lvt_6_2_.summonEntity(p_218914_2_) ? null : p_218914_2_;
            });
            if (lvt_7_1_ == null) {
                throw SUMMON_FAILED.create();
            } else {
                if (p_198737_4_ && lvt_7_1_ instanceof MobEntity) {
                    ((MobEntity)lvt_7_1_).onInitialSpawn(commandSource.getWorld(), commandSource.getWorld().getDifficultyForLocation(new BlockPos(lvt_7_1_)), SpawnReason.COMMAND, (ILivingEntityData)null, (CompoundNBT)null);
                }

                commandSource.sendFeedback(new TranslationTextComponent("commands.summon.success", new Object[]{lvt_7_1_.getDisplayName()}), true);
                return 1;
            }
        }
    }
}
