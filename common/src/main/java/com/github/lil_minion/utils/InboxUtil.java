package com.github.lil_minion.utils;

import com.github.lil_minion.ModLoaderMethods;
import com.github.lil_minion.network.message.MailMessage;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.network.message.OpenInboxScreenMessage;
import com.github.lil_minion.server.data.InboxSavedData;
import com.github.lil_minion.model.mail.Mail;
import com.github.lil_minion.model.mail.MailType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Utility class for managing mail operations, including creating, sending,
 * and decoding mail messages, as well as managing the inbox.
 */
public class InboxUtil {

    /**
     * Creates a MailMessage from a Mail object.
     *
     * @param mail The Mail object to convert.
     * @param toServer Indicates if the message is to be sent to the server.
     * @return A MailMessage representing the provided Mail.
     */
    public static MailMessage createMessage(Mail mail, boolean toServer) {
        List<String> message = new ArrayList<>();
        mail.message().forEach(component -> message.add(component.getString()));
        return new MailMessage(
                Optional.of(mail.sender().toString()),
                mail.recipient().toString(),
                mail.type().getType(),
                mail.timestamp(),
                message,
                toServer
        );
    }

    /**
     * Decodes a MailMessage into a Mail object.
     *
     * @param mailMessage The MailMessage to decode.
     * @return A Mail object representing the decoded message.
     */
    public static Mail decodeMailMessage(MailMessage mailMessage) {
        UUID senderUUID = UUID.fromString(mailMessage.sender().orElse(null));
        UUID recipientUUID = UUID.fromString(mailMessage.recipient());
        MailType type = MailType.fromString(mailMessage.mailType());

        List<Component> messageList = new ArrayList<>();
        for (String messageStr : mailMessage.message()) {
            messageList.add(Component.literal(messageStr));
        }

        return new Mail(senderUUID, recipientUUID, type, mailMessage.timestamp(), messageList);
    }

    /**
     * Sends a mail message to the specified player and stores it in their inbox.
     *
     * @param player The player to send the mail to.
     * @param mail The Mail object to send.
     * @param toServer Indicates if the message is to be sent to the server.
     */
    public static void sendMail(ServerPlayer player, Mail mail, boolean toServer) {
        ModLoaderMethods.sendMessageToClient(player, InboxUtil.createMessage(mail, toServer));
        storeMail(mail);
    }

    /**
     * Stores the given mail in the recipient's inbox.
     *
     * @param mail The Mail object to store.
     */
    public static void storeMail(Mail mail) {
        Inbox inbox;
        inbox = InboxSavedData.PLAYER_INBOX_MAP.getOrDefault(mail.recipient(), new Inbox(mail.recipient()));
        inbox.addMail(mail);
        InboxSavedData.PLAYER_INBOX_MAP.put(mail.recipient(), inbox);
        InboxSavedData.INSTANCE.setDirty();
    }

    /**
     * Opens the inbox screen for the specified player.
     *
     * @param player The player whose inbox screen is to be opened.
     */
    public static void openInboxScreen(ServerPlayer player) {
        Inbox inbox = InboxSavedData.PLAYER_INBOX_MAP.getOrDefault(player.getUUID(), new Inbox(player.getUUID()));
        ModLoaderMethods.sendMessageToClient(player, InboxUtil.createOpenInboxScreenRequest(inbox));
    }

    /**
     * Creates a request to open the MailScreen with the specified {@link Inbox}.
     *
     * @param inbox The Inbox object to create the request for.
     * @return An OpenInboxScreenMessage containing the mail messages.
     */
    public static OpenInboxScreenMessage createOpenInboxScreenRequest(Inbox inbox) {
        List<MailMessage> mailMessages = new ArrayList<>();
        List<Mail> mails = inbox.getMails();

        mails.forEach(mail -> mailMessages.add(createMessage(mail, false)));
        return new OpenInboxScreenMessage(mailMessages);
    }

    /**
     * Decodes an OpenInboxScreenMessage into an Inbox object.
     *
     * @param openInboxScreenMessage The OpenInboxScreenMessage to decode.
     * @param player The player associated with the inbox.
     * @return An Inbox object representing the decoded inbox.
     */
    public static Inbox decodeInbox(OpenInboxScreenMessage openInboxScreenMessage, Player player) {
        Inbox inbox = new Inbox(player.getUUID());

        List<Mail> mails = new ArrayList<>();
        List<MailMessage> mailMessages = openInboxScreenMessage.mails();

        mailMessages.forEach(mailMessage -> mails.add(InboxUtil.decodeMailMessage(mailMessage)));
        mails.forEach(inbox::addMail);
        return inbox;
    }

}
