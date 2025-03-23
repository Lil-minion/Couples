package com.github.lil_minion.couples.fabric.network;

import com.github.lil_minion.network.MailHandler;
import com.github.lil_minion.network.MailMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModMessagesFabric {

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(MailMessage.ID, (mailMessage, context) ->
                MailHandler.handle(mailMessage, context.player()));
    }

    public static void registerServer() {
        PayloadTypeRegistry.playS2C().register(MailMessage.ID, MailMessage.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(MailMessage.ID, MailMessage.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MailMessage.ID, (mailMessage, context) ->
                MailHandler.handle(mailMessage, context.player()));
    }

}
