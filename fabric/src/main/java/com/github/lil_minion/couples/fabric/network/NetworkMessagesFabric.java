package com.github.lil_minion.couples.fabric.network;

import com.github.lil_minion.network.handler.MailMessageHandler;
import com.github.lil_minion.network.message.MailMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkMessagesFabric {

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(MailMessage.ID, (mailMessage, context) ->
                MailMessageHandler.handle(mailMessage, context.player()));
    }

    public static void registerServer() {
        PayloadTypeRegistry.playS2C().register(MailMessage.ID, MailMessage.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(MailMessage.ID, MailMessage.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MailMessage.ID, (mailMessage, context) ->
                MailMessageHandler.handle(mailMessage, context.player()));
    }

}
