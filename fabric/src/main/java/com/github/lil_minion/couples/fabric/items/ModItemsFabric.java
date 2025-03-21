package com.github.lil_minion.couples.fabric.items;

import com.github.lil_minion.Couples;
import com.github.lil_minion.item.WeddingRingItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemsFabric {

    public static final Item WEDDING_RING = register("wedding_ring",
            new WeddingRingItem(new Item.Properties()));

    private static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Couples.MOD_ID + ":" + name), item);
    }

    public static void register() {/* Statically Initialized, do not remove */}

}
