package com.github.lil_minion.utils.network;

import com.github.lil_minion.uploaded.UploadedMethods;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.model.mail.Mail;
import com.github.lil_minion.model.relationship.interaction.InteractionRequest;
import com.github.lil_minion.network.message.CommandMessage;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import com.github.lil_minion.server.data.InboxSavedData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class MessageSenderUtil {
    /**
     * Sends a mail message to the specified player and stores it in their inbox.
     *
     * @param player   The player to send the mail to.
     * @param mail     The Mail object to send.
     */
    public static void sendMail(ServerPlayer player, Mail mail) {
        UploadedMethods.sendMessageToClient(player, MessageEncoderUtil.encode(mail));
    }

    /**
     * Opens the inbox screen for the specified player.
     *
     * @param player The player whose inbox screen is to be opened.
     */
    public static void openInboxScreen(ServerPlayer player) {
        UploadedMethods.sendMessageToClient(player, new CommandMessage("gui.inbox"));
    }

    public static void sendInbox(ServerPlayer player) {
        Inbox inbox = InboxSavedData.PLAYER_INBOX_MAP.getOrDefault(player.getUUID(), new Inbox(player.getUUID()));
        UploadedMethods.sendMessageToClient(player, MessageEncoderUtil.encode(inbox));
    }

    /**
     * Sends an interaction request message to the specified player.
     *
     * @param player  The {@link Player} to send the interaction request to.
     * @param request The {@link InteractionRequest} to send.
     */
    public static void sendInteractionRequest(Player player, InteractionRequest request) {
        InteractionRequestMessage message = MessageEncoderUtil.encode(request);
        if (player instanceof ServerPlayer serverPlayer) {
            UploadedMethods.sendMessageToClient(serverPlayer, message);
        } else {
            UploadedMethods.sendMessageToServer(message);
        }
    }

    public static void clearInbox(ServerPlayer player) {
        UploadedMethods.sendMessageToClient(player, new CommandMessage("inbox.clear"));
    }
}
