package com.github.lil_minion;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModLoaderMethods {

    public static Consumer<CustomPacketPayload> sendMessageToServerMethod;
    public static BiConsumer<ServerPlayer, CustomPacketPayload> sendMessageToClientMethod;

    public static void sendMessageToServer(CustomPacketPayload payload) {
        sendMessageToServerMethod.accept(payload);
    }

    public static void sendMessageToClient(ServerPlayer player, CustomPacketPayload payload) {
        sendMessageToClientMethod.accept(player, payload);
    }

}
