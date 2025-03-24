package com.github.lil_minion.couples.fabric;

import com.github.lil_minion.ModLoaderMethods;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/**
 * A utility class for uploading fabric specific methods.
 */
public class MethodUploaderFabric {

    /**
     * Sets the method for sending messages from the server to the client.
     * This method assigns the server-side networking method to the
     * ModLoaderMethods for sending messages.
     */
    public static void toCommonCodeForServer() {
        ModLoaderMethods.sendMessageToClientMethod = ServerPlayNetworking::send;
    }

    /**
     * Sets the method for sending messages from the client to the server.
     * This method assigns the client-side networking method to the
     * ModLoaderMethods for sending messages.
     */
    public static void toCommonCodeForClient() {
        ModLoaderMethods.sendMessageToServerMethod = ClientPlayNetworking::send;
    }
}
