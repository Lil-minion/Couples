package com.github.lil_minion.couples.fabric.event;

import com.github.lil_minion.event.handler.ServerEventHandler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ServerEventsFabric {

    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(ServerEventHandler::onServerStarted);
    }

}
