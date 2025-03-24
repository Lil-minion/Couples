package com.github.lil_minion;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This class serves as a way to upload methods from non-common code for use in common code.
 */
public class ModLoaderMethods {

    public static Consumer<CustomPacketPayload> sendMessageToServerMethod;
    public static BiConsumer<ServerPlayer, CustomPacketPayload> sendMessageToClientMethod;

    /**
     * Sends a message to the server with the specified payload.
     *
     * @param payload The payload containing the message to be sent.
     */
    public static void sendMessageToServer(CustomPacketPayload payload) {
        sendMessageToServerMethod.accept(payload);
    }

    /**
     * Sends a message to the specified client player with the given payload.
     *
     * @param player The player to whom the message will be sent.
     * @param payload The payload containing the message to be sent.
     */
    public static void sendMessageToClient(ServerPlayer player, CustomPacketPayload payload) {
        sendMessageToClientMethod.accept(player, payload);
    }

}
