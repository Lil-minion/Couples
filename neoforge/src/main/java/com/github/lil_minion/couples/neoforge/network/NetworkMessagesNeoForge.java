package com.github.lil_minion.couples.neoforge.network;

import com.github.lil_minion.network.handler.MailMessageHandler;
import com.github.lil_minion.network.message.MailMessage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkMessagesNeoForge {

    public static void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                MailMessage.ID,
                MailMessage.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        (mailMessage, context) ->
                                MailMessageHandler.handle(mailMessage, context.player()),
                        (mailMessage, context) ->
                                MailMessageHandler.handle(mailMessage, context.player())
                )
        );
    }

}
