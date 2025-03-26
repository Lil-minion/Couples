package com.github.lil_minion.utils.relationship;

import com.github.lil_minion.model.relationship.romance.Romance;
import com.github.lil_minion.server.data.RomanceSavedData;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing romance relationships between players.
 * This class provides methods to check, update, and load romance data.
 */
public class RomanceUtil {

    /**
     * Updates the ROMANCE_MAP with the given romance instance and optionally marks it as dirty.
     *
     * @param romance        The romance instance to update.
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
