package com.github.lil_minion.model.mail;

/**
 * Enum representing the different types of mail that can be sent.
 */
public enum MailType {
    MAIL("mail"),
    LOVE_LETTER("love_letter"),
    GOSSIP("gossip");

    private final String type;

    /**
     * Constructs a MailType with the specified type string.
     *
     * @param type The string representation of the mail type.
     */
    MailType(String type) {
        this.type = type;
    }

    /**
     * Returns the string representation of the mail type.
     *
     * @return The type as a string.
     */
    public String getType() {return type;}

    /**
     * Converts a string to its corresponding MailType enum value.
     *
     * @param text The string representation of the mail type.
     * @return The corresponding MailType, or null if no match is found.
     */
    public static MailType fromString(String text) {
        for (MailType mailType : MailType.values()) {
            if (mailType.type.equalsIgnoreCase(text)) {
                return mailType;
            }
        }
        return null;
    }
}
