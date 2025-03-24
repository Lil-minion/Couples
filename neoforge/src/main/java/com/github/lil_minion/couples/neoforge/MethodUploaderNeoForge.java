package com.github.lil_minion.couples.neoforge;

import com.github.lil_minion.ModLoaderMethods;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Handles the uploading of neoforge methods to common code
 */
public class MethodUploaderNeoForge {

    /**
     * Sets the method for sending messages from the server to the client.
     * This method assigns the server-side networking method to the
     * ModLoaderMethods for sending messages.
     */
    public static void toCommonCodeForServer() {
        ModLoaderMethods.sendMessageToClientMethod = PacketDistributor::sendToPlayer;
    }

    /**
     * Sets the method for sending messages from the client to the server.
     * This method assigns the client-side networking method to the
     * ModLoaderMethods for sending messages.
     */
    public static void toCommonCodeForClient() {
        ModLoaderMethods.sendMessageToServerMethod = PacketDistributor::sendToServer;
    }

}
