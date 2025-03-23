package com.github.lil_minion.event.handler;

import com.github.lil_minion.item.WeddingRingItem;
import com.github.lil_minion.server.data.MarriageSavedData;
import com.github.lil_minion.utils.ChatUtil;
import com.github.lil_minion.utils.MarriageUtil;
import net.minecraft.world.entity.player.Player;

public class PlayerEventHandler {

    public static void onPlayerRightClick(Player playerOrigin, Player playerTarget) {
        if (playerOrigin.getMainHandItem().getItem() instanceof WeddingRingItem) {

            // Check if any of the players is already married
            boolean originMarried = MarriageSavedData.MARRIAGE_MAP.containsKey(playerOrigin.getUUID());
            boolean targetMarried = MarriageSavedData.MARRIAGE_MAP.containsKey(playerTarget.getUUID());

            if (!originMarried && !targetMarried) {

                // Check if already sent a proposal, if so then don't send another
                if (!MarriageUtil.alreadySentProposal(playerOrigin, playerTarget)) {
                    MarriageSavedData.MARRIAGE_PROPOSAL_MAP.put(playerTarget.getUUID(), playerOrigin.getUUID());

                    // Send chat message to the playerTarget
                    playerTarget.displayClientMessage(
                            ChatUtil.createPlayerTranslatableComponent(playerOrigin,
                                    "messages.couples.marry_me"), false
                    );
                }
            }
        }
    }

}
