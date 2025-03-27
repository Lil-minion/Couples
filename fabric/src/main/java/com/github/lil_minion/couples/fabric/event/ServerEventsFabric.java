package com.github.lil_minion.couples.fabric.event;

import com.github.lil_minion.event.handler.ServerEventHandler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

/**
 * This class is responsible for registering server-related event callback
 */
public class ServerEventsFabric {

    /**
     * Registers the server event callbacks.
     */
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(ServerEventHandler::onServerStarted);
        ServerTickEvents.START_WORLD_TICK.register(ServerEventHandler::onLevelTick);
    }

}
