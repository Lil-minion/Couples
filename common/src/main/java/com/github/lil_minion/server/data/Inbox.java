package com.github.lil_minion.server.data;

import java.util.*;

public class Inbox {

    private final UUID playerUUID;
    private final List<Mail> mails = new ArrayList<>();
    private final Map<Mail, Boolean> hasBeenReadMap = new HashMap<>();

    public Inbox(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public UUID getPlayerUUID() {return playerUUID;}
    public List<Mail> getMails() {return mails;}

    public void addMail(Mail mail) {
        mails.add(mail);
    }
}
