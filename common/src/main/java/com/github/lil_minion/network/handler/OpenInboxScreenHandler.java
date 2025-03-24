package com.github.lil_minion.network.handler;

import com.github.lil_minion.client.screen.MailScreen;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.network.message.OpenInboxScreenMessage;
import com.github.lil_minion.utils.InboxUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

/**
 * Handles the opening of the inbox screen for players.
 * This class processes messages that request to open the mail inbox screen
 * and displays the corresponding UI to the player.
 */
public class OpenInboxScreenHandler {

    /**
     * Handles the request to open the inbox screen for the specified player.
     *
     * @param openInboxScreenMessage the message containing the inbox data
     * @param player the player who requested to open the inbox screen
     */
    public static void handle(OpenInboxScreenMessage openInboxScreenMessage, Player player) {
        Inbox inbox = InboxUtil.decodeInbox(openInboxScreenMessage, player);
        Minecraft minecraft = Minecraft.getInstance();
        MailScreen mailScreen = new MailScreen(inbox);
        minecraft.setScreen(mailScreen);
    }
}
