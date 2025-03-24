package com.github.lil_minion.couples.neoforge.network;

import com.github.lil_minion.network.handler.MailMessageHandler;
import com.github.lil_minion.network.handler.OpenInboxScreenHandler;
import com.github.lil_minion.network.message.MailMessage;
import com.github.lil_minion.network.message.OpenInboxScreenMessage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * Registers network message handlers for the NeoForge network.
 */
public class NetworkMessagesNeoForge {

    /**
     * Registers payload types and handlers.
     *
     * @param event The event used to register the handlers.
     */
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

        registrar.playBidirectional(
                OpenInboxScreenMessage.ID,
                OpenInboxScreenMessage.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        (openInboxScreenMessage, context) ->
                                OpenInboxScreenHandler.handle(openInboxScreenMessage, context.player()),
                        null
                )
        );
    }

}
