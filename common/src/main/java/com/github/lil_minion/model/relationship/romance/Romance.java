package com.github.lil_minion.model.relationship.romance;

import com.github.lil_minion.model.relationship.Couple;

import java.util.UUID;

public class Romance extends Couple {

    public Romance(UUID player1, UUID player2) {
        super(player1, player2);
        setMinHearths(0);
        setMaxHearths(20);
    }


}
