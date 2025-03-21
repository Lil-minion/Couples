package com.github.lil_minion.couples.fabric;

import com.github.lil_minion.couples.fabric.commands.ModCommandsFabric;
import net.fabricmc.api.ModInitializer;

public final class CouplesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModCommandsFabric.register();
    }

}
