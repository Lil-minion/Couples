package com.github.lil_minion.model.relationship;

import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public abstract class Relationship {

    private final UUID player1;
    private final UUID player2;
    private int hearths;
    private int minHearths = -100;
    private int maxHearths = 100;

    protected Relationship(UUID player1, UUID player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean isPlayerInRelationship(UUID playerUUID) {
        return getPlayer1().equals(playerUUID) || player2.equals(playerUUID);
    }

    public boolean isPlayerInRelationship(Player player) {
        return isPlayerInRelationship(player.getUUID());
    }

    public UUID getOtherPlayer(UUID playerUUID) {
        return playerUUID.equals(player1) ? player2 : player1;
    }

    public UUID getOtherPlayer(Player player) {
        return getOtherPlayer(player.getUUID());
    }

    public UUID getPlayer1() {return player1;}

    public UUID getPlayer2() {return player2;}

    public int getHearths() {return hearths;}

    public void setHearths(int hearths) {this.hearths = Math.min(Math.max(hearths, minHearths), maxHearths);}

    public int getMinHearths() {return minHearths;}

    public void setMinHearths(int minHearths) {this.minHearths = minHearths;}

    public int getMaxHearths() {return maxHearths;}

    public void setMaxHearths(int maxHearths) {this.maxHearths = maxHearths;}

}
