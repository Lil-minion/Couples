package com.github.lil_minion.couples.neoforge.network;

import com.github.lil_minion.network.MailHandler;
import com.github.lil_minion.network.MailMessage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModMessagesNeoForge {

    public static void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                MailMessage.ID,
                MailMessage.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        (mailMessage, context) ->
                                MailHandler.handle((MailMessage) mailMessage, context.player()),
                        (mailMessage, context) ->
                                MailHandler.handle((MailMessage) mailMessage, context.player())
                )
        );
    }

}
