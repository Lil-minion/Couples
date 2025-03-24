package com.github.lil_minion.command;

import com.github.lil_minion.client.screen.MailScreen;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.model.mail.Mail;
import com.github.lil_minion.model.mail.MailType;
import com.github.lil_minion.model.relationship.marriage.MarriageInteraction;
import com.github.lil_minion.model.relationship.marriage.MarriageInteractionType;
import com.github.lil_minion.model.relationship.marriage.Marriage;
import com.github.lil_minion.server.data.InboxSavedData;
import com.github.lil_minion.server.data.MarriageSavedData;
import com.github.lil_minion.utils.InboxUtil;
import com.github.lil_minion.utils.MarriageUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;

public class CoupleCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("couples")
                .then(Commands.literal("flirt")
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(CoupleCommand::flirt))

                ).then(Commands.literal("kiss")
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(CoupleCommand::kiss))

                ).then(Commands.literal("mail")
                        .executes(CoupleCommand::mail)

                ).then(Commands.literal("sendmail")
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(CoupleCommand::sendmail))

                ).then(Commands.literal("divorce")
                        .executes(CoupleCommand::divorce))
        );
    }

    private static int flirt(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player playerSource = context.getSource().getPlayer();
        Player playerTarget = EntityArgument.getPlayer(context,"Player");

        // Todo on interaction accept: romance.setHearths(romance.getHearths() + 1);

        if (MarriageUtil.isMarried(playerSource)) {
            Marriage marriage = MarriageSavedData.MARRIAGE_MAP.get(playerSource.getUUID());

            // If playerSource is married to playerTarget.
            if (marriage.isPlayerInRelationship(playerTarget)) {
                MarriageInteraction marriageInteraction = new MarriageInteraction(MarriageInteractionType.FLIRT,
                        playerSource.level().getGameTime(), 0);
                marriage.getInteractionsList().add(marriageInteraction);
                MarriageUtil.updateMarriage(marriage);
            } else {
                marriage.setHearths(marriage.getHearths() - 10);
                MarriageUtil.updateMarriage(marriage);
                // Todo implement logic for sending rumor to spouse
                // Todo implement logic for sending flirt request to target


            }
        } else {
            // Todo implement logic for sending rumor to spouse
            // Todo implement logic for sending flirt request to target
        }
        return 1;
    }

    private static int kiss(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player playerSource = context.getSource().getPlayer();
        Player playerTarget = EntityArgument.getPlayer(context, "Player");

        // If playerSource is married.
        if (MarriageUtil.isMarried(playerSource)) {
            Marriage marriage = MarriageSavedData.MARRIAGE_MAP.get(playerSource.getUUID());

            // If playerSource is married to playerTarget.
            if (marriage.isPlayerInRelationship(playerTarget)) {
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
        Player player = context.getSource().getPlayer();
        if (player instanceof ServerPlayer serverPlayer) {
            InboxUtil.openInboxScreen(serverPlayer);
            return 1;
        }

        return 0;
    }

    private static int sendmail(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        ItemStack mainHandStack = player.getMainHandItem();
        if (mainHandStack.getItem().equals(Items.WRITTEN_BOOK)) {
            if (player instanceof ServerPlayer serverPlayer) {

                // Get content from book in hand and write the format the MailMessage class requires
                WrittenBookContent content = mainHandStack.getComponents().get(DataComponents.WRITTEN_BOOK_CONTENT);
                List<Component> messageOriginal = content.getPages(false);

                // Create Mail
                Player target = EntityArgument.getPlayer(context, "Player");
                Mail mail = new Mail(
                        serverPlayer.getUUID(),
                        target.getUUID(),
                        MailType.MAIL,
                        context.getSource().getLevel().getGameTime(),
                        messageOriginal
                );

                // Send mail, remove book, and send mail_sent message to target
                InboxUtil.sendMail(serverPlayer, mail, false);
                mainHandStack.setCount(0);
                player.displayClientMessage(Component.translatable("messages.couples.mail_sent"), false);
            }
            return 1;
        } else {
            player.displayClientMessage(Component.translatable("messages.couples.need_written_book"), false);
        }

        return 0;
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
