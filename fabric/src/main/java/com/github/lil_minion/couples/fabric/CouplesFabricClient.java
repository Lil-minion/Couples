package com.github.lil_minion.couples.fabric;

import com.github.lil_minion.couples.fabric.network.NetworkMessagesFabric;
import net.fabricmc.api.ClientModInitializer;

/**
 * The client entry point for the Couples mod using Fabric.
 * This class implements the ClientModInitializer interface to handle client-side initialization.
 */
public final class CouplesFabricClient implements ClientModInitializer {

    /**
     * Called when the client is initialized.
     * This method is responsible for running fabric client specific code.
     */
    @Override
    public void onInitializeClient() {
        NetworkMessagesFabric.registerClient();
        MethodUploaderFabric.toCommonCodeForClient();
    }
}
