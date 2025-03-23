package com.github.lil_minion.couples.fabric;

import com.github.lil_minion.couples.fabric.network.NetworkMessagesFabric;
import net.fabricmc.api.ClientModInitializer;

public final class CouplesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NetworkMessagesFabric.registerClient();
        MethodUploaderFabric.toCommonCodeForClient();
    }

}
