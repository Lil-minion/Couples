package com.github.lil_minion.model.relationship.marriage;

import com.github.lil_minion.model.relationship.Couple;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Marriage extends Couple {

    private final List<MarriageInteraction> interactionsList = new ArrayList<>();

    private final long timeOfMarriage;
    private int hearthsEarned = 0;
    private int heartsLost = 0;
    private int timesSleptTogether = 0;
    private int timesSleptApart = 0;
    private int giftsGiven = 0;
    private int rumorCount = 0;

    public Marriage(UUID player1, UUID player2, long timeOfMarriage) {
        super(player1, player2);
        this.timeOfMarriage = timeOfMarriage;
    }

    public List<MarriageInteraction> getInteractionsList() {return interactionsList;}

    public int getHearthsEarned() {return hearthsEarned;}

    public void setHearthsEarned(int hearthsEarned) {this.hearthsEarned = hearthsEarned;}

    public int getHeartsLost() {return heartsLost;}

    public void setHeartsLost(int heartsLost) {this.heartsLost = heartsLost;}

    public long getTimeOfMarriage() {return timeOfMarriage;}

    public int getTimesSleptTogether() {return timesSleptTogether;}

    public void setTimesSleptTogether(int timesSleptTogether) {this.timesSleptTogether = timesSleptTogether;}

    public int getTimesSleptApart() {return timesSleptApart;}

    public void setTimesSleptApart(int timesSleptApart) {this.timesSleptApart = timesSleptApart;}

    public int getGiftsGiven() {return giftsGiven;}

    public void setGiftsGiven(int giftsGiven) {this.giftsGiven = giftsGiven;}

    public int getRumorCount() {return rumorCount;}

    public void setRumorCount(int rumorCount) {this.rumorCount = rumorCount;}

}
