package com.github.lil_minion.couples.neoforge;

import com.github.lil_minion.Couples;
import com.github.lil_minion.couples.neoforge.items.ModCreativeTabNeoForge;
import com.github.lil_minion.couples.neoforge.items.ModItemsNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Couples.MOD_ID)
public final class CouplesNeoForge {

    public CouplesNeoForge(IEventBus eventBus) {
        ModItemsNeoForge.register(eventBus);
        ModCreativeTabNeoForge.register(eventBus);
    }

}
