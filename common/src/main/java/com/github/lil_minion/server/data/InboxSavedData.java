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
        INSTANCE = server.overworld().getDataStorage().computeIfAbsent(new Factory<>(InboxSavedData::create, InboxSavedData::load, DataFixTypes.LEVEL), Couples.MOD_ID + ".inbox_data");
        INSTANCE.setDirty();
    }

    public static InboxSavedData create() {
        return new InboxSavedData();
    }

    public static InboxSavedData load(CompoundTag tag, HolderLookup.Provider registryLookup) {
        PLAYER_INBOX_MAP.clear();
        CompoundTag compoundTag = tag.getCompound("couples.mail_data");
        loadInboxes(compoundTag);
        return new InboxSavedData();
    }

    private static void loadInboxes(CompoundTag compoundTag) {
        for (String key : compoundTag.getAllKeys()) {
            CompoundTag inboxTag = compoundTag.getCompound(key);
            UUID playerUUID = inboxTag.getUUID("player");
            Inbox inbox = loadInbox(inboxTag);
            PLAYER_INBOX_MAP.put(playerUUID, inbox);
        }
    }

    private static Inbox loadInbox(CompoundTag inboxTag) {
        UUID playerUUID = inboxTag.getUUID("player");
        CompoundTag mailListTag = inboxTag.getCompound("mails");
        Inbox inbox = new Inbox(playerUUID);

        for (String mailKey : mailListTag.getAllKeys()) {
            CompoundTag mailTag = mailListTag.getCompound(mailKey);
            Mail mail = loadMail(mailTag);
            inbox.getMails().add(mail);
        }

        return inbox;
    }

    private static Mail loadMail(CompoundTag mailTag) {
        UUID sender = mailTag.getUUID("sender");
        UUID recipient = mailTag.getUUID("recipient");
        MailType type = MailType.fromString(mailTag.getString("type"));
        Long timestamp = mailTag.getLong("timestamp");
        List<Component> message = loadMailMessage(mailTag.getCompound("pages"));

        return new Mail(sender, recipient, type, timestamp, message);
    }

    private static List<Component> loadMailMessage(CompoundTag pagesTag) {
        List<Component> message = new ArrayList<>();
        for (String page : pagesTag.getAllKeys()) {
            message.add(Component.literal(pagesTag.getString(page)));
        }
        return message;
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        CompoundTag compoundTag = new CompoundTag();
        saveInboxes(compoundTag);
        tag.put("couples.mail_data", compoundTag);
        return tag;
    }

    private void saveInboxes(CompoundTag compoundTag) {
        for (Inbox inbox : PLAYER_INBOX_MAP.values()) {
            CompoundTag inboxTag = saveInbox(inbox);
            compoundTag.put(inbox.getPlayerUUID().toString(), inboxTag);
        }
    }

    private CompoundTag saveInbox(Inbox inbox) {
        CompoundTag inboxTag = new CompoundTag();
        inboxTag.putUUID("player", inbox.getPlayerUUID());
        CompoundTag mailsTag = new CompoundTag();

        int i = 0;
        for (Mail mail : inbox.getMails()) {
            CompoundTag mailTag = saveMail(mail);
            mailsTag.put("" + i, mailTag);
            i++;
        }
        inboxTag.put("mails", mailsTag);
        return inboxTag;
    }

    private CompoundTag saveMail(Mail mail) {
        CompoundTag mailTag = new CompoundTag();
        mailTag.putUUID("sender", mail.sender());
        mailTag.putUUID("recipient", mail.recipient());
        mailTag.putString("type", mail.type().getType());
        mailTag.putLong("timestamp", mail.timestamp());
        mailTag.put("pages", saveMailMessage(mail.message()));
        return mailTag;
    }

    private CompoundTag saveMailMessage(List<Component> message) {
        CompoundTag pagesTag = new CompoundTag();
        int j = 0;
        for (Component component : message) {
            pagesTag.putString("" + j, component.getString());
            j++;
        }
        return pagesTag;
    }

}