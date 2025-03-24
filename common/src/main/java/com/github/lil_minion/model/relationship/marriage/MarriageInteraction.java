package com.github.lil_minion.model.relationship.marriage;

/**
 * Represents an interaction that occurs within a marriage.
 * This class captures the type of interaction, the time it occurred,
 * and the change in hearts as a result of the interaction.
 */
public class MarriageInteraction {

    private final MarriageInteractionType interactionType;
    private final long timeOfInteraction;
    private final int hearthsChanged;

    /**
     * Constructs a MarriageInteraction instance with the specified parameters.
     *
     * @param type the type of interaction that occurred {@link MarriageInteractionType}
     * @param timeOfInteraction the time (in ticks) when the interaction took place
     * @param hearthsChanged the number of hearts changed as a result of the interaction
     */
    public MarriageInteraction(MarriageInteractionType type, long timeOfInteraction, int hearthsChanged) {
        this.interactionType = type;
        this.timeOfInteraction = timeOfInteraction;
        this.hearthsChanged = hearthsChanged;
    }

    /**
     * Returns the type of interaction that occurred.
     *
     * @return the MarriageInteractionType of this interaction
     */
    public MarriageInteractionType getInteractionType() {
        return interactionType;
    }

    /**
     * Returns the time of interaction.
     *
     * @return the time of interaction in ticks
     */
    public long getTimeOfInteraction() {
        return timeOfInteraction;
    }

    /**
     * Returns the number of hearts changed as a result of the interaction.
     *
     * @return the number of hearts changed
     */
    public int getHearthsChanged() {
        return hearthsChanged;
    }
}
