package com.github.lil_minion.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

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
