package com.github.lil_minion.server.data;

public class MarriageInteraction {

    private final MarriageInteractionType interactionType;
    private final long timeOfInteraction;
    private final int hearthsChanged;

    public MarriageInteraction(MarriageInteractionType type, long timeOfInteraction, int hearthsChanged) {
        this.interactionType = type;
        this.timeOfInteraction = timeOfInteraction;
        this.hearthsChanged = hearthsChanged;
    }

    public MarriageInteractionType getInteractionType() {return interactionType;}
    public long getTimeOfInteraction() {return timeOfInteraction;}
    public int getHearthsChanged() {return hearthsChanged;}

}
