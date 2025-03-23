package com.github.lil_minion.couples.neoforge.registry;

import com.github.lil_minion.Couples;
import com.github.lil_minion.item.WeddingRingItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemsNeoForge {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, Couples.MOD_ID);

    public static final DeferredHolder<Item, Item> WEDDING_RING = ITEMS.register("wedding_ring",
            () -> new WeddingRingItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
