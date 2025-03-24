package com.github.lil_minion.model.relationship.marriage;

/**
 * Enum representing the different types of interactions that can occur within a marriage.
 * Each interaction type is associated with a string representation.
 */
public enum MarriageInteractionType {
    KISS("kiss"),
    FLIRT("flirt"),
    SLEEP_TOGETHER("sleep_together"),
    GIFT("gift"),
    MAIL("mail");

    private final String type;

    /**
     * Constructs a MarriageInteractionType with the specified string representation.
     *
     * @param type the string representation of the interaction type
     */
    MarriageInteractionType(String type) {
        this.type = type;
    }

    /**
     * Returns the string representation of the interaction type.
     *
     * @return the string representation of this interaction type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the MarriageInteractionType corresponding to the given string.
     *
     * @param text the string representation of the interaction type
     * @return the corresponding MarriageInteractionType, or null if no match is found
     */
    public static MarriageInteractionType fromString(String text) {
        for (MarriageInteractionType interactionType : MarriageInteractionType.values()) {
            if (interactionType.type.equalsIgnoreCase(text)) {
                return interactionType;
            }
        }
        return null;
    }
}
