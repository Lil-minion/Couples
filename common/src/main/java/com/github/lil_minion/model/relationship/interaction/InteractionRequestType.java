package com.github.lil_minion.model.relationship.interaction;

/**
 * Enum representing the different types of interaction requests that can occur between 2 players.
 * Each interaction request type is associated with a string representation.
 */
public enum InteractionRequestType {
    MARRIAGE_PROPOSAL("marriage_proposal"),
    FLIRT_REQUEST("flirt_request"),
    KISS_REQUEST("kiss_request");

    private final String type;

    /**
     * Constructs a InteractionRequestType with the specified string representation.
     *
     * @param type the string representation of the interaction type
     */
    InteractionRequestType(String type) {
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
     * Returns the InteractionRequestType corresponding to the given string.
     *
     * @param text the string representation of the interaction type
     * @return the corresponding InteractionRequestType, or null if no match is found
     */
    public static InteractionRequestType fromString(String text) {
        for (InteractionRequestType interactionType : InteractionRequestType.values()) {
            if (interactionType.type.equalsIgnoreCase(text)) {
                return interactionType;
            }
        }
        return null;
    }
}
