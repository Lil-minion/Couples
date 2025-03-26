package com.github.lil_minion.network.handler;

import com.github.lil_minion.client.data.InboxVolatileData;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.network.message.InboxMessage;
import com.github.lil_minion.utils.InboxUtil;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * Handles sending the inbox to players.
 * and displays the corresponding UI to the player.
 */
public class InboxHandler {

    public static void handle(InboxMessage inboxMessage, Player player) {
        if (player instanceof LocalPlayer localPlayer) {
            Inbox inbox = InboxUtil.decodeInbox(inboxMessage, localPlayer);
            if (InboxVolatileData.getClientInbox() == null) {
                InboxVolatileData.setClientInbox(inbox);
            }
        }
    }
}
