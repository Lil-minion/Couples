package com.github.lil_minion.command;

import com.github.lil_minion.model.mail.Mail;
import com.github.lil_minion.model.mail.MailType;
import com.github.lil_minion.model.relationship.interaction.InteractionRequestType;
import com.github.lil_minion.model.relationship.marriage.MarriageInteraction;
import com.github.lil_minion.model.relationship.marriage.MarriageInteractionType;
import com.github.lil_minion.model.relationship.marriage.Marriage;
import com.github.lil_minion.server.data.MarriageSavedData;
import com.github.lil_minion.utils.ChatUtil;
import com.github.lil_minion.utils.InboxUtil;
import com.github.lil_minion.utils.InteractionUtil;
import com.github.lil_minion.utils.MarriageUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;


/**
 * Handles commands related to couple interactions, such as flirting, kissing, sending mail, and divorcing.
 * <p>
 * This class provides methods to register commands and execute actions that involve player interactions
 * in the context of relationships, including managing marriage proposals and mail.
 * </p>
 */
public class CoupleCommand {

    /**
     * Registers the couple command and subcommands with the given command dispatcher.
     *
     * @param dispatcher       The command dispatcher to register the commands with.
     * @param context          The command build context.
     * @param commandSelection The command selection type.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("couples")
                .then(Commands.literal("flirt")
                        .then(Commands.argument("Player", EntityArgument.player())
                                .then(Commands.argument("Message", MessageArgument.message())
                                        .executes(CoupleCommand::flirt)))

                ).then(Commands.literal("kiss")
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(commandContext ->
                                        CoupleCommand.kiss(commandContext, true))
                                .then(Commands.argument("Message", MessageArgument.message())
                                        .executes(commandContext ->
                                                CoupleCommand.kiss(commandContext, false))))

                ).then(Commands.literal("mail")
                        .executes(CoupleCommand::mail)

                ).then(Commands.literal("sendmail")
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(CoupleCommand::sendmail))

                ).then(Commands.literal("divorce")
                        .executes(CoupleCommand::divorce))
        );
    }

    // Todo handle when commands run in the console

    /**
     * Executes the flirt command, allowing a player to flirt with another player.
     *
     * @param context The command context containing the source and arguments.
     * @return An integer indicating the result of the command execution:
     * <ul>
     * <li>1 if the command was successful.</li>
     * <li>0 if the command was not successful.</li>
     * </ul>
     * @throws CommandSyntaxException If there is an issue with the command syntax.
     */
    private static int flirt(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player playerSource = context.getSource().getPlayer();
        Player playerTarget = EntityArgument.getPlayer(context, "Player");
        Component message = ChatUtil.createPlayerMessageComponent(playerSource, MessageArgument.getMessage(context, "Message"), ": ");

        boolean sendMessage = false;

        assert playerSource != null;
        if (MarriageUtil.isMarried(playerSource)) {
            Marriage marriage = MarriageSavedData.MARRIAGE_MAP.get(playerSource.getUUID());

            // If playerSource is married to playerTarget.
            if (marriage.isPlayerInRelationship(playerTarget)) {
                MarriageInteraction marriageInteraction = new MarriageInteraction(MarriageInteractionType.FLIRT,
                        playerSource.level().getGameTime(), 0);
                marriage.getInteractionsList().add(marriageInteraction);
                MarriageUtil.updateMarriage(marriage);

                playerTarget.displayClientMessage(
                        ChatUtil.createPlayerMessageComponent(playerSource, message, ": "), false
                );

            } else {
                marriage.setHearths(marriage.getHearths() - 10);
                MarriageUtil.updateMarriage(marriage);
                // Todo implement logic for sending rumor to spouse

                sendMessage = true;
            }
        } else {
            sendMessage = true;
        }


        if (sendMessage) {
            if (playerTarget instanceof ServerPlayer serverPlayerTarget) {
                InteractionUtil.interact(playerSource, serverPlayerTarget, message, InteractionRequestType.FLIRT_REQUEST);
            }
        }


        return 1;
    }

    /**
     * Executes the kiss command, allowing a player to kiss another player.
     *
     * @param context The command context containing the source and arguments.
     * @return An integer indicating the result of the command execution:
     * <ul>
     * <li>1 if the command was successful.</li>
     * <li>0 if the command was not successful.</li>
     * </ul>
     * @throws CommandSyntaxException If there is an issue with the command syntax.
     */
    private static int kiss(CommandContext<CommandSourceStack> context, boolean useDefault) throws CommandSyntaxException {
        Player playerSource = context.getSource().getPlayer();
        Player playerTarget = EntityArgument.getPlayer(context, "Player");

        // Try to assign message or use default
        Component message;
        if (useDefault){
            message = ChatUtil.createPlayerMessageComponent(playerSource, Component.translatable("messages.couples.kiss_me"), " ");
        } else{
            message = ChatUtil.createPlayerMessageComponent(playerSource, MessageArgument.getMessage(context, "Message"), ": ");
        }


        boolean sendMessage = false;

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

                sendMessage = true;
            }
        } else {
            sendMessage = true;
        }

        if (sendMessage) {
            if (playerTarget instanceof ServerPlayer serverPlayerTarget) {
                InteractionUtil.interact(playerSource, serverPlayerTarget, message, InteractionRequestType.KISS_REQUEST);
            }
        }

        return 1;
    }

    /**
     * Executes the mail command, allowing players to access their mail.
     *
     * @param context The command context containing the source and arguments.
     * @return An integer indicating the result of the command execution:
     * <ul>
     * <li>1 if the command was successful.</li>
     * <li>0 if the command was not successful.</li>
     * </ul>
     */
    private static int mail(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        if (player instanceof ServerPlayer serverPlayer) {
            InboxUtil.openInboxScreen(serverPlayer);
            return 1;
        }

        return 0;
    }

    /**
     * Executes the sendmail command, allowing a player to send a mail to another player.
     *
     * @param context The command context containing the source and arguments.
     * @return An integer indicating the result of the command execution:
     * <ul>
     * <li>1 if the command was successful.</li>
     * <li>0 if the command was not successful.</li>
     * </ul>
     * @throws CommandSyntaxException If there is an issue with the command syntax.
     */
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

    /**
     * Executes the divorce command, allowing a player to divorce their spouse.
     *
     * @param context The command context containing the source and arguments.
     * @return An integer indicating the result of the command execution:
     * <ul>
     * <li>1 if the command was successful.</li>
     * <li>0 if the command was not successful.</li>
     * </ul>
     */
    private static int divorce(CommandContext<CommandSourceStack> context) {
        Player playerSource = context.getSource().getPlayer();
        if (!MarriageUtil.divorce(playerSource)) {
            Component message = Component.translatable("messages.couples.not_married");
            playerSource.displayClientMessage(message, false);
        }
        return 1;
    }

}
