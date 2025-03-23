package com.github.lil_minion.utils;

import com.github.lil_minion.model.marriage.Marriage;
import com.github.lil_minion.server.data.MarriageSavedData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class MarriageUtil {

    public static boolean isMarried(UUID playerUUID) {
        return MarriageSavedData.MARRIAGE_MAP.containsKey(playerUUID);
    }

    public static boolean isMarried(Player player) {
        return isMarried(player.getUUID());
    }

    public static boolean divorce(Player player) {
        if (MarriageUtil.isMarried(player)) {
            Marriage marriage = MarriageSavedData.MARRIAGE_MAP.get(player.getUUID());
            if (marriage != null) {
                UUID targetUUID = marriage.getOtherPlayer(player);

                // Remove data from server
                MarriageSavedData.MARRIAGE_MAP.remove(player.getUUID());
                MarriageSavedData.MARRIAGE_MAP.remove(targetUUID);
                MarriageSavedData.INSTANCE.setDirty();

                // Send message to command issuer
                Component message = Component.translatable("messages.couples.not_married");
                player.displayClientMessage(message, false);

                // Send message to partner
                Player playerTarget = player.level().getPlayerByUUID(targetUUID);
                if (playerTarget != null) {
                    playerTarget.displayClientMessage(
                            ChatUtil.createPlayerTranslatableComponent(player,
                                    "messages.couples.someone_divorced_you"), false
                    );
                }
                return true;
            }
        }
        return false;
    }

    // Update the MARRIAGE_MAP and save the changes on it.
    public static void updateMarriage(Marriage marriage) {
        MarriageSavedData.MARRIAGE_MAP.put(marriage.getPlayer1(), marriage);
        MarriageSavedData.MARRIAGE_MAP.put(marriage.getPlayer2(), marriage);
        MarriageSavedData.INSTANCE.setDirty();
    }

    public static boolean alreadySentProposal(UUID originPlayerUUID, UUID targetPlayerUUID) {
        return MarriageSavedData.MARRIAGE_PROPOSAL_MAP.get(targetPlayerUUID).equals(originPlayerUUID);
    }

    public static boolean alreadySentProposal(Player originPlayer, Player targetPlayer) {
        return alreadySentProposal(originPlayer.getUUID(), targetPlayer.getUUID());
    }

}
