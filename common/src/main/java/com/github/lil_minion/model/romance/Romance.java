package com.github.lil_minion.model.romance;

import net.minecraft.world.entity.player.Player;
import java.util.UUID;

public class Romance {

    private final UUID player1;
    private final UUID player2;
    private int timesKissed = 0;
    private int timesFlirt = 0;
    private int hearths = 0;


    public Romance(UUID player1, UUID player2, int timesKissed, int timesFlirt) {
        this.player1 = player1;
        this.player2 = player2;
        this.timesKissed = timesKissed;
        this.timesFlirt = timesFlirt;
    }

    public Romance(UUID player1, UUID player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean isPlayerInRomance(UUID playerUUID) {
        return player1.equals(playerUUID) || player2.equals(playerUUID);
    }
    public boolean isPlayerInRomance(Player player) {
        return isPlayerInRomance(player.getUUID());
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
    public void setHearths(int hearths) {this.hearths = Math.min(Math.max(hearths, -100), 100);}

    public int getTimesKissed() {return timesKissed;}
    public void setTimesKissed(int timesKissed) {this.timesKissed = timesKissed;}

    public int getTimesFlirt() {return timesFlirt;}
    public void setTimesFlirt(int timesFlirt) {this.timesFlirt = timesFlirt;}

}
