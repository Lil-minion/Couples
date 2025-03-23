package com.github.lil_minion.server.data;

public enum MarriageInteractionType {
    KISS("kiss"),
    FLIRT("flirt"),
    SLEEP_TOGETHER("sleep_together"),
    GIFT("gift"),
    MAIL("mail");

    private final String type;

    MarriageInteractionType(String type) {
        this.type = type;
    }

    public String getType() {return type;}
}
