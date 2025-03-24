package com.github.lil_minion.utils;

import com.github.lil_minion.model.relationship.romance.Romance;
import com.github.lil_minion.server.data.RomanceSavedData;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RomanceUtil {

    public static boolean isRomance(UUID playerUUID) {
        return RomanceSavedData.ROMANCE_MAP.containsKey(playerUUID);
    }

    public static boolean isRomance(Player player) {
        return isRomance(player.getUUID());
    }

    // Update the ROMANCE_MAP and save the changes on it.
    public static void updateRomance(Romance romance, boolean updateInstance) {

        List<Romance> romancesPlayer1 = RomanceSavedData.ROMANCE_MAP.getOrDefault(
                romance.getPlayer1(), new ArrayList<>());
        List<Romance> romancesPlayer2 = RomanceSavedData.ROMANCE_MAP.getOrDefault(
                romance.getPlayer2(), new ArrayList<>());

        romancesPlayer1.add(romance);
        romancesPlayer1.add(romance);

        RomanceSavedData.ROMANCE_MAP.put(romance.getPlayer1(), romancesPlayer1);
        RomanceSavedData.ROMANCE_MAP.put(romance.getPlayer2(), romancesPlayer2);

        if (updateInstance) {
            RomanceSavedData.INSTANCE.setDirty();
        }
    }

    public static void updateRomance(Romance romance) {
        updateRomance(romance, true);
    }

    public static void loadRomance(Romance romance) {
        updateRomance(romance, false);
    }

}
