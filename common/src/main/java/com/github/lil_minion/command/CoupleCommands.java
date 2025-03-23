package com.github.lil_minion.command;

import com.github.lil_minion.server.data.Marriage;
import com.github.lil_minion.server.data.MarriageData;
import com.github.lil_minion.server.data.MarriageInteraction;
import com.github.lil_minion.server.data.MarriageInteractionType;
import com.github.lil_minion.utils.MarriageUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class CoupleCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("couples")
                .then(Commands.literal("flirt")
                        .then(Commands.argument("Player", EntityArgument.player()))
                        .executes(CoupleCommands::flirt)

                ).then(Commands.literal("kiss")
                        .then(Commands.argument("Player", EntityArgument.player()))
                        .executes(CoupleCommands::kiss)

                ).then(Commands.literal("mail")
                        .executes(CoupleCommands::mail)

                ).then(Commands.literal("sendmail")
                        .then(Commands.argument("Player", EntityArgument.player()))
                        .executes(CoupleCommands::sendmail)

                ).then(Commands.literal("divorce")
                        .executes(CoupleCommands::divorce))
        );
    }

    private static int flirt(CommandContext<CommandSourceStack> context) {
        // Todo Implement logic
        return 1;
    }

    private static int kiss(CommandContext<CommandSourceStack> context) {
        Player playerSource = context.getSource().getPlayer();
        Player playerTarget = context.getArgument("Player", Player.class);

        // If playerSource is married.
        if (MarriageUtil.isMarried(playerSource)) {
            Marriage marriage = MarriageData.MARRIAGE_MAP.get(playerSource.getUUID());

            // If playerSource is married to playerTarget.
            if (marriage.isPlayerInMarriage(playerTarget)) {
                marriage.setHearths(marriage.getHearths() + 1);
                marriage.setTimesKissed(marriage.getTimesKissed() + 1);

                // Create a MarriageInteraction to log the interaction.
                MarriageInteraction marriageInteraction = new MarriageInteraction(MarriageInteractionType.KISS,
                        playerSource.level().getGameTime(), 1);
                marriage.getInteractionsList().add(marriageInteraction);

                MarriageUtil.updateMarriage(marriage);
            } else {
                // Remove 15 hearths because of cheating rumors
                marriage.setHearths(marriage.getHearths() - 15);
                MarriageUtil.updateMarriage(marriage);

                // Todo implement logic for sending rumor to spouse
                // Todo implement logic for sending kiss request to target
            }
        } else {/* TODO implement logic if player is not married */}
        return 1;
    }

    private static int mail(CommandContext<CommandSourceStack> context) {
        // Todo Implement logic
        return 1;
    }

    private static int sendmail(CommandContext<CommandSourceStack> context) {
        // Todo Implement logic
        return 1;
    }

    private static int divorce(CommandContext<CommandSourceStack> context) {
        Player playerSource = context.getSource().getPlayer();
        if (!MarriageUtil.divorce(playerSource)) {
            Component message = Component.translatable("messages.couples.not_married");
            playerSource.displayClientMessage(message, false);
        }
        return 1;
    }

}
