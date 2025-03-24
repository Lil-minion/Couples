package com.github.lil_minion.couples.neoforge.event;

import com.github.lil_minion.Couples;
import com.github.lil_minion.event.handler.ServerEventHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

/**
 * This class handles server events for the Couples mod using NeoForge.
 * It listens for server startup events and triggers the appropriate handler methods.
 */
@EventBusSubscriber(modid = Couples.MOD_ID)
public class ServerEventsNeoForge {

    /**
     * Called when the server has started.
     * This method invokes the server start handler to perform any necessary initialization.
     *
     * @param event The event containing information about the server startup.
     */
    @SubscribeEvent
    private static void onServerStarted(ServerStartedEvent event) {
        ServerEventHandler.onServerStarted(event.getServer());
    }
}
