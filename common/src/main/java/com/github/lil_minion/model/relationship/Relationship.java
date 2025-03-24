package com.github.lil_minion.model.relationship;

import net.minecraft.world.entity.player.Player;

import java.util.UUID;

/**
 * Abstract class representing a relationship between two players.
 * This class manages the players involved, their hearts, and provides
 * methods to interact with the relationship.
 */
public abstract class Relationship {

    private final UUID player1; // UUID of the first player
    private final UUID player2; // UUID of the second player
    private int hearths; // Current hearts in the relationship
    private int minHearths = -100; // Minimum hearts allowed
    private int maxHearths = 100; // Maximum hearts allowed

    /**
     * Constructs a Relationship instance with the specified players.
     *
     * @param player1 the UUID of the first player
     * @param player2 the UUID of the second player
     */
    protected Relationship(UUID player1, UUID player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Checks if a player is in this relationship based on their UUID.
     *
     * @param playerUUID the UUID of the player to check
     * @return true if the player is in the relationship, false otherwise
     */
    public boolean isPlayerInRelationship(UUID playerUUID) {
        return getPlayer1().equals(playerUUID) || player2.equals(playerUUID);
    }

    /**
     * Checks if a player is in this relationship based on the Player object.
     *
     * @param player the Player object to check
     * @return true if the player is in the relationship, false otherwise
     */
    public boolean isPlayerInRelationship(Player player) {
        return isPlayerInRelationship(player.getUUID());
    }

    /**
     * Returns the UUID of the other player in the relationship.
     *
     * @param playerUUID the UUID of one player
     * @return the UUID of the other player
     */
    public UUID getOtherPlayer(UUID playerUUID) {
        return playerUUID.equals(player1) ? player2 : player1;
    }

    /**
     * Returns the UUID of the other player in the relationship based on the Player object.
     *
     * @param player the Player object of one player
     * @return the UUID of the other player
     */
    public UUID getOtherPlayer(Player player) {
        return getOtherPlayer(player.getUUID());
    }

    /**
     * Returns the UUID of the first player in the relationship.
     *
     * @return the UUID of the first player
     */
    public UUID getPlayer1() {
        return player1;
    }

    /**
     * Returns the UUID of the second player in the relationship.
     *
     * @return the UUID of the second player
     */
    public UUID getPlayer2() {
        return player2;
    }

    /**
     * Returns the current number of hearts in the relationship.
     *
     * @return the current hearts
     */
    public int getHearths() {
        return hearths;
    }

    /**
     * Sets the number of hearts in the relationship, ensuring it stays within the defined limits.
     *
     * @param hearths the number of hearts to set
     */
    public void setHearths(int hearths) {
        this.hearths = Math.min(Math.max(hearths, minHearths), maxHearths);
    }

    /**
     * Returns the minimum number of hearts allowed in the relationship.
     *
     * @return the minimum hearts
     */
    public int getMinHearths() {
        return minHearths;
    }

    /**
     * Sets the minimum number of hearts allowed in the relationship.
     *
     * @param minHearths the minimum hearts to set
     */
    public void setMinHearths(int minHearths) {
        this.minHearths = minHearths;
    }

    /**
     * Returns the maximum number of hearts allowed in the relationship.
     *
     * @return the maximum hearts
     */
    public int getMaxHearths() {
        return maxHearths;
    }

    /**
     * Sets the maximum number of hearts allowed in the relationship.
     *
     * @param maxHearths the maximum hearts to set
     */
    public void setMaxHearths(int maxHearths) {
        this.maxHearths = maxHearths;
    }
}
