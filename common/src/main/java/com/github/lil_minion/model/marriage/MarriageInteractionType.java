package com.github.lil_minion.model.marriage;

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

    public static MarriageInteractionType fromString(String text) {
        for (MarriageInteractionType interactionType : MarriageInteractionType.values()) {
            if (interactionType.type.equalsIgnoreCase(text)) {
                return interactionType;
            }
        }
        return null;
    }
}
