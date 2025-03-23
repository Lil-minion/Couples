package com.github.lil_minion.model.marriage;

import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Marriage {

    private final List<MarriageInteraction> interactionsList = new ArrayList<>();
    private final UUID player1;
    private final UUID player2;
    private final long timeOfMarriage;
    private int timesKissed = 0;
    private int hearths = 0;
    private int hearthsEarned = 0;
    private int heartsLost = 0;
    private int timesSleptTogether = 0;
    private int timesSleptApart = 0;
    private int giftsGiven = 0;
    private int rumorCount = 0;

    public Marriage(UUID player1, UUID player2, long timeOfMarriage, int timesKissed) {
        this.player1 = player1;
        this.player2 = player2;
        this.timeOfMarriage = timeOfMarriage;
        this.timesKissed = timesKissed;
    }

    public Marriage(UUID player1, UUID player2, long timeOfMarriage) {
        this.player1 = player1;
        this.player2 = player2;
        this.timeOfMarriage = timeOfMarriage;
    }

    public boolean isPlayerInMarriage(UUID playerUUID) {
        return player1.equals(playerUUID) || player2.equals(playerUUID);
    }

    public boolean isPlayerInMarriage(Player player) {
        return isPlayerInMarriage(player.getUUID());
    }

    public UUID getOtherPlayer(UUID playerUUID) {
        return playerUUID.equals(player1) ? player2 : player1;
    }

    public UUID getOtherPlayer(Player player) {
        return getOtherPlayer(player.getUUID());
    }

    public List<MarriageInteraction> getInteractionsList() {return interactionsList;}

    public UUID getPlayer1() {return player1;}

    public UUID getPlayer2() {return player2;}

    public int getHearths() {return hearths;}

    public void setHearths(int hearths) {this.hearths = Math.min(Math.max(hearths, -100), 100);}

    public int getHearthsEarned() {return hearthsEarned;}

    public void setHearthsEarned(int hearthsEarned) {this.hearthsEarned = hearthsEarned;}

    public int getHeartsLost() {return heartsLost;}

    public void setHeartsLost(int heartsLost) {this.heartsLost = heartsLost;}

    public long getTimeOfMarriage() {return timeOfMarriage;}

    public int getTimesSleptTogether() {return timesSleptTogether;}

    public void setTimesSleptTogether(int timesSleptTogether) {this.timesSleptTogether = timesSleptTogether;}

    public int getTimesSleptApart() {return timesSleptApart;}

    public void setTimesSleptApart(int timesSleptApart) {this.timesSleptApart = timesSleptApart;}

    public int getTimesKissed() {return timesKissed;}

    public void setTimesKissed(int timesKissed) {this.timesKissed = timesKissed;}

    public int getGiftsGiven() {return giftsGiven;}

    public void setGiftsGiven(int giftsGiven) {this.giftsGiven = giftsGiven;}

    public int getRumorCount() {return rumorCount;}

    public void setRumorCount(int rumorCount) {this.rumorCount = rumorCount;}

}
