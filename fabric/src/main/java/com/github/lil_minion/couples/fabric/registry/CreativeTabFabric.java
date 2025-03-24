package com.github.lil_minion.couples.fabric.registry;

import com.github.lil_minion.Couples;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Handles the creation and registration of creative mode tabs for the mod.
 * This class is responsible for defining the items that appear in the
 * creative inventory under the mod's tab.
 */
public class CreativeTabFabric {

    public static final CreativeModeTab CREATIVE_MODE_TABS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemsFabric.WEDDING_RING))
            .title(Component.translatable("creative_tab.couples_tab"))
            .displayItems(
                    (parameters, output) -> {
                        output.accept(ItemsFabric.WEDDING_RING);
                    })
            .build();

    /**
     * Registers the creative mode tab with the game's registry.
     * This method is called to ensure the tab is available in the
     * creative inventory.
     */
    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                new ResourceLocation(Couples.MOD_ID + ":couples_tab"), CREATIVE_MODE_TABS);
    }

}
