package com.github.lil_minion.couples.fabric.network;

import com.github.lil_minion.network.handler.InteractionRequestHandler;
import com.github.lil_minion.network.handler.MailHandler;
import com.github.lil_minion.network.handler.InboxHandler;
import com.github.lil_minion.network.handler.CommandHandler;
import com.github.lil_minion.network.message.MailMessage;
import com.github.lil_minion.network.message.InboxMessage;
import com.github.lil_minion.network.message.InteractionRequestMessage;
import com.github.lil_minion.network.message.CommandMessage;
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
                MailHandler.handle(mailMessage, context.player()));

        ClientPlayNetworking.registerGlobalReceiver(InteractionRequestMessage.ID, (requestMessage, context) ->
                InteractionRequestHandler.handle(requestMessage, context.player()));

        ClientPlayNetworking.registerGlobalReceiver(InboxMessage.ID, (openInboxScreenMessage, context) ->
                InboxHandler.handle(openInboxScreenMessage, context.player()));

        ClientPlayNetworking.registerGlobalReceiver(CommandMessage.ID, (simpleMessage, context) ->
                CommandHandler.handle(simpleMessage.message(), context.player()));
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

        PayloadTypeRegistry.playS2C().register(CommandMessage.ID, CommandMessage.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(CommandMessage.ID, CommandMessage.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(InboxMessage.ID, InboxMessage.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(MailMessage.ID, (mailMessage, context) ->
                MailHandler.handle(mailMessage, context.player()));

        ServerPlayNetworking.registerGlobalReceiver(CommandMessage.ID, (simpleMessage, context) ->
                CommandHandler.handle(simpleMessage.message(), context.player()));

        ServerPlayNetworking.registerGlobalReceiver(InteractionRequestMessage.ID, (requestMessage, context) ->
                InteractionRequestHandler.handle(requestMessage, context.player()));
    }

}
