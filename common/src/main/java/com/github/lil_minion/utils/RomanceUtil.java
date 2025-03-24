package com.github.lil_minion.utils;

import com.github.lil_minion.model.relationship.romance.Romance;
import com.github.lil_minion.server.data.RomanceSavedData;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Utility class for managing romance relationships between players.
 * This class provides methods to check, update, and load romance data.
 */
public class RomanceUtil {

    /**
     * Checks if a romance relationship exists for the given player UUID.
     *
     * @param playerUUID The UUID of the player to check.
     * @return True if a romance relationship exists, false otherwise.
     */
    public static boolean isRomance(UUID playerUUID) {
        return RomanceSavedData.ROMANCE_MAP.containsKey(playerUUID);
    }

    /**
     * Checks if a romance relationship exists for the given player.
     *
     * @param player The player to check.
     * @return True if a romance relationship exists, false otherwise.
     */
    public static boolean isRomance(Player player) {
        return isRomance(player.getUUID());
    }

    /**
     * Updates the ROMANCE_MAP with the given romance instance and optionally marks it as dirty.
     *
     * @param romance The romance instance to update.
     * @param updateInstance Whether to mark the instance as dirty.
     */
    public static void updateRomance(Romance romance, boolean updateInstance) {
        List<Romance> romancesPlayer1 = RomanceSavedData.ROMANCE_MAP.getOrDefault(
                romance.getPlayer1(), new ArrayList<>());
        List<Romance> romancesPlayer2 = RomanceSavedData.ROMANCE_MAP.getOrDefault(
                romance.getPlayer2(), new ArrayList<>());

        romancesPlayer1.add(romance);
        romancesPlayer2.add(romance);

        RomanceSavedData.ROMANCE_MAP.put(romance.getPlayer1(), romancesPlayer1);
        RomanceSavedData.ROMANCE_MAP.put(romance.getPlayer2(), romancesPlayer2);

        if (updateInstance) {
            RomanceSavedData.INSTANCE.setDirty();
        }
    }

    /**
     * Updates the ROMANCE_MAP with the given romance instance and marks it as dirty.
     *
     * @param romance The romance instance to update.
     */
    public static void updateRomance(Romance romance) {
        updateRomance(romance, true);
    }

    /**
     * Loads the given romance instance into the ROMANCE_MAP without marking it as dirty.
     *
     * @param romance The romance instance to load.
     */
    public static void loadRomance(Romance romance) {
        updateRomance(romance, false);
    }

}
