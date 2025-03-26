package com.github.lil_minion.utils.network;

import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.model.mail.Mail;
import com.github.lil_minion.model.mail.MailType;
import com.github.lil_minion.model.relationship.interaction.InteractionRequest;
import com.github.lil_minion.model.relationship.interaction.InteractionRequestType;
import com.github.lil_minion.network.message.InboxMessage;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import com.github.lil_minion.network.message.MailMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageDecoderUtil {
    /**
     * Decodes a MailMessage into a Mail object.
     *
     * @param mailMessage The MailMessage to decode.
     * @return A Mail object representing the decoded message.
     */
    public static Mail decode(MailMessage mailMessage) {
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
     * Decodes an {@link InboxMessage} into an Inbox object.
     *
     * @param inboxMessage The {@link InboxMessage}to decode.
     * @param player       The player associated with the inbox.
     * @return An Inbox object representing the decoded inbox.
     */
    public static Inbox decode(InboxMessage inboxMessage, Player player) {
        Inbox inbox = new Inbox(player.getUUID());

        List<Mail> mails = new ArrayList<>();
        List<MailMessage> mailMessages = inboxMessage.mails();

        mailMessages.forEach(mailMessage -> mails.add(decode(mailMessage)));
        mails.forEach(inbox::addMail);
        return inbox;
    }

    /**
     * Decodes an {@link InteractionRequestMessage} into an {@link InteractionRequest} object.
     *
     * @param request The {@link InteractionRequestMessage} to decode.
     * @return An {@link InteractionRequest}.
     */
    public static InteractionRequest decode(InteractionRequestMessage request) {
        return new InteractionRequest(
                UUID.fromString(request.sender()),
                UUID.fromString(request.recipient()),
                request.timestamp(),
                InteractionRequestType.fromString(request.interactionType()),
                Component.literal(request.message()),
                request.accepted()
        );
    }
}
