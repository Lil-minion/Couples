package com.github.lil_minion.event.handler;

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
        MarriageSavedData.createServerState(server);
        InboxSavedData.createServerState(server);
        RomanceSavedData.createServerState(server);
    }

}
