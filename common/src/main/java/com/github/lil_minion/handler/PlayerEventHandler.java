package com.github.lil_minion.handler;

import com.github.lil_minion.item.WeddingRingItem;
import com.github.lil_minion.server.data.MarriageData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class PlayerEventHandler {
    public static void onPlayerRightClick(Player playerOrigin, Player playerTarget) {
        if (playerOrigin.getMainHandItem().getItem() instanceof WeddingRingItem) {
            boolean originMarried = MarriageData.MARRIAGE_MAP.containsKey(playerOrigin.getUUID());
            boolean targetMarried = MarriageData.MARRIAGE_MAP.containsKey(playerTarget.getUUID());
            if (!originMarried && !targetMarried) {
                if (!MarriageData.MARRIAGE_PROPOSAL_MAP.get(playerTarget.getUUID()).equals(playerOrigin.getUUID())) {
                    MarriageData.MARRIAGE_PROPOSAL_MAP.put(playerTarget.getUUID(), playerOrigin.getUUID());
                    MutableComponent message = Component.literal(playerOrigin.getDisplayName().toString() + " ");
                    message.append(Component.translatable("messages.couples.marry_me"));
                    playerTarget.displayClientMessage(message, false);
                }
            }
        }
    }
}
