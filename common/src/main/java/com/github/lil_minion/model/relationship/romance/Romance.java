package com.github.lil_minion.model.relationship.romance;

import com.github.lil_minion.model.relationship.Couple;

import java.util.UUID;

/**
 * Represents a romantic-ish (flirty) relationship between two players.
 * <p> This class extends the Couple class.
 * <p>
 * A romance is required before players can marry. Players must accumulate 20 hearts
 * to be eligible for marriage. Hearts decrease at a rate of 1 heart per in-game day, but can be increased
 * through interactions such as flirting or kissing (once per in-game day each). A minimum of 10 days is required
 * to reach the marriage threshold.
 */
public class Romance extends Couple {

    /**
     * Constructs a Romance instance with the specified players.
     *
     * @param player1 the UUID of the first player
     * @param player2 the UUID of the second player
     */
    public Romance(UUID player1, UUID player2) {
        super(player1, player2);
        setMinHearths(0); // Minimum hearts for romance
        setMaxHearths(20); // Maximum hearts required for marriage
    }
}
