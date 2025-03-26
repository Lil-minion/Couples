package com.github.lil_minion.utils;

import com.github.lil_minion.model.relationship.marriage.Marriage;
import com.github.lil_minion.server.data.MarriageSavedData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

/**
 * Utility class for managing marriage relationships between players.
 * This class provides methods to check marriage status, divorce players,
 * and manage marriage proposals.
 */
public class MarriageUtil {

    /**
     * Checks if a player with the given {@link UUID} is married.
     *
     * @param playerUUID The {@link UUID} of the player to check.
     * @return True if the player is married, false otherwise.
     */
    public static boolean isMarried(UUID playerUUID) {
        return MarriageSavedData.MARRIAGE_MAP.containsKey(playerUUID);
    }

    /**
     * Checks if the specified player is married.
     *
     * @param player The {@link Player} to check.
     * @return True if the player is married, false otherwise.
     */
    public static boolean isMarried(Player player) {
        return isMarried(player.getUUID());
    }

    /**
     * Divorces the specified player if they are married.
     *
     * @param player The {@link Player} to divorce.
     * @return True if the divorce was successful, false otherwise.
     */
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

    /**
     * Updates the MARRIAGE_MAP with the given {@link Marriage} instance.
     *
     * @param marriage The marriage instance to update.
     */
    public static void updateMarriage(Marriage marriage) {
        MarriageSavedData.MARRIAGE_MAP.put(marriage.getPlayer1(), marriage);
        MarriageSavedData.MARRIAGE_MAP.put(marriage.getPlayer2(), marriage);
        MarriageSavedData.INSTANCE.setDirty();
    }

    /**
     * Checks if marriage proposal are on cooldown
     *
     * @param originPlayerUUID The {@link UUID} of the player who sent the proposal.
     * @param gameTime The current game time.
     * @return True if a proposal cooldown is active, otherwise false
     */
    public static boolean isOnProposalCooldown(UUID originPlayerUUID, long gameTime) {
        if (MarriageSavedData.MARRIAGE_PROPOSAL_COOLDOWN_MAP.containsKey(originPlayerUUID)) {
            return MarriageSavedData.MARRIAGE_PROPOSAL_COOLDOWN_MAP.get(originPlayerUUID) > gameTime + 12000;
        }
        return false;
    }

    /**
     * Checks if marriage proposal are on cooldown
     *
     * @param originPlayer The {@link Player} who sent the proposal.
     * @return True if a proposal cooldown is active, otherwise false
     */
    public static boolean isOnProposalCooldown(Player originPlayer) {
        return isOnProposalCooldown(originPlayer.getUUID(), originPlayer.level().getGameTime());
    }

}
