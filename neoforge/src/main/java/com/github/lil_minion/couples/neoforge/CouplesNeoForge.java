package com.github.lil_minion.couples.neoforge;

import com.github.lil_minion.Couples;
import com.github.lil_minion.couples.neoforge.registry.CreativeTabNeoForge;
import com.github.lil_minion.couples.neoforge.registry.ItemsNeoForge;
import com.github.lil_minion.couples.neoforge.network.NetworkMessagesNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(Couples.MOD_ID)
public final class CouplesNeoForge {

    public CouplesNeoForge(IEventBus eventBus) {
        ItemsNeoForge.register(eventBus);
        CreativeTabNeoForge.register(eventBus);
        eventBus.addListener(RegisterPayloadHandlersEvent.class, NetworkMessagesNeoForge::registerPayloadHandler);
        MethodUploaderNeoForge.toCommonCodeForServer();
    }

}
