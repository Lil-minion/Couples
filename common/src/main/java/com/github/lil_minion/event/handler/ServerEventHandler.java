package com.github.lil_minion.event.handler;

import com.github.lil_minion.server.data.InteractionVolatileData;
import com.github.lil_minion.server.data.RomanceSavedData;
import com.github.lil_minion.server.data.InboxSavedData;
import com.github.lil_minion.server.data.MarriageSavedData;
import net.minecraft.server.MinecraftServer;

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

}
