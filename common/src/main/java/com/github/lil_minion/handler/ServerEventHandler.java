package com.github.lil_minion.handler;

import com.github.lil_minion.server.data.InboxData;
import com.github.lil_minion.server.data.MarriageData;
import net.minecraft.server.MinecraftServer;

public class ServerEventHandler {

    public static void onServerStarted(MinecraftServer server) {
        MarriageData.createServerState(server);
        InboxData.createServerState(server);
    }

}
