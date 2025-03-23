package com.github.lil_minion.model.mail;

public enum MailType {
    MAIL("mail"),
    LOVE_LETTER("love_letter"),
    GOSSIP("gossip");

    private final String type;

    MailType(String type) {
        this.type = type;
    }

    public String getType() {return type;}

    public static MailType fromString(String text) {
        for (MailType mailType : MailType.values()) {
            if (mailType.type.equalsIgnoreCase(text)) {
                return mailType;
            }
        }
        return null;
    }
}
