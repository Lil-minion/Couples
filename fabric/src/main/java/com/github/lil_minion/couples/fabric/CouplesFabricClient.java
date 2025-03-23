package com.github.lil_minion.couples.fabric;

import com.github.lil_minion.couples.fabric.network.ModMessagesFabric;
import net.fabricmc.api.ClientModInitializer;

public final class CouplesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessagesFabric.registerClient();
        MethodUploaderFabric.toCommonCodeForClient();
    }

}
