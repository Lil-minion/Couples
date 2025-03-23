package com.github.lil_minion.couples.fabric;

import com.github.lil_minion.ModLoaderMethods;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class MethodUploaderFabric {

    public static void toCommonCodeForServer() {
        ModLoaderMethods.sendMessageToClientMethod = ServerPlayNetworking::send;
    }

    public static void toCommonCodeForClient() {
        ModLoaderMethods.sendMessageToServerMethod = ClientPlayNetworking::send;
    }

}
