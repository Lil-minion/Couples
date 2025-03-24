package com.github.lil_minion.server.data;

import com.github.lil_minion.Couples;
import com.github.lil_minion.model.mail.Inbox;
import com.github.lil_minion.model.mail.Mail;
import com.github.lil_minion.model.mail.MailType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class InboxSavedData extends SavedData {

    public static final Map<UUID, Inbox> PLAYER_INBOX_MAP = new HashMap<>();

    public static SavedData INSTANCE;

    public static void createServerState(MinecraftServer server) {
        INSTANCE = server.overworld().getDataStorage().computeIfAbsent(
                new Factory<>(InboxSavedData::create, InboxSavedData::load,
                        DataFixTypes.LEVEL), Couples.MOD_ID + ".inbox_data");
        INSTANCE.setDirty();
    }

    public static InboxSavedData create() {
        return new InboxSavedData();
    }

    public static InboxSavedData load(CompoundTag tag, HolderLookup.Provider registryLookup) {
        PLAYER_INBOX_MAP.clear();
        CompoundTag compoundTag = tag.getCompound("couples.mail_data");

        for (String key : compoundTag.getAllKeys()) {
            CompoundTag inboxTag = compoundTag.getCompound(key);

            UUID playerUUID = inboxTag.getUUID("player");

            CompoundTag mailListTag = inboxTag.getCompound("mails");
            List<Mail> mailList = new ArrayList<>();

            Inbox inbox = new Inbox(playerUUID);
            for (String mailKey : mailListTag.getAllKeys()) {
                CompoundTag mailTag = mailListTag.getCompound(mailKey);
                UUID sender = mailTag.getUUID("sender");
                UUID recipient = mailTag.getUUID("recipient");
                MailType type = MailType.fromString(mailTag.getString("type"));
                Long timestamp = mailTag.getLong("timestamp");

                CompoundTag pagesTag = mailTag.getCompound("pages");
                List<Component> message = new ArrayList<>();
                for (String page : pagesTag.getAllKeys()) {
                    message.add(Component.literal(page));
                }

                Mail mail = new Mail(sender, recipient, type, timestamp, message);
                inbox.getMails().add(mail);
            }
            PLAYER_INBOX_MAP.put(playerUUID, inbox);
        }

        return new InboxSavedData();
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        CompoundTag compoundTag = new CompoundTag();

        for (Inbox inbox : PLAYER_INBOX_MAP.values()) {
            CompoundTag inboxTag = new CompoundTag();
            inboxTag.putUUID("player", inbox.getPlayerUUID());

            CompoundTag mailsTag = new CompoundTag();
            int i = 0;
            for (Mail mail : inbox.getMails()) {
                CompoundTag mailTag = new CompoundTag();
                mailTag.putUUID("sender", mail.sender());
                mailTag.putUUID("recipient", mail.recipient());
                mailTag.putString("type", mail.type().getType());
                mailTag.putLong("timestamp", mail.timestamp());

                // Add message
                CompoundTag pagesTag = new CompoundTag();
                int j = 0;
                for (Component component : mail.message()) {
                    pagesTag.putString("" + j, component.getString());
                    j++;
                }

                mailTag.put("pages", pagesTag);

                mailsTag.put("" + i, mailTag);
                i++;
            }
            inboxTag.put("mails", mailsTag);

            compoundTag.put(inbox.getPlayerUUID().toString(), inboxTag);
        }

        tag.put("couples.mail_data", compoundTag);
        return tag;
    }

}
