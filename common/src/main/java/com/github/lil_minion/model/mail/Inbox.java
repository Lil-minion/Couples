package com.github.lil_minion.model.mail;

import java.util.*;

/**
 * Represents an inbox for a player, containing a list of mails.
 */
public class Inbox {

    private final UUID playerUUID;
    private final List<Mail> mails = new ArrayList<>();
    private final Map<Mail, Boolean> hasBeenReadMap = new HashMap<>();

    /**
     * Constructs a new Inbox for a player with the specified UUID.
     *
     * @param playerUUID The UUID of the player associated with this inbox.
     */
    public Inbox(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    /**
     * Gets the player UUID associated with this inbox.
     *
     * @return The UUID of the player.
     */
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    /**
     * Gets the list of mails in the inbox.
     *
     * @return A list of {@link Mail} objects contained in the inbox.
     */
    public List<Mail> getMails() {
        return mails;
    }

    /**
     * Adds a new mail to the inbox.
     *
     * @param mail The {@link Mail} object to be added to the inbox.
     */
    public void addMail(Mail mail) {
        mails.add(mail);
    }

    /**
     * Adds a list of new mails to the inbox.
     *
     * @param mails The {@link List} of {@link Mail} objects to be added to the inbox.
     */
    public void addMail(List<Mail> mails) {
        mails.forEach(this::addMail);
    }
}
