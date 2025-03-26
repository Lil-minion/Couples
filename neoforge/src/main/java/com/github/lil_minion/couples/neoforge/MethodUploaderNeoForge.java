package com.github.lil_minion.couples.neoforge;

import com.github.lil_minion.couples.neoforge.registry.SoundsNeoForge;
import com.github.lil_minion.uploaded.UploadedMethods;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Handles the uploading of neoforge methods to common code
 */
public class MethodUploaderNeoForge {

    /**
     * Sets the methods for both the server and client side
     */
    public static void uploadForBoth() {
        UploadedMethods.sendMessageToClientMethod = PacketDistributor::sendToPlayer;
        UploadedMethods.getKissSoundEvent = SoundsNeoForge.KISS;
    }

    /**
     * Sets the methods for both the client side
     */
    public static void uploadForClient() {
        UploadedMethods.sendMessageToServerMethod = PacketDistributor::sendToServer;
    }

}
