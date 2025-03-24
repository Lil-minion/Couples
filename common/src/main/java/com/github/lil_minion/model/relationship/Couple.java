package com.github.lil_minion.model.relationship;

import java.util.UUID;

public abstract class Couple extends Relationship {

    private int timesKissed = 0;
    private int timesFlirted = 0;

    protected Couple(UUID player1, UUID player2) {
        super(player1, player2);
    }

    public int getTimesKissed() {return timesKissed;}

    public void setTimesKissed(int timesKissed) {this.timesKissed = timesKissed;}

    public int getTimesFlirted() {return timesFlirted;}

    public void setTimesFlirted(int timesFlirt) {this.timesFlirted = timesFlirt;}

}
