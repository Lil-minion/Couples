package com.github.lil_minion.couples.fabric;

import com.github.lil_minion.couples.fabric.event.CommandEventsFabric;
import com.github.lil_minion.couples.fabric.event.PlayerEventsFabric;
import com.github.lil_minion.couples.fabric.event.ServerEventsFabric;
import com.github.lil_minion.couples.fabric.registry.CreativeTabFabric;
import com.github.lil_minion.couples.fabric.registry.ItemsFabric;
import com.github.lil_minion.couples.fabric.network.NetworkMessagesFabric;
import net.fabricmc.api.ModInitializer;

/**
 * The main class for the Couples mod using Fabric.
 * This class implements the ModInitializer interface to handle mod initialization.
 */
public final class CouplesFabric implements ModInitializer {

    /**
     * Called when the mod is initialized.
     * This method is responsible for running common fabric specific code.
     */
    @Override
    public void onInitialize() {
        CommandEventsFabric.register();
        ItemsFabric.register();
        CreativeTabFabric.register();
        NetworkMessagesFabric.registerServer();
        MethodUploaderFabric.toCommonCodeForServer();
        PlayerEventsFabric.register();
        ServerEventsFabric.register();
    }
}
