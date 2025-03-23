package com.github.lil_minion.utils;

import com.github.lil_minion.ModLoaderMethods;
import com.github.lil_minion.network.message.MailMessage;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.server.data.InboxSavedData;
import com.github.lil_minion.model.mail.Mail;
import com.github.lil_minion.model.mail.MailType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InboxUtil {

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

    public static void sendMail(ServerPlayer player, Mail mail, boolean toServer) {
        ModLoaderMethods.sendMessageToClient(player, InboxUtil.createMessage(mail, toServer));
        storeMail(mail);
    }

    public static void storeMail(Mail mail) {
        Inbox inbox;
        inbox = InboxSavedData.PLAYER_INBOX_MAP.getOrDefault(mail.recipient(), new Inbox(mail.recipient()));
        inbox.addMail(mail);
        InboxSavedData.PLAYER_INBOX_MAP.put(mail.recipient(), inbox);
        InboxSavedData.INSTANCE.setDirty();
    }

}
