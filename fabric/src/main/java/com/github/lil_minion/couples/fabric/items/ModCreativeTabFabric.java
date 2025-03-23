package com.github.lil_minion.couples.fabric.items;

import com.github.lil_minion.Couples;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabFabric {

    public static final CreativeModeTab CREATIVE_MODE_TABS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItemsFabric.WEDDING_RING))
            .title(Component.translatable("creative_tab.couples_tab"))
            .displayItems(
                    (parameters, output) -> {
                        output.accept(ModItemsFabric.WEDDING_RING);
                    })
            .build();

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                new ResourceLocation(Couples.MOD_ID + ":couples_tab"), CREATIVE_MODE_TABS);
    }

}

