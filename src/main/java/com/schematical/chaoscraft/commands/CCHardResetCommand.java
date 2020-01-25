package com.schematical.chaoscraft.commands;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.schematical.chaoscraft.ChaosCraft;
import com.schematical.chaosnet.model.ChaosNetException;
import com.schematical.chaosnet.model.PostUsernameTrainingroomsTrainingroomSessionsStartRequest;
import com.schematical.chaosnet.model.PostUsernameTrainingroomsTrainingroomSessionsStartResult;
import com.schematical.chaosnet.model.TraningRoomSessionStartRequest;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class CCHardResetCommand {

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
                Commands.literal("cchardreset").requires((source) -> source.hasPermissionLevel(2))
                        .then(
                            Commands.argument("arg", new CCAuthCommand.StringArgument()
                        )
                        .executes(CCHardResetCommand::execute)
                    )
                );


    }

    private static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        PostUsernameTrainingroomsTrainingroomSessionsStartResult response = null;
        try{
            String arg = context.getArgument("arg", String.class);
            if (!arg.equals("true"))
            {
                context.getSource().sendFeedback(
                        new TranslationTextComponent("This requires an argument of `true` to work"),
                        true
                );
                return 0;
            }
            PostUsernameTrainingroomsTrainingroomSessionsStartRequest request = new PostUsernameTrainingroomsTrainingroomSessionsStartRequest();
            request.setUsername(ChaosCraft.config.trainingRoomUsernameNamespace);
            request.setTrainingroom(ChaosCraft.config.trainingRoomNamespace);
            TraningRoomSessionStartRequest traningRoomSessionStartRequest = new TraningRoomSessionStartRequest();

            traningRoomSessionStartRequest.setReset(true);
            request.setTraningRoomSessionStartRequest(traningRoomSessionStartRequest);
            response = ChaosCraft.sdk.postUsernameTrainingroomsTrainingroomSessionsStart(request);

        }catch(ChaosNetException exception){

            context.getSource().sendFeedback(
                    new TranslationTextComponent("Error Status: " + exception.sdkHttpMetadata().httpStatusCode() + " - " + exception.sdkHttpMetadata().responseContent().toString()),
                    false
            );
            return 0;
        }

        context.getSource().sendFeedback(
                new TranslationTextComponent("Finished..." + response.getTrainingRoomSession().getGenomeNamespace() ),
                true
        );
        return 1;
    }
}
