package com.github.lil_minion.couples.fabric;

import com.github.lil_minion.couples.fabric.registry.SoundsFabric;
import com.github.lil_minion.uploaded.UploadedMethods;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/**
 * A utility class for uploading fabric specific methods.
 */
public class MethodUploaderFabric {

    /**
     * Sets the methods for both the server and client side
     */
    public static void uploadForBoth() {
        UploadedMethods.sendMessageToClientMethod = ServerPlayNetworking::send;
        UploadedMethods.getKissSoundEvent = () -> SoundsFabric.KISS;
    }

    /**
     * Sets the methods for the client side
     */
    public static void uploadForClient() {
        UploadedMethods.sendMessageToServerMethod = ClientPlayNetworking::send;
    }
}
