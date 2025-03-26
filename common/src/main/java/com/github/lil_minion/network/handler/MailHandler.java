package com.github.lil_minion.network.handler;

import com.github.lil_minion.client.data.InboxVolatileData;
import com.github.lil_minion.model.mail.Mail;
import com.github.lil_minion.network.message.MailMessage;
import com.github.lil_minion.utils.InboxUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

/**
 * Handles the processing of mail messages sent between players.
 * This class is responsible for notifying players when they receive mail.
 */
public class MailHandler {

    /**
     * Handles the received mail message for the specified player.
     *
     * @param mailMessage the mail message to be handled
     * @param player      the player who received the mail message
     */
    public static void handle(MailMessage mailMessage, Player player) {
        Mail mail = InboxUtil.decodeMailMessage(mailMessage);

        if (player instanceof LocalPlayer localPlayer) {
            //  When mail is received by player notify player
            localPlayer.displayClientMessage(Component.translatable("messages.couples.you_got_mail"), false);
            InboxVolatileData.getClientInbox().addMail(mail);
        }
    }
}
