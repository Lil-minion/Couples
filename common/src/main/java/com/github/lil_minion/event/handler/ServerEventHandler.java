package com.github.lil_minion.event.handler;

import com.github.lil_minion.model.relationship.marriage.Marriage;
import com.github.lil_minion.server.data.InteractionVolatileData;
import com.github.lil_minion.server.data.RomanceSavedData;
import com.github.lil_minion.server.data.InboxSavedData;
import com.github.lil_minion.server.data.MarriageSavedData;
import com.github.lil_minion.utils.relationship.MarriageUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import java.util.HashSet;
import java.util.Set;

/**
 * Handles server-related events.
 */
public class ServerEventHandler {

    /**
     * Handles the server started event.
     *
     * @param server The Minecraft server instance that has started.
     */
    public static void onServerStarted(MinecraftServer server) {
        MarriageSavedData.MARRIAGE_MAP.clear();
        InboxSavedData.PLAYER_INBOX_MAP.clear();
        RomanceSavedData.ROMANCE_MAP.clear();
        InteractionVolatileData.INTERACTION_COOLDOWN_MAP.clear();
        InteractionVolatileData.MARRIAGE_PROPOSAL_COOLDOWN_MAP.clear();

        MarriageSavedData.createServerState(server);
        InboxSavedData.createServerState(server);
        RomanceSavedData.createServerState(server);
    }

    public static void onLevelTick(ServerLevel serverLevel) {
        if (serverLevel.dayTime() == 8000) {
            Set<Marriage> marriages = new HashSet<>(MarriageSavedData.MARRIAGE_MAP.values());
            for
             (Marriage marriage : marriages) {
                marriage.setHearths(marriage.getHearths() - 1);
                marriage.setHeartsLost(marriage.getHeartsLost() + 1);
                MarriageUtil.updateMarriage(marriage);
            }
        }
    }
}
