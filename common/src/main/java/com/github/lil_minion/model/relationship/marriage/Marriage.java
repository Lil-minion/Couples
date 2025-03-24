package com.github.lil_minion.model.relationship.marriage;

import com.github.lil_minion.model.relationship.Couple;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a marriage relationship between two players.
 * This class extends the Couple class and maintains various statistics
 * related to the marriage, such as interactions, hearts earned/lost,
 * and sleeping arrangements.
 */
public class Marriage extends Couple {

    private final List<MarriageInteraction> interactionsList = new ArrayList<>();
    private final long timeOfMarriage;
    private int hearthsEarned = 0;
    private int heartsLost = 0;
    private int timesSleptTogether = 0;
    private int timesSleptApart = 0;
    private int giftsGiven = 0;
    private int rumorCount = 0;

    /**
     * Constructs a Marriage instance with the specified players and time of marriage.
     *
     * @param player1        the UUID of the first player
     * @param player2        the UUID of the second player
     * @param timeOfMarriage the time (in ticks) when the marriage took place
     */
    public Marriage(UUID player1, UUID player2, long timeOfMarriage) {
        super(player1, player2);
        this.timeOfMarriage = timeOfMarriage;
    }

    /**
     * Returns the list of interactions that have occurred in this marriage.
     *
     * @return a list of MarriageInteraction objects
     */
    public List<MarriageInteraction> getInteractionsList() {
        return interactionsList;
    }

    /**
     * Returns the number of hearts earned in this marriage.
     *
     * @return the number of hearts earned
     */
    public int getHearthsEarned() {
        return hearthsEarned;
    }

    /**
     * Sets the number of hearts earned in this marriage.
     *
     * @param hearthsEarned the number of hearts earned
     */
    public void setHearthsEarned(int hearthsEarned) {
        this.hearthsEarned = hearthsEarned;
    }

    /**
     * Returns the number of hearts lost in this marriage.
     *
     * @return the number of hearts lost
     */
    public int getHeartsLost() {
        return heartsLost;
    }

    /**
     * Sets the number of hearts lost in this marriage.
     *
     * @param heartsLost the number of hearts lost
     */
    public void setHeartsLost(int heartsLost) {
        this.heartsLost = heartsLost;
    }

    /**
     * Returns the time of marriage.
     *
     * @return the time of marriage in ticks
     */
    public long getTimeOfMarriage() {
        return timeOfMarriage;
    }

    /**
     * Returns the number of times the couple has slept together.
     *
     * @return the number of times slept together
     */
    public int getTimesSleptTogether() {
        return timesSleptTogether;
    }

    /**
     * Sets the number of times the couple has slept together.
     *
     * @param timesSleptTogether the number of times slept together
     */
    public void setTimesSleptTogether(int timesSleptTogether) {
        this.timesSleptTogether = timesSleptTogether;
    }

    /**
     * Returns the number of times the couple has slept apart.
     *
     * @return the number of times slept apart
     */
    public int getTimesSleptApart() {
        return timesSleptApart;
    }

    /**
     * Sets the number of times the couple has slept apart.
     *
     * @param timesSleptApart the number of times slept apart
     */
    public void setTimesSleptApart(int timesSleptApart) {
        this.timesSleptApart = timesSleptApart;
    }

    /**
     * Returns the number of gifts given in this marriage.
     *
     * @return the number of gifts given
     */
    public int getGiftsGiven() {
        return giftsGiven;
    }

    /**
     * Sets the number of gifts given in this marriage.
     *
     * @param giftsGiven the number of gifts given
     */
    public void setGiftsGiven(int giftsGiven) {
        this.giftsGiven = giftsGiven;
    }

    /**
     * Returns the number of rumors associated with this marriage.
     *
     * @return the number of rumors
     */
    public int getRumorCount() {
        return rumorCount;
    }

    /**
     * Sets the number of rumors associated with this marriage.
     *
     * @param rumorCount the number of rumors
     */
    public void setRumorCount(int rumorCount) {
        this.rumorCount = rumorCount;
    }
}