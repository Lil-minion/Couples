package com.github.lil_minion.event.handler;

import com.github.lil_minion.item.WeddingRingItem;
import com.github.lil_minion.model.relationship.interaction.InteractionRequestType;
import com.github.lil_minion.model.relationship.romance.Romance;
import com.github.lil_minion.server.data.MarriageSavedData;
import com.github.lil_minion.server.data.RomanceSavedData;
import com.github.lil_minion.utils.ChatUtil;
import com.github.lil_minion.utils.InteractionUtil;
import com.github.lil_minion.utils.MarriageUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Handles player-related events.
 */
public class PlayerEventHandler {

    /**
     * Handles the event when a player right-clicks another player while holding a wedding ring.
     *
     * @param playerOrigin The player who initiated the right-click action.
     * @param playerTarget The player who is being right-clicked.
     */
    public static void onPlayerRightClick(Player playerOrigin, Player playerTarget) {
        if (playerOrigin.getMainHandItem().getItem() instanceof WeddingRingItem) {

            // Check if any of the players is already married
            boolean originMarried = MarriageSavedData.MARRIAGE_MAP.containsKey(playerOrigin.getUUID());
            boolean targetMarried = MarriageSavedData.MARRIAGE_MAP.containsKey(playerTarget.getUUID());

            if (!originMarried && !targetMarried) {

                // Check if already sent a proposal, if so then don't send another
                if (!MarriageUtil.isOnProposalCooldown(playerOrigin)) {

                    if (playerTarget instanceof ServerPlayer serverPlayerTarget) {
                        Optional<Romance> romanceOpt = RomanceSavedData.ROMANCE_MAP.getOrDefault(playerOrigin.getUUID(), new ArrayList<>())
                                .stream()
                                .filter(romanceFromList -> romanceFromList.isPlayerInRelationship(playerTarget))
                                .findFirst();
                        if (romanceOpt.isPresent()) {
                            Romance romance = romanceOpt.get();
                            if (romance.getHearths() >= 20) {
                                InteractionUtil.interact(playerOrigin, serverPlayerTarget,
                                        ChatUtil.createPlayerTranslatableComponent(playerOrigin, "messages.couples.marry_me"),
                                        InteractionRequestType.MARRIAGE_PROPOSAL
                                );
                                long currentGameDayTime = playerOrigin.level().getDayTime();
                                long currentGameTime = playerOrigin.level().getGameTime();
                                long cooldown = currentGameTime + ((currentGameDayTime < 8000 ? 8000 : 31000) - currentGameDayTime);
                                MarriageSavedData.MARRIAGE_PROPOSAL_COOLDOWN_MAP.put(playerOrigin.getUUID(), cooldown);
                            } else {
                                MutableComponent combinedMessage = Component.translatable("messages.couples.not_enough_hearts");
                                playerOrigin.displayClientMessage(combinedMessage.append(romance.getHearths() + "/20"), false);
                            }
                        } else {
                            playerOrigin.displayClientMessage(Component.translatable("messages.couples.not_in_romance"), false);
                        }
                    }
                } else {
                    playerOrigin.displayClientMessage(Component.translatable("messages.couples.proposal_cooldown"), false);
                }
            }
        }
    }

}
