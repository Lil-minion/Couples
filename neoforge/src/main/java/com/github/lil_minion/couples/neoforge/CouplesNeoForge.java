package com.github.lil_minion.couples.neoforge;

import com.github.lil_minion.Couples;
import com.github.lil_minion.couples.neoforge.items.ModCreativeTabNeoForge;
import com.github.lil_minion.couples.neoforge.items.ModItemsNeoForge;
import com.github.lil_minion.couples.neoforge.network.ModMessagesNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(Couples.MOD_ID)
public final class CouplesNeoForge {

    public CouplesNeoForge(IEventBus eventBus) {
        ModItemsNeoForge.register(eventBus);
        ModCreativeTabNeoForge.register(eventBus);
        eventBus.addListener(RegisterPayloadHandlersEvent.class, ModMessagesNeoForge::registerPayloadHandler);
        MethodUploaderNeoForge.toCommonCodeForServer();
    }

}
