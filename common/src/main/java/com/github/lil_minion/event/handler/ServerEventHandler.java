package com.github.lil_minion.event.handler;

import com.github.lil_minion.server.data.RomanceSavedData;
import com.github.lil_minion.server.data.InboxSavedData;
import com.github.lil_minion.server.data.MarriageSavedData;
import net.minecraft.server.MinecraftServer;

public class ServerEventHandler {

    public static void onServerStarted(MinecraftServer server) {
        MarriageSavedData.createServerState(server);
        InboxSavedData.createServerState(server);
        RomanceSavedData.createServerState(server);
    }

}
