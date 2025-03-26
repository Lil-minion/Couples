package com.github.lil_minion.utils.network;

import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.model.mail.Mail;
import com.github.lil_minion.model.relationship.interaction.InteractionRequest;
import com.github.lil_minion.network.message.InboxMessage;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import com.github.lil_minion.network.message.MailMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageEncoderUtil {
    /**
     * Creates a {@link InteractionRequestMessage} from a {@link InteractionRequest}.
     *
     * @param request The {@link InteractionRequest} object to convert.
     * @return A {@link InteractionRequestMessage} representing the provided {@link InteractionRequest}.
     */
    public static InteractionRequestMessage encode(InteractionRequest request) {
        return new InteractionRequestMessage(
                request.sender().toString(),
                request.recipient().toString(),
                request.timeStamp(),
                request.interactionRequestType().getType(),
                request.message().getString(),
                request.accepted());
    }

    /**
     * Creates an {@link InboxMessage} with the specified {@link Inbox}.
     *
     * @param inbox The {@link Inbox} object to send.
     * @return An {@link InboxMessage} containing the {@link Mail} messages.
     */
    public static InboxMessage encode(Inbox inbox) {
        List<Mail> mails = inbox.getMails();
        List<MailMessage> mailMessages = new ArrayList<>();

        mails.forEach(mail -> mailMessages.add(MessageEncoderUtil.encode(mail, false)));
        return new InboxMessage(mailMessages);
    }

    /**
     * Creates a {@link MailMessage} from a {@link Mail} object.
     *
     * @param mail     The {@link Mail} object to convert.
     * @param toServer Indicates if the message is to be sent to the server.
     * @return A {@link MailMessage} representing the provided Mail.
     */
    public static MailMessage encode(Mail mail, boolean toServer) {
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
}
