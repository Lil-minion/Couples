package com.github.lil_minion.couples.fabric.registry;

import com.github.lil_minion.Couples;
import com.github.lil_minion.item.WeddingRingItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

/**
 * This class is responsible for registering items in the Couples mod.
 */
public class ItemsFabric {

    /**
     * The wedding ring item.
     */
    public static final Item WEDDING_RING = register("wedding_ring",
            new WeddingRingItem(new Item.Properties()));

    /**
     * Registers an item with the given name and item instance.
     *
     * @param name The name of the item to register.
     * @param item The item instance to register.
     * @return The registered item.
     */
    private static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Couples.MOD_ID + ":" + name), item);
    }

    /**
     * Static method to initialize item registration.
     * This method is called during the mod initialization phase.
     * It does not perform any actions but serves as a signal for static initialization.
     */
    public static void register() {}
}
