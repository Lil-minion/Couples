package com.github.lil_minion.couples.neoforge.registry;

import com.github.lil_minion.Couples;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers creative mode tabs for the NeoForge mod.
 */
public class CreativeTabNeoForge {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Couples.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TRADERS_TAB = CREATIVE_MODE_TABS.register("creative_tab.couples_tab",
            () -> CreativeModeTab.builder().icon(() ->
                            new ItemStack(ItemsNeoForge.WEDDING_RING.get()))
                    .title(Component.translatable("creative_tab.couples_tab")).displayItems(
                            (parameters, output) -> {
                                output.accept(ItemsNeoForge.WEDDING_RING.get());
                            })
                    .build()
    );

    /**
     * Registers the creative mode tabs with the event bus.
     *
     * @param eventBus The event bus to register the tabs with.
     */
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
