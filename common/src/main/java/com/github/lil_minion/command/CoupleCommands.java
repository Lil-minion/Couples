package com.github.lil_minion.command;

import com.github.lil_minion.server.data.Marriage;
import com.github.lil_minion.server.data.MarriageData;
import com.github.lil_minion.server.data.MarriageInteraction;
import com.github.lil_minion.server.data.MarriageInteractionType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

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
        // Todo Implement logic
        Player playerSource = context.getSource().getPlayer();
        Player playerTarget = context.getArgument("Player", Player.class);

        // If playerSource is married.
        if (MarriageData.MARRIAGE_MAP.containsKey(playerSource.getUUID())) {
            Marriage marriage = MarriageData.MARRIAGE_MAP.get(playerSource.getUUID());
            UUID marriedPlayer1 = marriage.getPlayer1();
            UUID marriedPlayer2 = marriage.getPlayer2();

            // If playerSource is married to playerTarget.
            if (marriedPlayer1.equals(playerTarget.getUUID()) && marriedPlayer2.equals(playerTarget.getUUID())) {
                marriage.setHearths(marriage.getHearths() + 1);
                marriage.setTimesKissed(marriage.getTimesKissed() + 1);

                // Create a MarriageInteraction to log the interaction.
                MarriageInteraction marriageInteraction = new MarriageInteraction(MarriageInteractionType.KISS,
                        playerSource.level().getGameTime(), 1);
                marriage.getInteractionsList().add(marriageInteraction);

                // Update the MARRIAGE_MAP and save the changes on it.
                MarriageData.MARRIAGE_MAP.put(playerSource.getUUID(), marriage);
                MarriageData.MARRIAGE_MAP.put(playerTarget.getUUID(), marriage);
                MarriageData.INSTANCE.setDirty();

            } else {/* TODO implement logic if not married to target player. -100 <3 divorcio */}
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
        // Todo Implement logic
        return 1;
    }

}
