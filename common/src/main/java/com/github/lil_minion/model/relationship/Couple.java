package com.github.lil_minion.model.relationship;

import java.util.UUID;

/**
 * Abstract class representing a couple in a romantic relationship.
 * This class extends the Relationship class and provides common functionality
 * for managing interactions between two players in a romantic context.
 */
public abstract class Couple extends Relationship {

    private int timesKissed = 0;
    private int timesFlirted = 0;

    /**
     * Constructs a Couple instance with the specified players.
     *
     * @param player1 the UUID of the first player
     * @param player2 the UUID of the second player
     */
    protected Couple(UUID player1, UUID player2) {
        super(player1, player2);
    }

    /**
     * Returns the number of times the couple has kissed.
     *
     * @return the number of times kissed
     */
    public int getTimesKissed() {
        return timesKissed;
    }

    /**
     * Sets the number of times the couple has kissed.
     *
     * @param timesKissed the number of times kissed
     */
    public void setTimesKissed(int timesKissed) {
        this.timesKissed = timesKissed;
    }

    /**
     * Returns the number of times the couple has flirted.
     *
     * @return the number of times flirted
     */
    public int getTimesFlirted() {
        return timesFlirted;
    }

    /**
     * Sets the number of times the couple has flirted.
     *
     * @param timesFlirt the number of times flirted
     */
    public void setTimesFlirted(int timesFlirt) {
        this.timesFlirted = timesFlirt;
    }
}
