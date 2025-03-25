package com.github.lil_minion.couples.fabric.network;

import com.github.lil_minion.network.handler.InteractionRequestHandler;
import com.github.lil_minion.network.handler.MailMessageHandler;
import com.github.lil_minion.network.handler.OpenInboxScreenHandler;
import com.github.lil_minion.network.message.MailMessage;
import com.github.lil_minion.network.message.OpenInboxScreenMessage;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/**
 * Handles the registration of network messages for the mod.
 * This class is responsible for setting up networking, including
 * message handling and payload registration.
 */
public class NetworkMessagesFabric {

    /**
     * Registers client-side network message handlers.
     * This method sets up the necessary receivers for handling
     * incoming messages from the server.
     */
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(MailMessage.ID, (mailMessage, context) ->
                MailMessageHandler.handle(mailMessage, context.player()));

        ClientPlayNetworking.registerGlobalReceiver(InteractionRequestMessage.ID, (requestMessage, context) ->
                InteractionRequestHandler.handle(requestMessage, context.player()));

        ClientPlayNetworking.registerGlobalReceiver(OpenInboxScreenMessage.ID, (openInboxScreenMessage, context) ->
                OpenInboxScreenHandler.handle(openInboxScreenMessage, context.player()));
    }

    /**
     * Registers common server network message handlers and common payload types.
     * This method sets up the necessary receivers for handling
     * incoming messages from clients and registers payload codecs.
     */
    public static void registerServer() {
        PayloadTypeRegistry.playS2C().register(MailMessage.ID, MailMessage.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(MailMessage.ID, MailMessage.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(InteractionRequestMessage.ID, InteractionRequestMessage.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(InteractionRequestMessage.ID, InteractionRequestMessage.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(OpenInboxScreenMessage.ID, OpenInboxScreenMessage.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(MailMessage.ID, (mailMessage, context) ->
                MailMessageHandler.handle(mailMessage, context.player()));

        ServerPlayNetworking.registerGlobalReceiver(InteractionRequestMessage.ID, (requestMessage, context) ->
                InteractionRequestHandler.handle(requestMessage, context.player()));
    }

}
