package com.github.lil_minion.couples.neoforge;

import com.github.lil_minion.Couples;
import com.github.lil_minion.couples.neoforge.registry.CreativeTabNeoForge;
import com.github.lil_minion.couples.neoforge.registry.ItemsNeoForge;
import com.github.lil_minion.couples.neoforge.network.NetworkMessagesNeoForge;
import com.github.lil_minion.couples.neoforge.registry.SoundsNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

/**
 * Main class for the Couples NeoForge mod.
 * This class initializes the mod and registers items, creative tabs, and network messages.
 */
@Mod(Couples.MOD_ID)
public final class CouplesNeoForge {

    /**
     * Constructs the CouplesNeoForge mod and registers necessary components.
     *
     * @param eventBus The event bus used for registering items and event listeners.
     */
    public CouplesNeoForge(IEventBus eventBus) {
        ItemsNeoForge.register(eventBus);
        CreativeTabNeoForge.register(eventBus);
        SoundsNeoForge.register(eventBus);
        eventBus.addListener(RegisterPayloadHandlersEvent.class, NetworkMessagesNeoForge::registerPayloadHandler);
        MethodUploaderNeoForge.uploadForBoth();

    }

}
