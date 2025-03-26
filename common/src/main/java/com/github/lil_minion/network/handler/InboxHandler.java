package com.github.lil_minion.network.handler;

import com.github.lil_minion.client.data.InboxVolatileData;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.network.message.InboxMessage;
import com.github.lil_minion.utils.network.MessageDecoderUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * Handles sending the inbox to players.
 * and displays the corresponding UI to the player.
 */
public class InboxHandler {

    public static void handle(InboxMessage inboxMessage, Player player) {
        if (player instanceof LocalPlayer localPlayer) {
            Inbox inbox = MessageDecoderUtil.decode(inboxMessage, localPlayer);
            if (InboxVolatileData.getClientInbox() == null) {
                InboxVolatileData.setClientInbox(inbox);
            }
        }
    }
}
